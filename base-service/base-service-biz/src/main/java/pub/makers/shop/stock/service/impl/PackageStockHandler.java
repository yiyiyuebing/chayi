package pub.makers.shop.stock.service.impl;

import com.dev.base.utils.SpringContextUtils;
import com.google.common.collect.Maps;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.cargo.entity.CargoSkuStockLog;
import pub.makers.shop.cargo.service.CargoSkuStockLogService;
import pub.makers.shop.stock.service.StockHandler;
import pub.makers.shop.tradeGoods.entity.GoodPackage;
import pub.makers.shop.tradeGoods.service.GoodPackageService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/5/12.
 */
public class PackageStockHandler implements StockHandler {
    private GoodPackageService goodPackageService = SpringContextUtils.getBean(GoodPackageService.class);
    private CargoSkuStockLogService cargoSkuStockLogService = SpringContextUtils.getBean(CargoSkuStockLogService.class);

    @Override
    public void updateOnsalesNo(Long skuId, Integer num) {
        GoodPackage goodPackage = goodPackageService.get(Conds.get().eq("boom_id", skuId));
        ValidateUtils.notNull(goodPackage, "商品不存在");
        ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
        goodPackageService.update(Update.byId(goodPackage.getId()).set("on_sales_no", num).set("update_time", new Date()));
    }

    @Override
    public void appendOnsalesNo(Long skuId, Integer num) {
        GoodPackage goodPackage = goodPackageService.get(Conds.get().eq("boom_id", skuId));
        ValidateUtils.notNull(goodPackage, "商品不存在");
        ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
        Integer onSalesNo = goodPackage.getOnSalesNo() == null ? 0 : goodPackage.getOnSalesNo();
        goodPackageService.update(Update.byId(goodPackage.getId()).set("on_sales_no", onSalesNo + num).set("update_time", new Date()));
    }

    @Override
    public void salesToOut(Long skuId, Integer num) {
        GoodPackage goodPackage = goodPackageService.get(Conds.get().eq("boom_id", skuId));
        ValidateUtils.notNull(goodPackage, "商品不存在");
        ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
        Integer onSalesNo = goodPackage.getOnSalesNo() == null ? 0 : goodPackage.getOnSalesNo();
//        ValidateUtils.isTrue(onSalesNo >= num, "商品库存不足");
        goodPackage.setOnSalesNo(onSalesNo - num);
        goodPackageService.update(Update.byId(goodPackage.getId()).set("on_sales_no", goodPackage.getOnSalesNo()).set("update_time", new Date()));
    }

    @Override
    public void salesToPay(Long skuId, Integer num) {
        GoodPackage goodPackage = goodPackageService.get(Conds.get().eq("boom_id", skuId));
        ValidateUtils.notNull(goodPackage, "商品不存在");
        ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
        Integer onSalesNo = goodPackage.getOnSalesNo() == null ? 0 : goodPackage.getOnSalesNo();
//        ValidateUtils.isTrue(onSalesNo >= num, "商品库存不足");
        Integer onPayNo = goodPackage.getOnPayNo() == null ? 0 : goodPackage.getOnPayNo();
        Integer saleNum = goodPackage.getSaleNum() == null ? 0 : goodPackage.getSaleNum();
        goodPackageService.update(Update.byId(goodPackage.getId()).set("on_sales_no", onSalesNo - num).set("on_pay_no", onPayNo + num)
                .set("sale_num", saleNum + num).set("update_time", new Date()));

        createLog(goodPackage, 4, num);
    }

    @Override
    public void payToSales(Long skuId, Integer num) {
        GoodPackage goodPackage = goodPackageService.get(Conds.get().eq("boom_id", skuId));
        ValidateUtils.notNull(goodPackage, "商品不存在");
        ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
        Integer onPayNo = goodPackage.getOnPayNo() == null ? 0 : goodPackage.getOnPayNo();
        // 库存数小于修改数
        num = onPayNo < num ? onPayNo : num;
        Integer onSalesNo = goodPackage.getOnSalesNo() == null ? 0 : goodPackage.getOnSalesNo();
        Integer saleNum = goodPackage.getSaleNum() == null ? 0 : goodPackage.getSaleNum();
        goodPackageService.update(Update.byId(goodPackage.getId()).set("on_pay_no", onPayNo - num).set("on_sales_no", onSalesNo + num)
                .set("sale_num", saleNum - num).set("update_time", new Date()));

        createLog(goodPackage, 4, num);
    }

