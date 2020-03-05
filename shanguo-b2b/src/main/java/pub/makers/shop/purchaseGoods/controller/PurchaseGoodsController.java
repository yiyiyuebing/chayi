package pub.makers.shop.purchaseGoods.controller;

import com.dev.base.json.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.utils.DateParseUtil;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseGood.pojo.BaseGood;
import pub.makers.shop.baseGood.pojo.ChangeGoodNumQuery;
import pub.makers.shop.baseGood.pojo.TplQuery;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.PromotionGoodQuery;
import pub.makers.shop.browseLog.service.GoodsBrowseLogB2bService;
import pub.makers.shop.cargo.entity.vo.CargoBrandVo;
import pub.makers.shop.cargo.vo.GoodPageTplVo;
import pub.makers.shop.cart.vo.ChangeGoodNumVo;
import pub.makers.shop.freight.service.FreightTplB2bService;
import pub.makers.shop.index.vo.BaseArticleInfo;
import pub.makers.shop.promotion.enums.PresellType;
import pub.makers.shop.promotion.enums.PromotionActivityType;
import pub.makers.shop.promotion.vo.PresellPromotionActivityVo;
import pub.makers.shop.promotion.vo.PromotionActivityVo;
import pub.makers.shop.promotion.vo.PromotionInfoDetailVo;
import pub.makers.shop.promotion.vo.SalePromotionActivityVo;
import pub.makers.shop.purchaseGoods.entity.PurchaseClassify;
import pub.makers.shop.purchaseGoods.pojo.PurchaseGoodsEvaluationQuery;
import pub.makers.shop.purchaseGoods.pojo.PurchaseGoodsQuery;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsB2bService;
import pub.makers.shop.purchaseGoods.vo.*;
import pub.makers.shop.user.utils.AccountUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/6/2.
 */

@Controller
@RequestMapping("/purchase")
public class PurchaseGoodsController {

    @Autowired
    private PurchaseGoodsB2bService purchaseGoodsB2bService;
    @Autowired
    private FreightTplB2bService freightTplB2bService;
    @Autowired
    private GoodsBrowseLogB2bService goodsBrowseLogB2bService;

    @RequestMapping("/good/{goodId}.html")
    public String jumpTemplatePage(@PathVariable String goodId, Model model) {
        model.addAttribute("goodId", goodId);
        return "www/purchaseGoods/templatePage";
    }


    @RequestMapping("/changeGoodNum")
    @ResponseBody
    public ResultData changeGoodNum(HttpServletRequest request, String queryJson) {
        if (StringUtils.isBlank(queryJson)) {
            return ResultData.createFail("商品信息不存在");
        }
        ChangeGoodNumQuery goodNumQuery = JsonUtils.toObject(queryJson, ChangeGoodNumQuery.class);
        goodNumQuery.setStoreLevelId(AccountUtils.getCurrStoreLevelId());
        goodNumQuery.setClientType(ClientType.pc);
        goodNumQuery.setOrderBizType(OrderBizType.purchase);
        ChangeGoodNumVo changeGoodNumVo = purchaseGoodsB2bService.changeGoodNum(goodNumQuery);
        return ResultData.createSuccess(changeGoodNumVo.getNum());
    }


    /**
     * 详情页 看一看数据
     * @return
     */
    @RequestMapping("getDetailRelGoods")
    @ResponseBody
    public ResultData getDetailRelGoods(String groupIdStr){
        ValidateUtils.notNull(groupIdStr, "商品分类不能为空");
        PurchaseGoodsQuery purchaseGoodsQuery = new PurchaseGoodsQuery();
        purchaseGoodsQuery.setSaleNumSort("0");
        purchaseGoodsQuery.setClassifyIds(groupIdStr);
        String storeLevelId = AccountUtils.getCurrStoreLevelId();
        purchaseGoodsQuery.setStoreLevelId(storeLevelId);
        List<BaseGoodVo> purchaseGoodsVos =
                purchaseGoodsB2bService.getSearchGoodsList(purchaseGoodsQuery);
        return ResultData.createSuccess(purchaseGoodsVos);
    }


