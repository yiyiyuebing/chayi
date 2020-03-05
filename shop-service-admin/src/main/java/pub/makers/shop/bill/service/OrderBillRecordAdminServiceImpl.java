package pub.makers.shop.bill.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.bill.entity.TimeCycle;
import pub.makers.shop.bill.enums.OrderBillStatus;
import pub.makers.shop.bill.enums.TimeCycleType;
import pub.makers.shop.bill.vo.ExportExcelOrderBillVo;
import pub.makers.shop.bill.vo.OrderBillRecordParam;
import pub.makers.shop.bill.vo.OrderBillRecordVo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/9/6.
 */
@Service(version = "1.0.0")
public class OrderBillRecordAdminServiceImpl implements OrderBillRecordMgrBizService {


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Reference(version = "1.0.0")
    private TimeCycleBizService timeCycleBizService;
    @Reference(version = "1.0.0")
    private BillBizService billBizService;

    @Override
    public Map<String, Object> getBillRecordList(OrderBillRecordParam param, Paging paging) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        //结算周期
        TimeCycle timeCycle = timeCycleBizService.getTimeCycleInfo(TimeCycleType.settleTime);
        param.setCycle(timeCycle.getDuration());
        //结算列表数据
        ResultList<OrderBillRecordVo> billRecordVoResultList = new ResultList<OrderBillRecordVo>();
        String recordVoListSql = FreeMarkerHelper.getValueFromTpl("sql/bill/queryOrderBillRecordList.sql", param);
        List<OrderBillRecordVo> recordVoList = jdbcTemplate.query(recordVoListSql + " limit ?, ?", ParameterizedBeanPropertyRowMapper.newInstance(OrderBillRecordVo.class), paging.getPs(), paging.getPn());

        //支付金额
        BigDecimal totalPayamount = jdbcTemplate.queryForObject("select sum(a.paymentAmount) from (" + recordVoListSql + ") a", BigDecimal.class);
        //结算金额
        BigDecimal totalSettleAmount = jdbcTemplate.queryForObject("select sum(a.supplyPrice) from (" + recordVoListSql + ") a", BigDecimal.class);
        billRecordVoResultList.setResultList(recordVoList);
        //总记录数
        Long totalCount = jdbcTemplate.queryForObject("select count(*) from (" + recordVoListSql + ") a", Long.class);
        billRecordVoResultList.setTotalRecords(totalCount.intValue());

        resultMap.put("page", billRecordVoResultList);
        resultMap.put("paymentAmountCount", totalPayamount == null ? BigDecimal.ZERO : totalPayamount.setScale(2, RoundingMode.HALF_UP));
        resultMap.put("supplyPriceCount", totalSettleAmount == null ? BigDecimal.ZERO : totalSettleAmount.setScale(2, RoundingMode.HALF_UP));
        return resultMap;
    }

    @Override
    public ResultData updateOrderBillStatus(String ids, Integer status) {
        String updateOrderBillSql = "update order_bill_record set status = ? where id in (" + ids + ")";

        jdbcTemplate.update(updateOrderBillSql, status);

        if (status != null && OrderBillStatus.payed.getDbData() == status) {
            //操作账户余额
            billBizService.doSettlementMoneyBill(ids);
        }

        return ResultData.createSuccess();
    }

    @Override
    public List<ExportExcelOrderBillVo> getExportExcelOrderBillList(OrderBillRecordParam param) {
        //结算周期
        TimeCycle timeCycle = timeCycleBizService.getTimeCycleInfo(TimeCycleType.settleTime);
        param.setCycle(timeCycle.getDuration());
        String recordVoExportListSql = FreeMarkerHelper.getValueFromTpl("sql/bill/queryOrderBillExportList.sql", param);
        List<ExportExcelOrderBillVo> recordVoList = jdbcTemplate.query(recordVoExportListSql, ParameterizedBeanPropertyRowMapper.newInstance(ExportExcelOrderBillVo.class));
        return recordVoList;
    }

    @Override
    public BigDecimal getTotalPayment(OrderBillRecordParam param) {
        //结算周期
        TimeCycle timeCycle = timeCycleBizService.getTimeCycleInfo(TimeCycleType.settleTime);
        param.setCycle(timeCycle.getDuration());
        String recordVoListSql = FreeMarkerHelper.getValueFromTpl("sql/bill/queryOrderBillRecordList.sql", param);
        //支付金额
        BigDecimal totalPayamount = jdbcTemplate.queryForObject("select sum(a.paymentAmount) from (" + recordVoListSql + ") a", BigDecimal.class);
        return totalPayamount == null ? BigDecimal.ZERO : totalPayamount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal getTotalSettleAmount(OrderBillRecordParam param) {
        //结算周期
        TimeCycle timeCycle = timeCycleBizService.getTimeCycleInfo(TimeCycleType.settleTime);
        param.setCycle(timeCycle.getDuration());
        String recordVoListSql = FreeMarkerHelper.getValueFromTpl("sql/bill/queryOrderBillRecordList.sql", param);
        BigDecimal totalSettleAmount = jdbcTemplate.queryForObject("select sum(a.supplyPrice) from (" + recordVoListSql + ") a", BigDecimal.class);
        return totalSettleAmount == null ? BigDecimal.ZERO : totalSettleAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public ResultData totalOrderBillInfo() {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        BigDecimal paymentAmountCount = BigDecimal.ZERO;// 总收入
        BigDecimal settlementAmountCount = BigDecimal.ZERO; // 待结算金额
        BigDecimal payAmountCount = BigDecimal.ZERO; // 已结算待付款金额
        BigDecimal alreadypayAmountCount = BigDecimal.ZERO; // 已结算金额
        OrderBillRecordParam param = new OrderBillRecordParam();
        String recordVoListSql = FreeMarkerHelper.getValueFromTpl("sql/bill/queryOrderBillRecordList.sql", param);
        List<OrderBillRecordVo> recordVoList = jdbcTemplate.query(recordVoListSql, ParameterizedBeanPropertyRowMapper.newInstance(OrderBillRecordVo.class));

        for (OrderBillRecordVo orderBillRecordVo : recordVoList) {
            paymentAmountCount = paymentAmountCount.add(orderBillRecordVo.getPaymentAmount());
            if (orderBillRecordVo.getStatus() == null) continue;
            if (OrderBillStatus.unpay.getDbData() == orderBillRecordVo.getStatus()) { //已结算待付款金额
                payAmountCount = payAmountCount.add(orderBillRecordVo.getSupplyPrice());
            }
            if (OrderBillStatus.payed.getDbData() == orderBillRecordVo.getStatus()) { //已结算待付款金额
                alreadypayAmountCount = alreadypayAmountCount.add(orderBillRecordVo.getSupplyPrice());
            }
            if (OrderBillStatus.wait.getDbData() == orderBillRecordVo.getStatus()) { //待结算金额
                settlementAmountCount = settlementAmountCount.add(orderBillRecordVo.getSupplyPrice());
            }
        }
        returnMap.put("paymentAmountCount", paymentAmountCount);
        returnMap.put("settlementAmountCount", settlementAmountCount);
        returnMap.put("payAmountCount", payAmountCount);
        returnMap.put("alreadypayAmountCount", alreadypayAmountCount);

        return ResultData.createSuccess("totalOrderBillInfo", returnMap);
    }
}
