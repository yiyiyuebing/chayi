package pub.makers.shop.stock.service.impl;

import com.dev.base.utils.SpringContextUtils;
import com.google.common.collect.Maps;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.stock.service.StockHandler;
import pub.makers.shop.tradeGoods.entity.TradeGood;
import pub.makers.shop.tradeGoods.entity.TradeGoodSku;
import pub.makers.shop.tradeGoods.service.TradeGoodService;
import pub.makers.shop.tradeGoods.service.TradeGoodSkuService;

import java.util.List;
import java.util.Map;

/**
 * 商城商品库存操作接口
 * Created by kok on 2017/5/10.
 */
public class TradeStockHandler implements StockHandler {
    private TradeGoodSkuService tradeGoodSkuService = SpringContextUtils.getBean(TradeGoodSkuService.class);
    private TradeGoodService tradeGoodService = SpringContextUtils.getBean(TradeGoodService.class);

    @Override
    public void updateOnsalesNo(Long skuId, Integer num) {
        TradeGoodSku sku = tradeGoodSkuService.getById(skuId);
        ValidateUtils.notNull(sku, "商品不存在");
        ValidateUtils.isTrue(num >= 0, "库存修改数必须大于零");
        // 更新sku库存
        tradeGoodSkuService.update(Update.byId(sku.getId()).set("on_sales_no", num));
    }

    @Override
    public void appendOnsalesNo(Long skuId, Integer num) {
        TradeGoodSku sku = tradeGoodSkuService.getById(skuId);
        ValidateUtils.notNull(sku, "商品不存在");
        ValidateUtils.isTrue(num >= 0, "库存修改数必须大于零");
        Integer onSalesNo = sku.getOnSalesNo() == null ? 0 : sku.getOnSalesNo();
        // 更新sku库存
        tradeGoodSkuService.update(Update.byId(sku.getId()).set("on_sales_no", onSalesNo + num));
    }

    @Override
    public void salesToOut(Long skuId, Integer num) {
        TradeGoodSku sku = tradeGoodSkuService.getById(skuId);
        ValidateUtils.notNull(sku, "商品不存在");
        ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
        Integer onSalesNo = sku.getOnSalesNo() == null ? 0 : sku.getOnSalesNo();
//        ValidateUtils.isTrue(onSalesNo >= num, "商品库存不足");
        sku.setOnSalesNo(onSalesNo - num);
        // 更新sku库存
        tradeGoodSkuService.update(Update.byId(sku.getId()).set("on_sales_no", sku.getOnSalesNo()));
    }

    @Override
    public void salesToPay(Long skuId, Integer num) {
        TradeGoodSku sku = tradeGoodSkuService.getById(skuId);
        ValidateUtils.notNull(sku, "商品不存在");
        ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
        Integer onSalesNo = sku.getOnSalesNo() == null ? 0 : sku.getOnSalesNo();
//        ValidateUtils.isTrue(onSalesNo >= num, "商品库存不足");
        Integer onPayNo = sku.getOnPayNo() == null ? 0 : sku.getOnPayNo();
        Integer saleNum = sku.getSaleNum() == null ? 0 : sku.getSaleNum();
        // 更新sku库存和销量
        tradeGoodSkuService.update(Update.byId(sku.getId()).set("on_sales_no", onSalesNo - num).set("on_pay_no", onPayNo + num).set("sale_num", saleNum + num));

        TradeGood tradeGood = tradeGoodService.getById(sku.getGoodId());
        Integer allSaleNum = tradeGood.getSaleNum() == null ? 0 : tradeGood.getSaleNum();
        // 更新商品销量
        tradeGoodService.update(Update.byId(tradeGood.getId()).set("sale_num", allSaleNum + num));
    }