    /**
     * 查询页面模版
     * @return
     */
    @RequestMapping("getPageTpl")
    @ResponseBody
    public ResultData getPageTpl(HttpServletRequest request, HttpServletResponse response, String modelJsonStr){
        TplQuery tplQuery = new TplQuery();
        ValidateUtils.notNull(modelJsonStr, "模版信息不能为空");
        GoodPageTplVo fvo = new GoodPageTplVo();
        try {
            tplQuery = JsonUtils.toObject(modelJsonStr, TplQuery.class);
            tplQuery.setClientType(ClientType.pc);
            String storeLevelId = AccountUtils.getCurrStoreLevelId();
            tplQuery.setStoreLevelId(storeLevelId);
            tplQuery.setOrderBizType(OrderBizType.purchase.name());
            fvo = purchaseGoodsB2bService.getPageTpl(tplQuery);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.createFail();
        }
        return ResultData.createSuccess(fvo);
    }

    @RequestMapping("/detail/{goodsId}.html")
    public  String  newDetail(@PathVariable String goodsId, Model model){
        String userId = AccountUtils.getCurrShopId(false);
        String storeLevel = AccountUtils.getCurrStoreLevelId();
        PurchaseGoodsVo goods= purchaseGoodsB2bService.getPcGoodsOnloadDetail(goodsId, storeLevel);
        if (StringUtils.isNotBlank(userId)) {
            //添加足迹
            goodsBrowseLogB2bService.addBrowseLog(goods, userId);
        }
        //查询是否参加活动
        PromotionGoodQuery promotionGoodQuery = new PromotionGoodQuery();
        List<BaseGood> baseGoodList = Lists.newArrayList();
        for (PurchaseGoodsSkuVo purchaseGoodsSkuVo : goods.getGoodSkuVoList()) {
            BaseGood baseGood = new BaseGood();
            baseGood.setGoodSkuId(purchaseGoodsSkuVo.getId());
            baseGood.setAmount(new BigDecimal(StringUtils.isNotBlank(purchaseGoodsSkuVo.getSupplyPrice()) ? purchaseGoodsSkuVo.getSupplyPrice() : "0"));
            baseGood.setGoodId(goods.getId());
            baseGoodList.add(baseGood);
        }
        promotionGoodQuery.setGoodList(baseGoodList);
        promotionGoodQuery.setOrderType(OrderType.normal);
        promotionGoodQuery.setOrderBizType(OrderBizType.purchase);
        promotionGoodQuery.setIsDetail(true);
        PromotionInfoDetailVo goodPromotionalInfoVo = purchaseGoodsB2bService.getPromotionPriceInfo(promotionGoodQuery);
        model.addAttribute("shopId", userId);
        Integer totalStock = 0;
        String goodType = "normal";
        if ("presell".equals(goodPromotionalInfoVo.getActivityType())) {
            for (PurchaseGoodsSkuVo purchaseGoodsSkuVo : goods.getGoodSkuVoList()) {
                totalStock += purchaseGoodsSkuVo.getOnSalesNo();
                flag:
                for (PromotionActivityVo promotionActivityVo : goodPromotionalInfoVo.getActivityList()) {
                    PresellPromotionActivityVo presellPromotionActivityVo = (PresellPromotionActivityVo) promotionActivityVo;
                    if (purchaseGoodsSkuVo.getId().equals(presellPromotionActivityVo.getGoodSkuId())) {
                        purchaseGoodsSkuVo.setOnSalesNo(presellPromotionActivityVo.getPresellNum());
                        purchaseGoodsSkuVo.setCargoSkuStock(presellPromotionActivityVo.getPresellNum() + 1);
                        if (BoolType.T.name().equals(presellPromotionActivityVo.getLimitFlg())) {
                            purchaseGoodsSkuVo.setStartNum(1);
                            purchaseGoodsSkuVo.setLimitNum(presellPromotionActivityVo.getLimitNum());
                            purchaseGoodsSkuVo.setLimitFlg(presellPromotionActivityVo.getLimitFlg());
                            purchaseGoodsSkuVo.setLimitUnit(presellPromotionActivityVo.getLimitUnit());
                        }
                        if (PresellType.one.name().equals(presellPromotionActivityVo.getPresellType())) {
                            purchaseGoodsSkuVo.setSupplyPrice(presellPromotionActivityVo.getPresellAmount().toString());
                        } else {
                            purchaseGoodsSkuVo.setSupplyPrice(presellPromotionActivityVo.getFirstAmount().toString());
                        }

                        break flag;
                    }
                }
            }
            if (DateParseUtil.compareDates(new Date(), goodPromotionalInfoVo.getActivityStart())) {
                model.addAttribute("startFalg", true);
            } else {
                model.addAttribute("startFalg", false);
            }

            model.addAttribute("goods", goods);
            model.addAttribute("totalStock", totalStock);
            model.addAttribute("goodPromotionalInfo", goodPromotionalInfoVo);
            goodType = "presell";
        } else if ("sale".equals(goodPromotionalInfoVo.getActivityType())) {
            for (PurchaseGoodsSkuVo purchaseGoodsSkuVo : goods.getGoodSkuVoList()) {
                if (purchaseGoodsSkuVo.getOnSalesNo() <= purchaseGoodsSkuVo.getCargoSkuStock()) {
                    totalStock += purchaseGoodsSkuVo.getOnSalesNo();
                } else {
                    totalStock += purchaseGoodsSkuVo.getCargoSkuStock();
                }
                flag:
                for (PromotionActivityVo promotionActivityVo : goodPromotionalInfoVo.getActivityList()) {
                    if (!PromotionActivityType.sale.name().equals(promotionActivityVo.getActivityType())) {
                        continue ;
                    }
                    SalePromotionActivityVo salePromotionActivityVo = (SalePromotionActivityVo) promotionActivityVo;
                    if (purchaseGoodsSkuVo.getId().equals(salePromotionActivityVo.getGoodSkuId())) {
                        if (BoolType.T.name().equals(salePromotionActivityVo.getLimitFlg())) {
                            purchaseGoodsSkuVo.setLimitNum(salePromotionActivityVo.getLimitNum());
                            purchaseGoodsSkuVo.setLimitFlg(salePromotionActivityVo.getLimitFlg());
                            purchaseGoodsSkuVo.setLimitUnit(salePromotionActivityVo.getLimitUnit());
                        }
//                        purchaseGoodsSkuVo.setSupplyPrice(salePromotionActivityVo.getFinalAmount().toString());
                        break flag;
                    }
                }
            }
            if (totalStock < 0) {
                totalStock = 0;
            }

            model.addAttribute("totalStock", totalStock);
            model.addAttribute("goodPromotionalInfo", goodPromotionalInfoVo);
            model.addAttribute("goods", goods);
            goodType = "sale";
        } else {
            for (PurchaseGoodsSkuVo purchaseGoodsSkuVo : goods.getGoodSkuVoList()) {
                if (purchaseGoodsSkuVo.getOnSalesNo() <= purchaseGoodsSkuVo.getCargoSkuStock()) {
                    totalStock += purchaseGoodsSkuVo.getOnSalesNo();
                } else {
                    totalStock += purchaseGoodsSkuVo.getCargoSkuStock();
                }
            }
            if (totalStock < 0) {
                totalStock = 0;
            }
            model.addAttribute("goodPromotionalInfo", goodPromotionalInfoVo);
            model.addAttribute("totalStock", totalStock);
            model.addAttribute("goods", goods);
        }
        model.addAttribute("goodType", goodType);

        return "www/purchaseGoods/good_detail";
    }



