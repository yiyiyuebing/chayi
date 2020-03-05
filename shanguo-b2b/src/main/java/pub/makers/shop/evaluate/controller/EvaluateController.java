package pub.makers.shop.evaluate.controller;

import com.dev.base.json.JsonUtils;
import com.dev.base.utils.MapUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lantu.base.util.ListUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.cargo.entity.vo.ImageGroupVo;
import pub.makers.shop.cart.pojo.CartQuery;
import pub.makers.shop.evaluate.service.EvaluateService;
import pub.makers.shop.purchaseGoods.pojo.PurchaseGoodsEvaluationQuery;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsEvaluationVo;
import pub.makers.shop.purchaseOrder.service.PurchaseOrderB2bService;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.store.vo.ImageVo;
import pub.makers.shop.tradeGoods.entity.Image;
import pub.makers.shop.user.utils.AccountUtils;

import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/7/7.
 */
@Controller
@RequestMapping("evaluate")
public class EvaluateController {
    @Autowired
    private EvaluateService evaluateService;
    @Autowired
    private PurchaseOrderB2bService orderService;


    /***
     * 评价列表
     * @return
     */
    @RequestMapping("evaluatelist")
    public String evaluatelist () {
        AccountUtils.getCurrShopId();
        return "www/evaluate/product_evaluate_list";
    }


    /***
     * 立即评价
     * @return
     */
    @RequestMapping("/orderEvaluation/{id}")
    public String orderEvaluation (@PathVariable  String id, Model model) {
        AccountUtils.getCurrShopId();
        PurchaseOrderVo pov= orderService.getOrderDetail(id);
        model.addAttribute("pov", pov);
        return "www/evaluate/order_evaluation";
    }

    /***
     * 评价成功
     * @return
     */
    @RequestMapping("/orderEvaluationSuccess/{id}")
    public String orderEvaluationSuccess (@PathVariable  String id, Model model) {
        AccountUtils.getCurrShopId();
        PurchaseOrderVo pov= orderService.getOrderDetail(id);
        model.addAttribute("pov", pov);
        return "www/evaluate/order_evaluate_success";
    }

    /***
     * 评价详情
     * @return
     */
    @RequestMapping("/evaluateDetails/{id}")
    public String appraiseDetails (@PathVariable String id,Model model) {
        PurchaseOrderVo pov= orderService.getOrderDetail(id);
        PurchaseGoodsEvaluationQuery query = new PurchaseGoodsEvaluationQuery();
        query.setUserId(AccountUtils.getCurrShopId());
        query.setOrderId(id);
        evaluateService.getEvaluationListByOrder(pov, AccountUtils.getCurrShopId());
//        ImageGroupVo igv = new ImageGroupVo();
//
//        for (PurchaseGoodsEvaluationVo el : evaluationList) {
//            igv = el.getImages();
//        }
//        List<ImageVo> imgList = igv.getImages();
//        StringBuffer buf = new StringBuffer();
//         for (ImageVo img :imgList) {
//             String url = img.getUrl();
//             buf.append(url+",");
//         }
//        if (buf.length()>0) {
//            buf.deleteCharAt(buf.length()-1);
//        }

//        model.addAttribute("buf",buf);
        model.addAttribute("pov",pov);
//        model.addAttribute("evaluationList",evaluationList);
        return "www/evaluate/evaluate_details";
    }

    @RequestMapping("/addEvaluate")
    @ResponseBody
    public ResultData addEvaluate(String addJson) {
        if (StringUtils.isNotEmpty(addJson)) {
            ResultData.createFail();
        }
        List<PurchaseGoodsEvaluationVo> purchaseGoodsEvaluationVos = JsonUtils.toObject(addJson, ListUtils.getCollectionType(List.class, PurchaseGoodsEvaluationVo.class));
        evaluateService.addEvaluation(purchaseGoodsEvaluationVos, AccountUtils.getCurrShopId());
        return ResultData.createSuccess();
    }

    @RequestMapping("getEvaluationList")
    @ResponseBody
    public ResultData getEvaluationList() {
        Map<String,Object> res = Maps.newHashMap();
        PurchaseOrderVo pov= orderService.getOrderDetail("395636121530605568");
        PurchaseGoodsEvaluationQuery query = new PurchaseGoodsEvaluationQuery();
        query.setUserId(AccountUtils.getCurrShopId());
        query.setOrderId("395636121530605568");
        List<PurchaseGoodsEvaluationVo> evaluationList = evaluateService.getEvaluationList(query);
        res.put("pov",pov);
        res.put("evaluationList",evaluationList);
        return ResultData.createSuccess("evaluationList",evaluationList);
    }


}
