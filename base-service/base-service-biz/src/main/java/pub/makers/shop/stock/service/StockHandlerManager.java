package pub.makers.shop.stock.service;

import pub.makers.shop.stock.service.impl.CargoStockHandler;
import pub.makers.shop.stock.service.impl.PackageStockHandler;
import pub.makers.shop.stock.service.impl.PurchaseStockHandler;
import pub.makers.shop.stock.service.impl.TradeStockHandler;

/**
 * Created by kok on 2017/5/10.
 */
public enum StockHandlerManager {

    trade("trade", new TradeStockHandler()), purchase("purchase", new PurchaseStockHandler()), cargo("cargo", new CargoStockHandler()), goodPackage("package", new PackageStockHandler());

    private String orderType;
    private StockHandler stockHandler;

    private StockHandlerManager(String orderType, StockHandler stockHandler) {
        this.orderType = orderType;
        this.stockHandler = stockHandler;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public StockHandler getStockHandler() {
        return stockHandler;
    }

    public void setStockHandler(StockHandler stockHandler) {
        this.stockHandler = stockHandler;
    }

    public static StockHandler findHandler(String orderType) {
        for (StockHandlerManager stockHandlerManager : values()) {
            if (stockHandlerManager.getOrderType().equals(orderType)) {
                return stockHandlerManager.getStockHandler();
            }
        }
        return null;
    }
}