    @RequestMapping("/goodDetail/{goodsId}.html")
    public  String  detail(@PathVariable String goodsId, Model model){
        String userId = AccountUtils.getCurrShopId(false);
        String storeLevel = AccountUtils.getCurrStoreLevelId();
        PurchaseGoodsVo goods= purchaseGoodsB2bService.getPcGoodsOnloadDetail(goodsId, storeLevel);
        if (StringUtils.isNotBlank(userId)) {
            //添加足迹
            goodsBrowseLogB2bService.addBrowseLog(goods, userId);
        }
        //查询是否参加活动
        PromotionGoodQuery promotionGoodQuery = new PromotionGoodQuery();
        List<BaseGood> baseGoodList = Lists.newArrayList();
        for (PurchaseGoodsSkuVo purchaseGoodsSkuVo : goods.getGoodSkuVoList()) {
            BaseGood baseGood = new BaseGood();
            baseGood.setGoodSkuId(purchaseGoodsSkuVo.getId());
            baseGood.setAmount(new BigDecimal(StringUtils.isNotBlank(purchaseGoodsSkuVo.getSupplyPrice()) ? purchaseGoodsSkuVo.getSupplyPrice() : "0"));
            baseGood.setGoodId(goods.getId());
            baseGoodList.add(baseGood);
        }
        promotionGoodQuery.setGoodList(baseGoodList);
        promotionGoodQuery.setOrderType(OrderType.normal);
        promotionGoodQuery.setOrderBizType(OrderBizType.purchase);
        PromotionInfoDetailVo goodPromotionalInfoVo = purchaseGoodsB2bService.getPromotionPriceInfo(promotionGoodQuery);
        model.addAttribute("shopId", userId);
        Integer totalStock = 0;
        if ("presell".equals(goodPromotionalInfoVo.getActivityType())) {
            for (PurchaseGoodsSkuVo purchaseGoodsSkuVo : goods.getGoodSkuVoList()) {
                flag:
                for (PromotionActivityVo promotionActivityVo : goodPromotionalInfoVo.getActivityList()) {
                    PresellPromotionActivityVo presellPromotionActivityVo = (PresellPromotionActivityVo) promotionActivityVo;
                    if (purchaseGoodsSkuVo.getId().equals(presellPromotionActivityVo.getGoodSkuId())) {
                        purchaseGoodsSkuVo.setOnSalesNo(presellPromotionActivityVo.getPresellNum());
                        purchaseGoodsSkuVo.setCargoSkuStock(presellPromotionActivityVo.getPresellNum() + 1);
                        if (BoolType.T.name().equals(presellPromotionActivityVo.getLimitFlg())) {
                            purchaseGoodsSkuVo.setStartNum(1);
                            purchaseGoodsSkuVo.setLimitNum(presellPromotionActivityVo.getLimitNum());
                            purchaseGoodsSkuVo.setLimitFlg(presellPromotionActivityVo.getLimitFlg());
                        }
                        break flag;
                    }
                }
            }

            for (PurchaseGoodsSkuVo purchaseGoodsSkuVo : goods.getGoodSkuVoList()) {
                totalStock += purchaseGoodsSkuVo.getOnSalesNo();
            }

            model.addAttribute("goods", goods);
            model.addAttribute("totalStock", totalStock);
            model.addAttribute("goodPromotionalInfo", goodPromotionalInfoVo);
            return "www/purchaseGoods/product_presell_detail";
        } else {
            for (PurchaseGoodsSkuVo purchaseGoodsSkuVo : goods.getGoodSkuVoList()) {
                if (purchaseGoodsSkuVo.getOnSalesNo() <= purchaseGoodsSkuVo.getCargoSkuStock()) {
                    totalStock += purchaseGoodsSkuVo.getOnSalesNo();
                } else {
                    totalStock += purchaseGoodsSkuVo.getCargoSkuStock();
                }
            }
            if (totalStock < 0) {
                totalStock = 0;
            }
            model.addAttribute("totalStock", totalStock);
            model.addAttribute("goods", goods);
            return "www/purchaseGoods/product_details";
        }
    }