    @Override
    public void payToSales(Long skuId, Integer num) {
        TradeGoodSku sku = tradeGoodSkuService.getById(skuId);
        ValidateUtils.notNull(sku, "商品不存在");
        ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
        Integer onPayNo = sku.getOnPayNo() == null ? 0 : sku.getOnPayNo();
        // 库存数小于修改数
        num = onPayNo < num ? onPayNo : num;
        Integer onSalesNo = sku.getOnSalesNo() == null ? 0 : sku.getOnSalesNo();
        Integer saleNum = sku.getSaleNum() == null ? 0 : sku.getSaleNum();
        saleNum = saleNum < num ? num : saleNum;
        // 更新sku库存和销量
        tradeGoodSkuService.update(Update.byId(sku.getId()).set("on_pay_no", onPayNo - num).set("on_sales_no", onSalesNo + num).set("sale_num", saleNum - num));

        TradeGood tradeGood = tradeGoodService.getById(sku.getGoodId());
        Integer allSaleNum = tradeGood.getSaleNum() == null ? 0 : tradeGood.getSaleNum();
        allSaleNum = allSaleNum < num ? num : allSaleNum;
        // 更新商品销量
        tradeGoodService.update(Update.byId(tradeGood.getId()).set("sale_num", allSaleNum - num));
    }

    @Override
    public void payToSend(Long skuId, Integer num) {
        TradeGoodSku sku = tradeGoodSkuService.getById(skuId);
        ValidateUtils.notNull(sku, "商品不存在");
        ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
        Integer onPayNo = sku.getOnPayNo() == null ? 0 : sku.getOnPayNo();
        // 库存数小于修改数
        num = onPayNo < num ? onPayNo : num;
        Integer onSendNo = sku.getOnSendNo() == null ? 0 : sku.getOnSendNo();
        // 更新sku库存
        tradeGoodSkuService.update(Update.byId(sku.getId()).set("on_pay_no", onPayNo - num).set("on_send_no", onSendNo + num));
    }

    @Override
    public void sendToSales(Long skuId, Integer num) {
        TradeGoodSku sku = tradeGoodSkuService.getById(skuId);
        ValidateUtils.notNull(sku, "商品不存在");
        ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
        Integer onSendNo = sku.getOnSendNo() == null ? 0 : sku.getOnSendNo();
        // 库存数小于修改数
        num = onSendNo < num ? onSendNo : num;
        Integer onSalesNo = sku.getOnSalesNo() == null ? 0 : sku.getOnSalesNo();
        Integer saleNum = sku.getSaleNum() == null ? 0 : sku.getSaleNum();
        saleNum = saleNum < num ? num : saleNum;
        // 更新sku库存和销量
        tradeGoodSkuService.update(Update.byId(sku.getId()).set("on_send_no", onSendNo - num).set("on_sales_no", onSalesNo + num).set("sale_num", saleNum - num));

        TradeGood tradeGood = tradeGoodService.getById(sku.getGoodId());
        Integer allSaleNum = tradeGood.getSaleNum() == null ? 0 : tradeGood.getSaleNum();
        allSaleNum = allSaleNum < num ? num : allSaleNum;
        // 更新商品销量
        tradeGoodService.update(Update.byId(tradeGood.getId()).set("sale_num", allSaleNum - num));
    }

    @Override
    public void sendToSeller(Long skuId, Integer num) {
        TradeGoodSku sku = tradeGoodSkuService.getById(skuId);
        ValidateUtils.notNull(sku, "商品不存在");
        ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
        Integer onSendNo = sku.getOnSendNo() == null ? 0 : sku.getOnSendNo();
        // 库存数小于修改数
        num = onSendNo < num ? onSendNo : num;
        // 更新sku库存
        tradeGoodSkuService.update(Update.byId(sku.getId()).set("on_send_no", onSendNo - num));
    }

    @Override
    public Integer getSalesNum(Long skuId) {
        TradeGoodSku sku = tradeGoodSkuService.getById(skuId);
        ValidateUtils.notNull(sku, "商品不存在");
        return sku.getOnSalesNo() == null ? 0 : sku.getOnSalesNo();
    }

    @Override
    public Map<String, Integer> getSalesNum(List<Long> skuIdList) {
        List<TradeGoodSku> skuList = tradeGoodSkuService.list(Conds.get().in("id", skuIdList));
        Map<String, Integer> map = Maps.newHashMap();
        for (TradeGoodSku sku : skuList) {
            map.put(sku.getId().toString(), sku.getOnSalesNo() == null ? 0 : sku.getOnSalesNo());
        }
        return map;
    }

    @Override
    public Long getCargoSkuId(Long skuId) {
        TradeGoodSku sku = tradeGoodSkuService.getById(skuId);
        ValidateUtils.notNull(sku, "商品不存在");
        return sku.getCargoSkuId();
    }
}
