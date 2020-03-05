package pub.makers.shop.bill.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.bill.vo.ExportExcelOrderBillVo;
import pub.makers.shop.bill.vo.OrderBillRecordParam;
import pub.makers.shop.bill.vo.OrderBillRecordVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/9/6.
 */
public interface OrderBillRecordMgrBizService {

    /**
     * 获取对账管理列表
     * @param param
     * @param paging
     * @return
     */
    Map<String, Object> getBillRecordList(OrderBillRecordParam param, Paging paging);

    /**
     * 更新账单状态
     * @param ids
     * @param status
     * @return
     */
    ResultData updateOrderBillStatus(String ids, Integer status);

    /**
     * 获取导出列表信息
     * @param param
     * @return
     */
    List<ExportExcelOrderBillVo> getExportExcelOrderBillList(OrderBillRecordParam param);

    /**
     * 获取支付总金额
     * @param param
     * @return
     */
    BigDecimal getTotalPayment(OrderBillRecordParam param);

    /**
     * 获取结算总金额
     * @param param
     * @return
     */
    BigDecimal getTotalSettleAmount(OrderBillRecordParam param);

    /**
     * 统计账单信息
     * @return
     */
    ResultData totalOrderBillInfo();
}
