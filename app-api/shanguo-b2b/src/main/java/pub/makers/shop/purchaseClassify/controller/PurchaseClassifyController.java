package pub.makers.shop.purchaseClassify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.purchaseClassify.service.PurchaseClassifyB2bService;
import pub.makers.shop.purchaseGoods.vo.PurchaseClassifyVo;
import pub.makers.shop.user.utils.AccountUtils;

/**
 * Created by dy on 2017/6/2.
 */
@Controller
@RequestMapping("/purchaseClassify")
public class PurchaseClassifyController {

    @Autowired
    private PurchaseClassifyB2bService purchaseClassifyB2bService;

    /**
     * 获取商品类别信息列表
     * @return
     */
    @RequestMapping("/purchaseClassifyList")
    @ResponseBody
    public ResultData purchaseClassifyList() {
        String storeLevel = AccountUtils.getCurrStoreLevelId();
        List<PurchaseClassifyVo> purchaseClassifyVos = purchaseClassifyB2bService.purchaseClassifyList(storeLevel);
        return ResultData.createSuccess("purchaseClassifys", purchaseClassifyVos);
    }
}
