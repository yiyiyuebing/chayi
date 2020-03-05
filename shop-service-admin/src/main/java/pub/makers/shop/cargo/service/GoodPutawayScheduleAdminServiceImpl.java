package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.cargo.entity.GoodPutawaySchedule;
import pub.makers.shop.cart.enums.GoodType;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSku;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuService;
import pub.makers.shop.tradeGoods.entity.TradeGoodSku;
import pub.makers.shop.tradeGoods.service.TradeGoodSkuService;

import java.util.Date;

/**
 * Created by daiwenfa on 2017/6/1.
 */
@Service(version = "1.0.0")
public class GoodPutawayScheduleAdminServiceImpl implements GoodPutawayScheduleMgrBizService {
    @Autowired
    private GoodPutawayScheduleService goodPutawayScheduleService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TradeGoodSkuService tradeGoodSkuService;
    @Autowired
    private PurchaseGoodsSkuService purchaseGoodsSkuService;
    @Override
    public void goodsShelfTime(GoodPutawaySchedule goodPutawaySchedule) {
        Long skuId = goodPutawaySchedule.getSkuId();
        Long goodId = goodPutawaySchedule.getGoodId();
        if(goodPutawaySchedule.getGoodType().equals(GoodType.trade.toString())) {
            TradeGoodSku sku = tradeGoodSkuService.get(Conds.get().eq("id", skuId).eq("good_id",goodId));
            ValidateUtils.notNull(sku, "商品sku不存在");
        }else{
            PurchaseGoodsSku sku = purchaseGoodsSkuService.get(Conds.get().eq("id", skuId).eq("pur_goods_id",goodId));
            ValidateUtils.notNull(sku, "采购sku不存在");
        }
        String sql = "update good_putaway_schedule gps set gps.del_flag = 'T' where gps.good_id = ? and gps.sku_id = ? ";
        jdbcTemplate.update(sql, goodPutawaySchedule.getGoodId(),goodPutawaySchedule.getSkuId());
        goodPutawaySchedule.setId(IdGenerator.getDefault().nextId());
        goodPutawaySchedule.setDelFlag("F");
        goodPutawaySchedule.setDateCreate(new Date());
        goodPutawayScheduleService.insert(goodPutawaySchedule);
    }
}
