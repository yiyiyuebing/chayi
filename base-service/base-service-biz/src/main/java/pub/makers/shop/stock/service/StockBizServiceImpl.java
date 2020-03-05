package pub.makers.shop.stock.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.dev.base.utils.SpringContextUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.transaction.support.TransactionTemplate;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.pojo.BaseOrderItem;
import pub.makers.shop.baseOrder.utils.BaseOrderHelps;
import pub.makers.shop.stock.pojo.StockQuery;

import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/5/10.
 */
@Service(version = "1.0.0")
public class StockBizServiceImpl implements StockBizService {
    private StockHandler cargoStockHandler = StockHandlerManager.cargo.getStockHandler();
    private StockHandler packageStockHandler = StockHandlerManager.goodPackage.getStockHandler();
    private TransactionTemplate transactionTemplate = SpringContextUtils.getBean(TransactionTemplate.class);

    @Override
    public void upGoodStock(final StockQuery stockQuery) {
        if (stockQuery.getSkuId() > 100000000000L) { //单品
            StockHandler stockHandler = StockHandlerManager.findHandler(stockQuery.getOrderType().name());
            ValidateUtils.notNull(stockHandler, "订单类型错误");
            // 验证货品库存
            Long cargoSkuId = stockHandler.getCargoSkuId(stockQuery.getSkuId());
            Integer stock = cargoStockHandler.getSalesNum(cargoSkuId);
            ValidateUtils.isTrue(stock >= stockQuery.getNum(), "货品库存不足");
            stockHandler.appendOnsalesNo(stockQuery.getSkuId(), stockQuery.getNum());
        } else { //套餐
            packageStockHandler.appendOnsalesNo(stockQuery.getSkuId(), stockQuery.getNum());
        }
    }

    @Override
    public void downGoodStock(final StockQuery stockQuery) {
        if (stockQuery.getSkuId() > 100000000000L) { //单品
            StockHandler stockHandler = StockHandlerManager.findHandler(stockQuery.getOrderType().name());
            ValidateUtils.notNull(stockHandler, "订单类型错误");
            stockHandler.salesToOut(stockQuery.getSkuId(), stockQuery.getNum());
        } else { //套餐
            packageStockHandler.salesToOut(stockQuery.getSkuId(), stockQuery.getNum());
        }
    }

    @Override
    public void lockStock(final StockQuery stockQuery) {
        if (stockQuery.getSkuId() > 100000000000L) { //单品
        	
        	StockHandler stockHandler = StockHandlerManager.findHandler(stockQuery.getOrderType().name());
            ValidateUtils.notNull(stockHandler, "订单类型错误");
            stockHandler.salesToPay(stockQuery.getSkuId(), stockQuery.getNum());

            // 货品
            Long cargoSkuId = stockHandler.getCargoSkuId(stockQuery.getSkuId());
            cargoStockHandler.salesToPay(cargoSkuId, stockQuery.getNum());
//            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//                @Override
//                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
//                    
//                }
//            });
        } else { //套餐
            packageStockHandler.salesToPay(stockQuery.getSkuId(), stockQuery.getNum());
        }
    }

    @Override
    public void useLockStock(final StockQuery stockQuery) {
        if (stockQuery.getSkuId() > 100000000000L) { //单品
        	StockHandler stockHandler = StockHandlerManager.findHandler(stockQuery.getOrderType().name());
            ValidateUtils.notNull(stockHandler, "订单类型错误");
            stockHandler.payToSend(stockQuery.getSkuId(), stockQuery.getNum());

            // 货品
            Long cargoSkuId = stockHandler.getCargoSkuId(stockQuery.getSkuId());
            cargoStockHandler.payToSend(cargoSkuId, stockQuery.getNum());
//            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//                @Override
//                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
//                    
//                }
//            });
        } else { //套餐
            packageStockHandler.payToSend(stockQuery.getSkuId(), stockQuery.getNum());
        }
    }

    @Override
    public void releaseLockStock(final StockQuery stockQuery) {
        if (stockQuery.getSkuId() > 100000000000L) { //单品
        	StockHandler stockHandler = StockHandlerManager.findHandler(stockQuery.getOrderType().name());
            ValidateUtils.notNull(stockHandler, "订单类型错误");
            stockHandler.payToSales(stockQuery.getSkuId(), stockQuery.getNum());

            // 货品
            Long cargoSkuId = stockHandler.getCargoSkuId(stockQuery.getSkuId());
            cargoStockHandler.payToSales(cargoSkuId, stockQuery.getNum());
//            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//                @Override
//                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
//                    
//                }
//            });
        } else { //套餐
            packageStockHandler.payToSales(stockQuery.getSkuId(), stockQuery.getNum());
        }
    }