    @RequestMapping("/presellDetail")
    public  String  presellDetail(String goodsId, Model model){
        String storeLevel = AccountUtils.getCurrStoreLevelId();
        PurchaseGoodsVo goods= purchaseGoodsB2bService.getPcGoodsOnloadDetail(goodsId, storeLevel);
        model.addAttribute("goods", goods);
        return "www/purchaseGoods/product_presell_detail";
    }

    @RequestMapping("/getPromotionPriceInfo")
    @ResponseBody
    public ResultData getPromotionPriceInfo(String modelJsonStr) {
        PromotionGoodQuery promotionGoodQuery = new PromotionGoodQuery();
        if (StringUtils.isBlank(modelJsonStr)) {
            return ResultData.createFail();
        }
        promotionGoodQuery = JsonUtils.toObject(modelJsonStr, PromotionGoodQuery.class);
        return ResultData.createSuccess(purchaseGoodsB2bService.getGoodsPromotionPrice(promotionGoodQuery));

    }




    @RequestMapping("/getPcGoodsOnloadDetail")
    @ResponseBody
    public ResultData getPcGoodsOnloadDetail(String goodsId, Model model){
        String storeLevel = AccountUtils.getCurrStoreLevelId();
        PurchaseGoodsVo goods= purchaseGoodsB2bService.getPcGoodsOnloadDetail(goodsId, storeLevel);
        return ResultData.createSuccess("goods", goods);
    }

