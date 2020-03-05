package pub.makers.shop.purchaseGoods.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.purchaseGoods.entity.PurchaseSearchKeyword;

import java.util.Date;
import java.util.List;

/**
 * Created by daiwenfa on 2017/6/23.
 */
@Service(version="1.0.0")
public class PurchaseSearchKeywordAdminServiceImpl implements PurchaseSearchKeywordAdminService{
    @Autowired
    private PurchaseSearchKeywordService purchaseSearchKeywordService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public ResultList<PurchaseSearchKeyword> getPageList(PurchaseSearchKeyword purchaseSearchKeyword, Paging pg) {
        String sql = FreeMarkerHelper.getValueFromTpl("sql/purchaseGood/queryPurchaseSearchKeywordList.sql", purchaseSearchKeyword);
        RowMapper<PurchaseSearchKeyword> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(PurchaseSearchKeyword.class);
        List<PurchaseSearchKeyword> list = jdbcTemplate.query(sql + " limit ?,? ", rowMapper, pg.getPs(), pg.getPn());
        Number total = jdbcTemplate.queryForObject("select count(0) from (" + sql +") nums ",null,Integer.class);
        ResultList<PurchaseSearchKeyword> resultList = new ResultList<PurchaseSearchKeyword>();
        resultList.setResultList(list);
        resultList.setTotalPages(total!=null?total.intValue():0);
        return resultList;
    }

    @Override
    public boolean ableOrDisable(String id, String operation, long userId) {
        PurchaseSearchKeyword purchaseSearchKeywordOld = purchaseSearchKeywordService.get(Conds.get().eq("id", id));
        String flag = purchaseSearchKeywordOld.getIsValid();
        if(!flag.equals(operation)) {
            purchaseSearchKeywordService.update(Update.byId(id).set("is_valid", operation).set("last_updated",new Date()));
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(String id) {
        purchaseSearchKeywordService.update(Update.byId(id).set("del_flag", "T").set("last_updated", new Date()));
        return true;
    }

    @Override
    public PurchaseSearchKeyword getData(String id) {
        return purchaseSearchKeywordService.get(Conds.get().eq("id", id));
    }

    @Override
    public void addOrUpdate(PurchaseSearchKeyword purchaseSearchKeyword, long userId) {
        if(purchaseSearchKeyword.getId().equals("")||purchaseSearchKeyword.getId()==null){
            purchaseSearchKeyword.setId(IdGenerator.getDefault().nextId() + "");
            purchaseSearchKeyword.setDelFlag("F");
            purchaseSearchKeyword.setIsValid("T");
            purchaseSearchKeyword.setDateCreated(new Date());
            purchaseSearchKeywordService.insert(purchaseSearchKeyword);
        }else{
            purchaseSearchKeyword.setLastUpdated(new Date());
            purchaseSearchKeywordService.update(purchaseSearchKeyword);
        }
    }
}