    @Override
    public void rfStock(final StockQuery stockQuery) {
        if (stockQuery.getSkuId() > 100000000000L) { //单品
        	StockHandler stockHandler = StockHandlerManager.findHandler(stockQuery.getOrderType().name());
            ValidateUtils.notNull(stockHandler, "订单类型错误");
            stockHandler.sendToSales(stockQuery.getSkuId(), stockQuery.getNum());

            // 货品
            Long cargoSkuId = stockHandler.getCargoSkuId(stockQuery.getSkuId());
            cargoStockHandler.sendToSales(cargoSkuId, stockQuery.getNum());
//            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//                @Override
//                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
//                    
//                }
//            });
        } else { //套餐
            packageStockHandler.sendToSales(stockQuery.getSkuId(), stockQuery.getNum());
        }
    }

    @Override
    public void rtStock(final StockQuery stockQuery) {
        if (stockQuery.getSkuId() > 100000000000L) { //单品
        	StockHandler stockHandler = StockHandlerManager.findHandler(stockQuery.getOrderType().name());
            ValidateUtils.notNull(stockHandler, "订单类型错误");
            stockHandler.appendOnsalesNo(stockQuery.getSkuId(), stockQuery.getNum());

            // 货品
            Long cargoSkuId = stockHandler.getCargoSkuId(stockQuery.getSkuId());
            cargoStockHandler.appendOnsalesNo(cargoSkuId, stockQuery.getNum());
//            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//                @Override
//                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
//                    
//                }
//            });
        } else { //套餐
            packageStockHandler.appendOnsalesNo(stockQuery.getSkuId(), stockQuery.getNum());
        }
    }

    @Override
    public void useStock(final StockQuery stockQuery) {
        if (stockQuery.getSkuId() > 100000000000L) { //单品
        	StockHandler stockHandler = StockHandlerManager.findHandler(stockQuery.getOrderType().name());
            ValidateUtils.notNull(stockHandler, "订单类型错误");
            stockHandler.sendToSeller(stockQuery.getSkuId(), stockQuery.getNum());

            // 货品
            Long cargoSkuId = stockHandler.getCargoSkuId(stockQuery.getSkuId());
            cargoStockHandler.sendToSeller(cargoSkuId, stockQuery.getNum());
//            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//                @Override
//                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
//                    
//                }
//            });
        } else { //套餐
            packageStockHandler.sendToSeller(stockQuery.getSkuId(), stockQuery.getNum());
        }
    }

    @Override
    public boolean hasEnoughStock(StockQuery stockQuery) {
        if (stockQuery.getSkuId() > 100000000000L) { //单品
            StockHandler stockHandler = StockHandlerManager.findHandler(stockQuery.getOrderType().name());
            ValidateUtils.notNull(stockHandler, "订单类型错误");
            Integer salesNum = stockHandler.getSalesNum(stockQuery.getSkuId());
            if (salesNum < stockQuery.getNum()) {
                return false;
            }

            // 货品
            Long cargoSkuId = stockHandler.getCargoSkuId(stockQuery.getSkuId());
            Integer cargoSalesNum = cargoStockHandler.getSalesNum(cargoSkuId);
            return cargoSalesNum >= stockQuery.getNum();
        } else { //套餐
            Integer salesNum = packageStockHandler.getSalesNum(stockQuery.getSkuId());
            return salesNum >= stockQuery.getNum();
        }
    }

    @Override
    public int queryCurrStork(String goodSkuId, String orderBizType) {
        if (Long.valueOf(goodSkuId) > 100000000000L) { //单品
            StockHandler stockHandler = StockHandlerManager.findHandler(orderBizType);
            ValidateUtils.notNull(stockHandler, "订单类型错误");
            Integer salesNum = stockHandler.getSalesNum(Long.valueOf(goodSkuId));
            // 货品
            Long cargoSkuId = stockHandler.getCargoSkuId(Long.valueOf(goodSkuId));
            Integer cargoSalesNum = cargoStockHandler.getSalesNum(cargoSkuId);
            return Math.min(salesNum, cargoSalesNum);
        } else { //套餐
            return packageStockHandler.getSalesNum(Long.valueOf(goodSkuId));
        }
    }

    @Override
    public Map<String, Integer> queryCurrStork(List<String> goodSkuIdList, String orderBizType) {
        List<Long> skuIdList = Lists.newArrayList();
        List<Long> packageIdList = Lists.newArrayList();
        for (Object goodSkuId : goodSkuIdList) {
            Long skuId = Long.valueOf(goodSkuId.toString());
            if (skuId > 100000000000L) { //单品
                skuIdList.add(skuId);
            } else { //套餐
                packageIdList.add(skuId);
            }
        }
        Map<String, Integer> map = Maps.newHashMap();
        Map<String, Integer> skuStockMap = Maps.newHashMap();
        Map<String, Integer> packageStockMap = Maps.newHashMap();
        if (!skuIdList.isEmpty()) {
            StockHandler stockHandler = StockHandlerManager.findHandler(orderBizType);
            ValidateUtils.notNull(stockHandler, "订单类型错误");
            skuStockMap = stockHandler.getSalesNum(skuIdList);
        }
        if (!packageIdList.isEmpty()) {
            packageStockMap = packageStockHandler.getSalesNum(packageIdList);
        }
        map.putAll(skuStockMap);
        map.putAll(packageStockMap);
        return map;
    }