    @Override
    public void payToSend(Long skuId, Integer num) {
        GoodPackage goodPackage = goodPackageService.get(Conds.get().eq("boom_id", skuId));
        ValidateUtils.notNull(goodPackage, "商品不存在");
        ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
        Integer onPayNo = goodPackage.getOnPayNo() == null ? 0 : goodPackage.getOnPayNo();
        // 库存数小于修改数
        num = onPayNo < num ? onPayNo : num;
        Integer onSendNo = goodPackage.getOnSendNo() == null ? 0 : goodPackage.getOnSendNo();
        goodPackageService.update(Update.byId(goodPackage.getId()).set("on_pay_no", onPayNo - num).set("on_send_no", onSendNo + num)
                .set("update_time", new Date()));

        createLog(goodPackage, 5, num);
    }

    @Override
    public void sendToSales(Long skuId, Integer num) {
        GoodPackage goodPackage = goodPackageService.get(Conds.get().eq("boom_id", skuId));
        ValidateUtils.notNull(goodPackage, "商品不存在");
        ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
        Integer onSendNo = goodPackage.getOnSendNo() == null ? 0 : goodPackage.getOnSendNo();
        // 库存数小于修改数
        num = onSendNo < num ? onSendNo : num;
        Integer onSalesNo = goodPackage.getOnSalesNo() == null ? 0 : goodPackage.getOnSalesNo();
        Integer saleNum = goodPackage.getSaleNum() == null ? 0 : goodPackage.getSaleNum();
        goodPackageService.update(Update.byId(goodPackage.getId()).set("on_send_no", onSendNo - num).set("on_sales_no", onSalesNo + num)
                .set("sale_num", saleNum - num).set("update_time", new Date()));

        createLog(goodPackage, 5, num);
    }

    @Override
    public void sendToSeller(Long skuId, Integer num) {
        GoodPackage goodPackage = goodPackageService.get(Conds.get().eq("boom_id", skuId));
        ValidateUtils.notNull(goodPackage, "商品不存在");
        ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
        Integer onSendNo = goodPackage.getOnSendNo() == null ? 0 : goodPackage.getOnSendNo();
        // 库存数小于修改数
        num = onSendNo < num ? onSendNo : num;
        goodPackageService.update(Update.byId(goodPackage.getId()).set("on_send_no", onSendNo - num).set("update_time", new Date()));

        createLog(goodPackage, 6, num);
    }

    @Override
    public Integer getSalesNum(Long skuId) {
        GoodPackage goodPackage = goodPackageService.get(Conds.get().eq("boom_id", skuId));
        ValidateUtils.notNull(goodPackage, "商品不存在");
        return goodPackage.getOnSalesNo() == null ? 0 : goodPackage.getOnSalesNo();
    }

    @Override
    public Map<String, Integer> getSalesNum(List<Long> skuIdList) {
        List<GoodPackage> goodPackageList = goodPackageService.list(Conds.get().in("boom_id", skuIdList));
        Map<String, Integer> map = Maps.newHashMap();
        for (GoodPackage goodPackage : goodPackageList) {
            map.put(goodPackage.getBoomId(), goodPackage.getOnSalesNo() == null ? 0 : goodPackage.getOnSalesNo());
        }
        return map;
    }

    @Override
    public Long getCargoSkuId(Long skuId) {
        return null;
    }

    private CargoSkuStockLog createLog(GoodPackage stock, int status, int count) {
        CargoSkuStockLog log = new CargoSkuStockLog();
        log.setId(IdGenerator.getDefault().nextId());
        log.setCargoSkuId(Long.valueOf(stock.getBoomId()));
        log.setOutShelvesNo(stock.getStock().intValue() - stock.getOnSalesNo() - stock.getOnPayNo());
        log.setOnSalesNo(stock.getOnSalesNo());
        log.setOnPayNo(stock.getOnPayNo());
        log.setOnSendNo(stock.getOnSendNo());
        log.setInboundId(Long.valueOf(stock.getBoomId()));
        log.setCreateTime(new Date());
        log.setUpdateCount(count);
        log.setUpdateStatus(status);
        cargoSkuStockLogService.insert(log);
        return log;
    }
}