    @RequestMapping("/goodDetail")
    @ResponseBody
    public ResultData goodDetail(String goodsId, Model model){
        PurchaseGoodsVo goods= purchaseGoodsB2bService.getGoodsDetail(goodsId);
        return ResultData.createSuccess("goods", goods);
    }


    @RequestMapping("/purchaseEvaluationPageInfo")
    @ResponseBody
    public  ResultData purchaseEvaluationPageInfo(HttpServletRequest request, String goodsId, Model model){
        PurchaseGoodsEvaluationQuery evaluationQuery =new PurchaseGoodsEvaluationQuery();
        PurchaseGoodsEvaluationCountVo pgec = new PurchaseGoodsEvaluationCountVo();
        try {
            evaluationQuery.setGoodId(goodsId);
            pgec =purchaseGoodsB2bService.getEvaluationCount(evaluationQuery);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.createFail();
        }
        return ResultData.createSuccess("evaluationPageInfo", pgec);
    }

    @RequestMapping("/purchaseEvaluationPageList")
    @ResponseBody
    public ResultData purchaseEvaluationPageList(HttpServletRequest request, String goodsId, String type, Model model){
        PurchaseGoodsEvaluationQuery evaluationQuery = new PurchaseGoodsEvaluationQuery();
        List<PurchaseGoodsEvaluationVo> list = Lists.newArrayList();
        try {
            evaluationQuery.bulidPurchaseGoodsQuery(request);
            evaluationQuery.setGoodId(goodsId);
            evaluationQuery.setType(Integer.parseInt(type));
            list = purchaseGoodsB2bService.getEvaluationList(evaluationQuery);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.createFail();
        }
        return ResultData.createSuccess("evaluationPageList", list);
    }