    @Override
    public void lockStocks(final List<? extends BaseOrderItem> itemList, OrderBizType orderBizType) {
    	
    	final List<? extends BaseOrderItem> groupList = BaseOrderHelps.groupItems(itemList);
        final StockHandler stockHandler = StockHandlerManager.findHandler(orderBizType.name());
        ValidateUtils.notNull(stockHandler, "订单类型错误");
        for (BaseOrderItem baseOrderItem : groupList) {
        	String goodSkuId = baseOrderItem.getGoodSkuId();
            if (Long.valueOf(goodSkuId) > 100000000000L) { //单品
                stockHandler.salesToPay(Long.valueOf(goodSkuId), baseOrderItem.getBuyNum());

                // 货品
                Long cargoSkuId = stockHandler.getCargoSkuId(Long.valueOf(goodSkuId));
                cargoStockHandler.salesToPay(cargoSkuId, baseOrderItem.getBuyNum());
            } else { //套餐
                packageStockHandler.salesToPay(Long.valueOf(goodSkuId), baseOrderItem.getBuyNum());
            }
        }
//        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//            @Override
//            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
//                
//            }
//        });
    }

    @Override
    public void useLockStocks(final List<? extends BaseOrderItem> itemList, OrderBizType orderBizType) {
    	final List<? extends BaseOrderItem> groupList = BaseOrderHelps.groupItems(itemList);
        final StockHandler stockHandler = StockHandlerManager.findHandler(orderBizType.name());
        ValidateUtils.notNull(stockHandler, "订单类型错误");
        for (BaseOrderItem baseOrderItem : groupList) {
        	String goodSkuId = baseOrderItem.getGoodSkuId();
            if (Long.valueOf(goodSkuId) > 100000000000L) { //单品
                stockHandler.payToSend(Long.valueOf(goodSkuId), baseOrderItem.getBuyNum());

                // 货品
                Long cargoSkuId = stockHandler.getCargoSkuId(Long.valueOf(goodSkuId));
                cargoStockHandler.payToSend(cargoSkuId, baseOrderItem.getBuyNum());
            } else { //套餐
                packageStockHandler.payToSend(Long.valueOf(goodSkuId), baseOrderItem.getBuyNum());
            }
        }
//        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//            @Override
//            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
//                
//            }
//        });
    }

    @Override
    public void releaseLockStocks(final List<? extends BaseOrderItem> itemList, OrderBizType orderBizType) {
    	final List<? extends BaseOrderItem> groupList = BaseOrderHelps.groupItems(itemList);
        final StockHandler stockHandler = StockHandlerManager.findHandler(orderBizType.name());
        ValidateUtils.notNull(stockHandler, "订单类型错误");
        for (BaseOrderItem baseOrderItem : groupList) {
        	String goodSkuId = baseOrderItem.getGoodSkuId();
            if (Long.valueOf(goodSkuId) > 100000000000L) { //单品
                stockHandler.payToSales(Long.valueOf(goodSkuId), baseOrderItem.getBuyNum());

                // 货品
                Long cargoSkuId = stockHandler.getCargoSkuId(Long.valueOf(goodSkuId));
                cargoStockHandler.payToSales(cargoSkuId, baseOrderItem.getBuyNum());
            } else { //套餐
                packageStockHandler.payToSales(Long.valueOf(goodSkuId), baseOrderItem.getBuyNum());
            }
        }
//        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//            @Override
//            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
//                
//            }
//        });
    }

    @Override
    public void useStocks(final List<? extends BaseOrderItem> itemList, OrderBizType orderBizType) {
    	final List<? extends BaseOrderItem> groupList = BaseOrderHelps.groupItems(itemList);
        final StockHandler stockHandler = StockHandlerManager.findHandler(orderBizType.name());
        ValidateUtils.notNull(stockHandler, "订单类型错误");
        
        for (BaseOrderItem baseOrderItem : groupList) {
        	Long goodSkuId = Long.valueOf(baseOrderItem.getGoodSkuId());
            if (goodSkuId > 100000000000L) { //单品
                stockHandler.sendToSeller(goodSkuId, baseOrderItem.getBuyNum());

                // 货品
                Long cargoSkuId = stockHandler.getCargoSkuId(goodSkuId);
                cargoStockHandler.sendToSeller(cargoSkuId, baseOrderItem.getBuyNum());
            } else { //套餐
                packageStockHandler.sendToSeller(goodSkuId, baseOrderItem.getBuyNum());
            }
        }
//        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//            @Override
//            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
//                
//            }
//        });
    }
}
