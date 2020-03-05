package pub.makers.shop.user.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import pub.makers.shop.store.service.GoodReceiptAddrBizService;
import pub.makers.shop.store.vo.GoodReceiptAddrVo;
import pub.makers.shop.user.utils.AccountUtils;

import java.util.List;

/**
 * Created by kok on 2017/8/2.
 */
@Service
public class GoodReceiptAddrAppService {
    @Reference(version = "1.0.0")
    private GoodReceiptAddrBizService goodReceiptAddrBizService;

    /**
     * 用户id查找收货地址
     */
    public List<GoodReceiptAddrVo> listGoodReceiptAddr() {
        String userId = AccountUtils.getCurrShopId();
        return goodReceiptAddrBizService.listGoodReceiptAddr(userId);
    }

    /**
     * 用户id查找默认收货地址
     */
    public GoodReceiptAddrVo defaultGoodReceiptAddr() {
        String userId = AccountUtils.getCurrShopId();
        return goodReceiptAddrBizService.defaultGoodReceiptAddr(userId);
    }

    /**
     * id查找收货地址
     */
    public GoodReceiptAddrVo getById(String id) {
        return goodReceiptAddrBizService.getById(id);
    }

    /**
     * 保存收货地址
     */
    public GoodReceiptAddrVo createGoodReceiptAddr(GoodReceiptAddrVo addr) {
        addr.setUserId(AccountUtils.getCurrShopId());
        return goodReceiptAddrBizService.createGoodReceiptAddr(addr);
    }

    /**
     * 更新收货地址
     */
    public GoodReceiptAddrVo updateGoodReceiptAddr(GoodReceiptAddrVo addr) {
        addr.setUserId(AccountUtils.getCurrShopId());
        return goodReceiptAddrBizService.updateGoodReceiptAddr(addr);
    }

    /**
     * 删除收获地址
     */
    public void delGoodReceiptAddr(String addrId) {
        String userId = AccountUtils.getCurrShopId();
        goodReceiptAddrBizService.delGoodReceiptAddr(addrId, userId);
    }

    /**
     * 设置默认收货地址
     */
    public void updateDefaultAddr(String addrId) {
        String userId = AccountUtils.getCurrShopId();
        goodReceiptAddrBizService.updateDefaultAddr(addrId, userId);
    }
}
