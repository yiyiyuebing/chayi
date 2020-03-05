package pub.makers.shop.marketing.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.service.SysDictService;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.marketing.entity.MktShowcase;
import pub.makers.shop.marketing.entity.MktShowcaseGood;

import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2017/5/29.
 */
@Service(version = "1.0.0")
public class MktShowcaseAdminServiceImpl implements MktShowcaseMgrBizService {

    @Autowired
    private MktShowcaseService mktShowcaseService;
    @Autowired
    private MktShowcaseGoodService mktShowcaseGoodService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public ResultList<MktShowcase> mktShowcaseList(MktShowcase mktShowcase, Paging pg) {
        String mktShowcaseListStmt = FreeMarkerHelper.getValueFromTpl("sql/marketing/mktShowcaseList.sql", mktShowcase);
        RowMapper<MktShowcase> mktShowcaseRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(MktShowcase.class);
        List<MktShowcase> mktShowcaseList = Lists.newArrayList();
        mktShowcaseList = jdbcTemplate.query(mktShowcaseListStmt, mktShowcaseRowMapper, pg.getPs(), pg.getPn());
        String countMktShowcaseStmt = FreeMarkerHelper.getValueFromTpl("sql/marketing/countMktShowcase.sql", mktShowcase);
        Integer totalCount = jdbcTemplate.queryForObject(countMktShowcaseStmt, Integer.class);
        ResultList<MktShowcase> resultList = new ResultList<MktShowcase>();
        resultList.setResultList(mktShowcaseList);
        resultList.setTotalRecords(totalCount);
        return resultList;
    }


    @Override
    public List<SysDict> mktShowcaseTypeList() {
        return sysDictService.list(Conds.get().eq("dict_type", "mktshowcase"));
    }

    @Override
    public void saveMktShowcase(final MktShowcase mktShowcase) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                if (StringUtils.isNotBlank(mktShowcase.getId())) {
                    MktShowcase mktShowcaseOld = mktShowcaseService.getById(mktShowcase.getId());
                    BeanUtils.copyProperties(mktShowcase, mktShowcaseOld);
                    mktShowcaseOld.setLastUpdated(new Date());
                    if ("all".equals(mktShowcase.getScope())) {
                        doOptMktShowcaseGoods(mktShowcaseOld);
                    }
                    mktShowcaseService.update(mktShowcaseOld);
                } else {
                    mktShowcase.setId(IdGenerator.getDefault().nextId() + "");
                    mktShowcase.setLastUpdated(new Date());
                    mktShowcase.setDateCreated(new Date());
                    mktShowcase.setDelFlag("F");
                    mktShowcaseService.insert(mktShowcase);
                }
            }
        });
    }

    @Override
    public void saveMktShowcaseGoods(String showcaseId, String goodsIds) {
        mktShowcaseGoodService.delete(Conds.get().eq("showcase_id", showcaseId));
        if (StringUtils.isBlank(goodsIds)) {
            return;
        }
        for (String goodsId : goodsIds.split(",")) {
            MktShowcaseGood mktShowcaseGood = new MktShowcaseGood();
            mktShowcaseGood.setShowcaseId(showcaseId);
            mktShowcaseGood.setGoodId(goodsId);
            mktShowcaseGood.setId(IdGenerator.getDefault().nextId() + "");
            mktShowcaseGood.setDelFlag("F");
            mktShowcaseGood.setIsValid("T");
            mktShowcaseGood.setLastUpdated(new Date());
            mktShowcaseGood.setDateCreated(new Date());
            mktShowcaseGoodService.insert(mktShowcaseGood);
        }
    }

    @Override
    public void editMktShowcaseValid(String ids, String isValid) {
        if (StringUtils.isNotBlank(ids)) {
            for (String id : ids.split(",")) {
                mktShowcaseService.update(Update.byId(id).set("is_valid", isValid));
            }
        }
    }

    @Override
    public void delMktShowcase(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            for (String id : ids.split(",")) {
                mktShowcaseService.update(Update.byId(id).set("del_flag", "T"));
            }
        }

    }

    @Override
    public List<MktShowcaseGood> mktShowcaseGoodsList(String showcaseId) {
        return mktShowcaseGoodService.list(Conds.get().eq("showcase_id", showcaseId).eq("del_flag", "F"));
    }

    /**
     * 处理模版关联商品
     * @param mktShowcase
     */
    private void doOptMktShowcaseGoods(MktShowcase mktShowcase) {
        mktShowcaseGoodService.delete(Conds.get().eq("showcase_id", mktShowcase.getId()));
    }
}