    @RequestMapping("/searchList")
    public String searchList(HttpServletRequest request, Model model) {
        PurchaseGoodsQuery purchaseGoodsQuery = new PurchaseGoodsQuery();
        Integer count = 0;
        List<BaseGoodVo> purchaseGoodsVos = Lists.newArrayList();
        List<PurchaseClassifyVo> classifyVoList = Lists.newArrayList();
        List<CargoBrandVo> brandVoList = Lists.newArrayList();
        List<PurchaseClassifyAttrVo> attrVoList = Lists.newArrayList();
        PurchaseClassify purchaseClassify = new PurchaseClassify();
        try {
            purchaseGoodsQuery.bulidPurchaseGoodsQuery(request);
            String storeLevelId = AccountUtils.getCurrStoreLevelId();
            purchaseGoodsQuery.setClientType(ClientType.pc);
            purchaseGoodsQuery.setStoreLevelId(storeLevelId);
            ResultData paramResultData = purchaseGoodsB2bService.getListSearchParams(purchaseGoodsQuery.getClassifyId(), storeLevelId);
            Map<String, Object> map = (Map<String, Object>) paramResultData.getData();
            classifyVoList = (List<PurchaseClassifyVo>) map.get("classifyList");
            brandVoList = (List<CargoBrandVo>) map.get("brandList");
            attrVoList = (List<PurchaseClassifyAttrVo>) map.get("attrList");
            purchaseClassify = (PurchaseClassify) map.get("classify");
            purchaseGoodsVos = purchaseGoodsB2bService.getSearchGoodsList(purchaseGoodsQuery);
            count = purchaseGoodsB2bService.getSearchGoodsCount(purchaseGoodsQuery);
            purchaseGoodsQuery.setTotalRecods(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("classifyVoList", classifyVoList);
        model.addAttribute("brandVoList", brandVoList);
        model.addAttribute("attrVoList", attrVoList);
        model.addAttribute("purchaseClassify", purchaseClassify);
        model.addAttribute("purchaseGoodsQuery", purchaseGoodsQuery);
        model.addAttribute("purchaseGoodsVos", purchaseGoodsVos);
        return "www/purchaseGoods/list";
    }

    @RequestMapping("")
    public String listAll(HttpServletRequest request, Model model) {
        PurchaseGoodsQuery purchaseGoodsQuery = new PurchaseGoodsQuery();
        Integer count = 0;
        List<BaseGoodVo> purchaseGoodsVos = Lists.newArrayList();
        List<PurchaseClassifyVo> classifyVoList = Lists.newArrayList();
        List<CargoBrandVo> brandVoList = Lists.newArrayList();
        List<PurchaseClassifyAttrVo> attrVoList = Lists.newArrayList();
        PurchaseClassify purchaseClassify = new PurchaseClassify();
        String shopId = null;
        try {
            purchaseGoodsQuery.bulidPurchaseGoodsQuery(request);
            String storeLevelId = AccountUtils.getCurrStoreLevelId();
            shopId = AccountUtils.getCurrShopId(false);
            purchaseGoodsQuery.setClientType(ClientType.pc);
            purchaseGoodsQuery.setStoreLevelId(storeLevelId);
            ResultData paramResultData = purchaseGoodsB2bService.getListSearchParams(purchaseGoodsQuery.getClassifyId(), storeLevelId);
            Map<String, Object> map = (Map<String, Object>) paramResultData.getData();
            classifyVoList = (List<PurchaseClassifyVo>) map.get("classifyList");
            brandVoList = (List<CargoBrandVo>) map.get("brandList");
            attrVoList = (List<PurchaseClassifyAttrVo>) map.get("attrList");
            purchaseClassify = (PurchaseClassify) map.get("classify");
            purchaseGoodsVos = purchaseGoodsB2bService.getSearchGoodsList(purchaseGoodsQuery);
            count = purchaseGoodsB2bService.getSearchGoodsCount(purchaseGoodsQuery);
            purchaseGoodsQuery.setTotalRecods(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        purchaseGoodsQuery.setStoreLevelId(null);
        model.addAttribute("shopId", shopId);
        model.addAttribute("classifyVoList", classifyVoList);
        model.addAttribute("brandVoList", brandVoList);
        model.addAttribute("attrVoList", attrVoList);
        model.addAttribute("purchaseClassify", purchaseClassify);
        model.addAttribute("purchaseGoodsQuery", purchaseGoodsQuery);
        model.addAttribute("purchaseGoodsVos", purchaseGoodsVos);
        return "www/purchaseGoods/list";
    }


    @RequestMapping("/list")
    public String list(HttpServletRequest request, Model model) {
        PurchaseGoodsQuery purchaseGoodsQuery = new PurchaseGoodsQuery();
        Integer count = 0;
        List<BaseGoodVo> purchaseGoodsVos = Lists.newArrayList();
        List<PurchaseClassifyVo> classifyVoList = Lists.newArrayList();
        List<CargoBrandVo> brandVoList = Lists.newArrayList();
        List<PurchaseClassifyAttrVo> attrVoList = Lists.newArrayList();
        PurchaseClassify purchaseClassify = new PurchaseClassify();
        String shopId = null;
        try {
            purchaseGoodsQuery.bulidPurchaseGoodsQuery(request);
            String storeLevelId = AccountUtils.getCurrStoreLevelId();
            shopId = AccountUtils.getCurrShopId(false);
            purchaseGoodsQuery.setClientType(ClientType.pc);
            purchaseGoodsQuery.setStoreLevelId(storeLevelId);
            ResultData paramResultData = purchaseGoodsB2bService.getListSearchParams(purchaseGoodsQuery.getClassifyId(), storeLevelId);
            Map<String, Object> map = (Map<String, Object>) paramResultData.getData();
            classifyVoList = (List<PurchaseClassifyVo>) map.get("classifyList");
            brandVoList = (List<CargoBrandVo>) map.get("brandList");
            attrVoList = (List<PurchaseClassifyAttrVo>) map.get("attrList");
            purchaseClassify = (PurchaseClassify) map.get("classify");
            purchaseGoodsVos = purchaseGoodsB2bService.getSearchGoodsList(purchaseGoodsQuery);
            count = purchaseGoodsB2bService.getSearchGoodsCount(purchaseGoodsQuery);
            purchaseGoodsQuery.setTotalRecods(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        purchaseGoodsQuery.setStoreLevelId(null);
        model.addAttribute("shopId", shopId);
        model.addAttribute("classifyVoList", classifyVoList);
        model.addAttribute("brandVoList", brandVoList);
        model.addAttribute("attrVoList", attrVoList);
        model.addAttribute("purchaseClassify", purchaseClassify);
        model.addAttribute("purchaseGoodsQuery", purchaseGoodsQuery);
        model.addAttribute("purchaseGoodsVos", purchaseGoodsVos);
        return "www/purchaseGoods/list";
    }

    @RequestMapping("/cuxiaoList")
    @ResponseBody
    public ResultData cuxiaoList(HttpServletRequest request, Model model) {
        BaseArticleInfo baseArticleInfo = new BaseArticleInfo();
        try {
            baseArticleInfo = purchaseGoodsB2bService.cuxiaoList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultData.createSuccess("baseArticleInfo", baseArticleInfo);
    }


     @RequestMapping("/purchaseGoodsList")
     @ResponseBody
     public ResultData purchaseGoodsList(HttpServletRequest request) {
         PurchaseGoodsQuery purchaseGoodsQuery = new PurchaseGoodsQuery();
         List<BaseGoodVo> purchaseGoodsVos = Lists.newArrayList();
         Integer count = 0;
         ObjectMapper objectMapper = new ObjectMapper();
        try {
            purchaseGoodsQuery.bulidPurchaseGoodsQuery(request);
            purchaseGoodsQuery.setClientType(ClientType.pc);
            purchaseGoodsVos = purchaseGoodsB2bService.getSearchGoodsList(purchaseGoodsQuery);
            count = purchaseGoodsB2bService.getSearchGoodsCount(purchaseGoodsQuery);
            purchaseGoodsQuery.setTotalRecods(count);
            purchaseGoodsQuery.setPurchaseGoodsVos(purchaseGoodsVos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultData.createSuccess("purchaseGoods", purchaseGoodsQuery);
     }
}
