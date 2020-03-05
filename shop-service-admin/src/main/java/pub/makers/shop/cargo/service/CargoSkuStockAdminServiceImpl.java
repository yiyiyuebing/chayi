package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.util.XMLUtil;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.cargo.entity.CargoClassify;
import pub.makers.shop.cargo.entity.CargoSkuStock;
import pub.makers.shop.cargo.vo.CargoSkuStockParams;
import pub.makers.shop.cargo.vo.CargoSkuStockVo;
import pub.makers.shop.u8.service.U8MgrBizSerivce;
import pub.makers.shop.u8.vo.U8StockVo;

import java.util.*;

/**
 * Created by dy on 2017/5/23.
 */
@Service(version = "1.0.0")
public class CargoSkuStockAdminServiceImpl implements CargoSkuStockMgrBizService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CargoSkuStockService cargoSkuStockService;
    @Reference(version = "1.0.0")
    private U8MgrBizSerivce u8MgrBizSerivce;
    @Autowired
    private CargoClassifyService cargoClassifyService;

    public ResultList<CargoSkuStockVo> listByCondition(CargoSkuStockParams param, Paging pg){
        if(StringUtils.isNotBlank(param.getClassify())){
            param.setClassify(getAllClassifyId(param.getClassify()));
        }
        String listStmt= FreeMarkerHelper.getValueFromTpl("sql/cargo/listStock.sql", param);
        RowMapper<CargoSkuStockVo> cargoSkuStockVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(CargoSkuStockVo.class);
        List<CargoSkuStockVo> resultList = jdbcTemplate.query(listStmt, cargoSkuStockVoRowMapper, pg.getPs(), pg.getPn());
        String countStmt=FreeMarkerHelper.getValueFromTpl("sql/cargo/countStock.sql",param);
        Number total = jdbcTemplate.queryForObject(countStmt, Integer.class);
        ResultList<CargoSkuStockVo> result = new ResultList<CargoSkuStockVo>();
        result.setTotalRecords(total != null ? total.intValue() : 0);
        result.setResultList(resultList);
        return result;
    }

    public String getAllClassifyId(String parentId){
        List<String> allList = new ArrayList<>();//所有的分类
        allList.add(parentId);
        List<CargoClassify> list = cargoClassifyService.list(Conds.get().eq("parent_id", parentId));
        List<String> xiajiList = new ArrayList<>();//有下级的分类
        if (list.size() > 0) {
            for (CargoClassify cargoClassify : list) {
                allList.add(cargoClassify.getId()+"");
                xiajiList.add(cargoClassify.getId() + "");
            }
        }
        List<CargoClassify> lists = cargoClassifyService.list(Conds.get().in("parent_id", xiajiList));//查询下级的下级
        if (lists.size() > 0) {
            for (CargoClassify cargoClassify : lists) {
                allList.add(cargoClassify.getId() + "");
            }
        }
        allList = new ArrayList(new HashSet(allList));//去重 allList.add();
        return  StringUtils.join(allList, ",");
    }

    @Override
    public void setWarnings(CargoSkuStockParams param, String ids) {
        if (StringUtils.isBlank(ids)) {
            jdbcTemplate.update("update cargo_sku_stock set warning_value = ?, is_valid = ?", param.getWarningValue(), param.getIsValid());
        } else {
            jdbcTemplate.update("update cargo_sku_stock set warning_value = ?, is_valid = ? where id in ("+ ids +")", param.getWarningValue(), param.getIsValid());
        }
    }

    @Override
    public void setWarningsByIds(CargoSkuStockParams param) {
        cargoSkuStockService.update(Update.byId(param.getIds()).set("warning_value",param.getWarningValue()).set("is_valid",param.getIsValid()));
    }

    @Override
    public void updateIsSync(CargoSkuStockParams param) {
        cargoSkuStockService.update(Update.byId(param.getId()).set("is_sync",param.getIsSync()));
    }

    @Override
    public void updateAutoSynchU8Stock(String skuIdListStr, Integer synchStatus) {
        if (org.apache.commons.lang.StringUtils.isNotBlank(skuIdListStr)) {
            jdbcTemplate.update("update cargo_sku_stock set is_sync = ? where id in ("+ skuIdListStr +")", synchStatus);
        } else {
            jdbcTemplate.update("update cargo_sku_stock set is_sync = ?", synchStatus);
        }
    }

    @Override
    public List<CargoSkuStockVo> stockDetail(String id) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("stockId", id);
        String getCargoSkuStockDetailStmt= FreeMarkerHelper.getValueFromTpl("sql/cargo/getCargoSkuStockDetail.sql", param);
        List<CargoSkuStockVo> cargoSkuStockVos = jdbcTemplate.query(getCargoSkuStockDetailStmt, ParameterizedBeanPropertyRowMapper.newInstance(CargoSkuStockVo.class));
        return cargoSkuStockVos;
    }

    @Override
    public void updateStockForU8(String cInvCodes) throws Exception {

        if (StringUtils.isBlank(cInvCodes)) {
            String result = u8MgrBizSerivce.asychStockFromU8(null);
            List<U8StockVo> list  = XMLUtil.doParseForStockList(result);
            doU8StockAndSkuStock(list, null);
        } else {
            String result = u8MgrBizSerivce.asychStockFromU8(cInvCodes);
            List<U8StockVo> list  = XMLUtil.doParseForStockList(result);
            doU8StockAndSkuStock(list, cInvCodes);
        }

    }

    private void doU8StockAndSkuStock(List<U8StockVo> u8list, String cInvCodes) {
        if(u8list != null && u8list.size() > 0) {
            List<U8StockVo> localList = queryCargoSkuStockList(cInvCodes);
            if (localList != null && localList.size() > 0) {
                for (U8StockVo u8 : u8list) {
                    //遍历u8库存
                    for (U8StockVo lo : localList) {//对比本地库存
                        if (u8.getCinvcode().equals(lo.getCode())) {
                            //总库存=未上架+已上架未售+已售未付款+库存留存(U8的库存会扣掉已付款的库存lo.getOn_send_no())
                            if ((u8.getIquantity() == null ? 0 : u8.getIquantity().intValue()) == (lo.getCurrStock() == null ? 0 : lo.getCurrStock())) {
                                updateLocalStock(lo);
                                break;
                            }

                            int locurr = lo.getCurrStock() == null ? 0 : lo.getCurrStock();
                            int localonpay = lo.getOn_pay_no() == null ? 0 : lo.getOn_pay_no().intValue();
                            int u8curr = u8.getIquantity() == null ? 0 : u8.getIquantity().intValue();
                            if (locurr + localonpay != u8curr) {
                                int realStock = u8.getIquantity().intValue() - localonpay;
                                lo.setCurrStock(realStock);
//                                updateLocalStock(lo);
                            }
                            updateLocalStock(lo);
                        }
                    }
                }
            }
        }
    }

    private void updateLocalStock(U8StockVo u8StockVo) {
        CargoSkuStock cargoSkuStock = new CargoSkuStock();
        cargoSkuStock.setId(u8StockVo.getId());
        cargoSkuStock.setCurrStock(u8StockVo.getCurrStock());
        cargoSkuStock.setUpdateTime(new Date());
        cargoSkuStockService.update(cargoSkuStock);
    }

    private List<U8StockVo> queryCargoSkuStockList(String cInvCodes) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("cInvCodes", StringUtils.isNotBlank(cInvCodes) ? cInvCodes : null);
        String listStockVoStmt = FreeMarkerHelper.getValueFromTpl("sql/cargo/listStockVo.sql", param);
        List<U8StockVo> u8StockVos = jdbcTemplate.query(listStockVoStmt, ParameterizedBeanPropertyRowMapper.newInstance(U8StockVo.class));
        return u8StockVos;
    }

}
