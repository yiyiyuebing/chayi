package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.cargo.entity.CargoOutboundDetail;
import pub.makers.shop.cargo.entity.CargoOutboundOrder;
import pub.makers.shop.purchaseGoods.service.PurchaseOrderListService;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrderList;
import pub.makers.shop.tradeOrder.entity.IndentList;
import pub.makers.shop.tradeOrder.service.IndentListService;

import java.util.Date;
import java.util.List;

/**
 * Created by kok on 2017/5/13.
 */
@Service(version = "1.0.0")
public class CargoOutboundBizServiceImpl implements CargoOutboundBizService {
    @Autowired
    private IndentListService indentListService;
    @Autowired
    private CargoOutboundOrderService cargoOutboundOrderService;
    @Autowired
    private CargoOutboundDetailService cargoOutboundDetailService;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private PurchaseOrderListService purchaseOrderListService;

    @Override
    public void addOutbound(final String orderId, OrderBizType orderType, final Long userId, final String remarks) {
        final CargoOutboundOrder outboundOrder = new CargoOutboundOrder();
        outboundOrder.setId(IdGenerator.getDefault().nextId());
        outboundOrder.setStatus(2);
        outboundOrder.setCreateTime(new Date());
        outboundOrder.setSubTime(new Date());
        outboundOrder.setOutboundTime(new Date());
        outboundOrder.setRemarks(remarks);
        outboundOrder.setSourceNo(orderId);
        outboundOrder.setOutboundType(0);
        outboundOrder.setCreateBy(userId);
        if (OrderBizType.trade.equals(orderType)) {
            final List<IndentList> indentLists = indentListService.list(Conds.get().eq("indent_id", orderId));
            if (indentLists != null && !indentLists.isEmpty()) {
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                        cargoOutboundOrderService.insert(outboundOrder);
                        for (IndentList indentList : indentLists) {
                            CargoOutboundDetail outboundDetail = new CargoOutboundDetail();
                            outboundDetail.setId(IdGenerator.getDefault().nextId());
                            outboundDetail.setCount(indentList.getNumber());
                            outboundDetail.setOutboundId(outboundOrder.getId());
                            outboundDetail.setSkuId(Long.valueOf(indentList.getCargoSkuId()));
                            cargoOutboundDetailService.insert(outboundDetail);
                        }
                    }
                });
            }
        } else if (OrderBizType.purchase.equals(orderType)) {
            final List<PurchaseOrderList> purchaseOrderLists = purchaseOrderListService.list(Conds.get().eq("order_id", orderId));
            if (purchaseOrderLists != null && !purchaseOrderLists.isEmpty()) {
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                        cargoOutboundOrderService.insert(outboundOrder);
                        for (PurchaseOrderList purchaseOrderList : purchaseOrderLists) {
                            CargoOutboundDetail outboundDetail = new CargoOutboundDetail();
                            outboundDetail.setId(IdGenerator.getDefault().nextId());
                            outboundDetail.setCount(purchaseOrderList.getNumber());
                            outboundDetail.setOutboundId(outboundOrder.getId());
                            outboundDetail.setSkuId(Long.valueOf(purchaseOrderList.getCargoSkuId()));
                            cargoOutboundDetailService.insert(outboundDetail);
                        }
                    }
                });
            }
        }
    }

    @Override
    public void confirmOutbound(String orderId, String desc) {
        CargoOutboundOrder outboundOrder = cargoOutboundOrderService.get(Conds.get().eq("source_no", orderId));
        cargoOutboundOrderService.update(Update.byId(outboundOrder.getId()).set("status", 3).set("apply_desc", desc));
    }

    @Override
    public void cancelOutbound(String orderId, String desc) {
        CargoOutboundOrder outboundOrder = cargoOutboundOrderService.get(Conds.get().eq("source_no", orderId));
        cargoOutboundOrderService.update(Update.byId(outboundOrder.getId()).set("status", 4).set("apply_desc", desc));
    }
}
