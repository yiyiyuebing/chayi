package pub.makers.shop.index.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang.StringUtils;
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
import pub.makers.shop.index.entity.IndentMobileModule;
import pub.makers.shop.index.entity.IndentMobileModuleClassify;
import pub.makers.shop.index.entity.IndexFloorKeyword;
import pub.makers.shop.index.vo.IndexMobileFloorVo;
import pub.makers.shop.purchaseGoods.entity.PurchaseClassify;
import pub.makers.shop.purchaseGoods.service.PurchaseClassifyService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by daiwenfa on 2017/7/14.
 */
@Service(version = "1.0.0")
public class IndexMobileAdminServiceImpl implements IndexMobileAdminService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IndentMobileModuleService indentMobileModuleService;

    @Autowired
    private IndentMobileModuleClassifyService indentMobileModuleClassifyService;

    @Autowired
    private PurchaseClassifyService purchaseClassifyService;
    /**
     * 楼层列表数据
     * @param indentMobileModule
     * @param pg
     * @return
     */
    @Override
    public ResultList<IndexMobileFloorVo> getIndexMobileFloorPageList(IndentMobileModule indentMobileModule, Paging pg) {
        String sql = FreeMarkerHelper.getValueFromTpl("sql/index/queryIndexMobileFloorList.sql", null);
        RowMapper<IndexMobileFloorVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(IndexMobileFloorVo.class);
        List<IndexMobileFloorVo> list = jdbcTemplate.query(sql + " limit ?,? ",rowMapper, pg.getPs(), pg.getPn());
        Number total = jdbcTemplate.queryForObject("select count(0) from (" + sql +") nums ",null,Integer.class);
        ResultList<IndexMobileFloorVo> resultList = new ResultList<IndexMobileFloorVo>();
        resultList.setResultList(list);
        resultList.setTotalRecords(total!=null?total.intValue():0);
        return resultList;
    }

    /**
     * 新增楼层
     * @param param
     */
    @Override
    public void addOrUpdateIndexMobileFloor(IndentMobileModule param) {
        String id = "";
        if(StringUtils.isBlank(param.getId())){
            id = IdGenerator.getDefault().nextId()+"";
            param.setId(id);
            param.setDelFlag("F");
            param.setIsValid("T");
            param.setDateCreated(new Date());
            indentMobileModuleService.insert(param);
        }else {
            id = param.getId();
            param.setLastUpdated(new Date());
            indentMobileModuleService.update(param);
        }
        indentMobileModuleClassifyService.delete(Conds.get().eq("module_id", id));
        List<IndentMobileModuleClassify> list = param.getIndexMobileModuleClassifyList();
        for (IndentMobileModuleClassify indentMobileModuleClassify:list){
            indentMobileModuleClassify.setId(IdGenerator.getDefault().nextId() + "");
            indentMobileModuleClassify.setModuleId(id);
            indentMobileModuleClassify.setIsValid("T");
            indentMobileModuleClassify.setDelFlag("F");
            indentMobileModuleClassify.setDateCreated(new Date());
            indentMobileModuleClassifyService.insert(indentMobileModuleClassify);
        }
    }

    /**
     * 获取楼层信息
     * @param id
     * @return
     */
    @Override
    public IndentMobileModule getIndentMobileModuleData(String id) {
        IndentMobileModule indentMobileModule = indentMobileModuleService.get(Conds.get().eq("id", id));
        indentMobileModule.setIndexMobileModuleClassifyList(indentMobileModuleClassifyService.list(Conds.get().eq("module_id",id)));
        return indentMobileModule;
    }

    /**
     * 获取分类列表
     * @param id
     * @return
     */
    @Override
    public List<PurchaseClassify> getClassify(String id) {
        List<PurchaseClassify> purchaseClassifyList = new ArrayList<>();
        List<PurchaseClassify> list = purchaseClassifyService.list(Conds.get().eq("parent_id", id).eq("del_flag","F").order("order_index desc"));
        purchaseClassifyList.addAll(list);
        if(!id.equals("1")){
            if(list.size()>0) {
                for (PurchaseClassify purchaseClassify : list) {
                    List<PurchaseClassify> lists = purchaseClassifyService.list(Conds.get().eq("parent_id", purchaseClassify.getId()).eq("del_flag", "F"));
                    if (lists.size() > 0) {
                        purchaseClassifyList.addAll(lists);
                    }
                }
            }
        }
        return purchaseClassifyList;
    }

    @Override
    public boolean remove(String id) {
        indentMobileModuleService.update(Update.byId(id).set("del_flag", "T").set("last_updated", new Date()));
        List<IndentMobileModuleClassify> list = indentMobileModuleClassifyService.list(Conds.get().eq("module_id", id));
        for(IndentMobileModuleClassify indentMobileModuleClassify:list) {
            indentMobileModuleClassifyService.update(Update.byId(indentMobileModuleClassify.getId()).set("del_flag", "T").set("last_updated", new Date()));
        }
        return true;
    }
}
