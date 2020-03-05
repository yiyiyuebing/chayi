package pub.makers.shop.bill.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.lantu.base.common.entity.BoolType;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.util.IdGenerator;
import pub.makers.shop.base.utils.DateParseUtil;
import pub.makers.shop.baseOrder.enums.OrderStatus;
import pub.makers.shop.bill.entity.IndentBill;
import pub.makers.shop.bill.entity.OrderBillRecord;
import pub.makers.shop.bill.entity.TimeCycle;
import pub.makers.shop.bill.enums.OrderBillStatus;
import pub.makers.shop.bill.enums.TimeCycleType;
import pub.makers.shop.store.entity.StoreLevel;
import pub.makers.shop.store.service.StoreLevelMgrBizService;
import pub.makers.shop.tradeOrder.entity.Indent;
import pub.makers.shop.tradeOrder.service.IndentMgrBizService;
import pub.makers.shop.tradeOrder.vo.IndentListVo;
import pub.makers.shop.tradeOrder.vo.IndentVo;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dy on 2017/9/6.
 */
@Service(version = "1.0.0")
public class OrderBillRecordBizServiceImpl implements OrderBillRecordBizService {

    private Logger logger = Logger.getLogger(OrderBillRecordBizServiceImpl.class);
    @Autowired
    private TimeCycleService timeCycleService;

    @Reference(version = "1.0.0")
    private IndentMgrBizService indentMgrBizService;
    @Reference(version = "1.0.0")
    private StoreLevelMgrBizService storeLevelMgrBizService;
    @Autowired
    private OrderBillRecordService orderBillRecordService;
    @Autowired
    private IndentBillService indentBillService;

    @Override
    public void saveOrderBillRecord(OrderBillRecord orderBillRecord) {
        orderBillRecordService.insert(orderBillRecord);
    }

    public OrderBillRecord getByOrderId(String orderId) {
        OrderBillRecord record = orderBillRecordService.get(Conds.get().eq("order_id", orderId));
        return record;
    }

    public IndentBill getIndentBill(String orderId) {
        IndentBill bill = indentBillService.get(Conds.get().eq("indent_id", orderId));
        return bill;
    }
}
