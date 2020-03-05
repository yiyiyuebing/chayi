package pub.makers.shop.purchasegood.controller;

import com.dev.base.json.JsonUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseGood.enums.PageTplClassify;
import pub.makers.shop.baseGood.pojo.ChangeGoodNumQuery;
import pub.makers.shop.baseGood.pojo.TplQuery;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.cargo.entity.vo.CargoMaterialLibraryVo;
import pub.makers.shop.cargo.vo.GoodPageTplModelVo;
import pub.makers.shop.cargo.vo.GoodPageTplVo;
import pub.makers.shop.cart.vo.ChangeGoodNumVo;
import pub.makers.shop.purchaseGoods.pojo.PurchaseGoodsEvaluationQuery;
import pub.makers.shop.purchaseGoods.pojo.PurchaseGoodsQuery;
import pub.makers.shop.purchaseGoods.vo.PurchaseClassifyVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsEvaluationCountVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsEvaluationVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsVo;
import pub.makers.shop.purchasegood.service.PurchaseGoodAppService;
import pub.makers.shop.user.utils.AccountUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/6/2.
 */
@Controller
@RequestMapping("purchaseGood")
public class PurchaseGoodController {
    @Autowired
    private PurchaseGoodAppService purchaseGoodAppService;

    /**
     * 分类列表
     */
    @RequestMapping("getClassifyList")
    @ResponseBody
    public ResultData getClassifyList() {
        String storeLevelId = AccountUtils.getCurrStoreLevelId();
        List<PurchaseClassifyVo> voList = purchaseGoodAppService.getClassifyList(storeLevelId);
        return ResultData.createSuccess("classifyList", voList);
    }

    /**
     * 列表查询条件
     */
    @RequestMapping("getListSearchParams")
    @ResponseBody
    public ResultData getListSearchParams(String classifyId) {
        String storeLevelId = AccountUtils.getCurrStoreLevelId();
        ResultData resultData = purchaseGoodAppService.getListSearchParams(classifyId, storeLevelId);
        return resultData;
    }

    /**
     * 商品列表
     */
    @RequestMapping("getGoodsList")
    @ResponseBody
    public ResultData purchaseGoodsList(String modelJson) {
        ValidateUtils.notNull(modelJson, "参数不能为空");

        PurchaseGoodsQuery purchaseGoodsQuery = JsonUtils.toObject(modelJson, PurchaseGoodsQuery.class);
        String storeLevelId = AccountUtils.getCurrStoreLevelId();
        purchaseGoodsQuery.setStoreLevelId(storeLevelId);
        purchaseGoodsQuery.setClientType(ClientType.mobile);
        List<BaseGoodVo> purchaseGoodsVos = purchaseGoodAppService.getSearchGoodsList(purchaseGoodsQuery);
        Integer count = purchaseGoodAppService.getSearchGoodsCount(purchaseGoodsQuery);
        purchaseGoodsQuery.setTotalRecods(count);
        purchaseGoodsQuery.setPurchaseGoodsVos(purchaseGoodsVos);
        return ResultData.createSuccess("purchaseGoods", purchaseGoodsQuery);
    }

    /**
     * 商品详情
     */
    @RequestMapping("getGoodsDetail")
    @ResponseBody
    public ResultData getGoodsDetail(String goodId) {
        String storeLevelId = AccountUtils.getCurrStoreLevelId();
        PurchaseGoodsVo vo = purchaseGoodAppService.getGoodsDetail(goodId, storeLevelId);
        return ResultData.createSuccess(vo);
    }

    /**
     * 评论列表
     */
    @RequestMapping("getEvaluationList")
    @ResponseBody
    public ResultData getEvaluationList(String modelJson) {
        PurchaseGoodsEvaluationQuery query = JsonUtils.toObject(modelJson, PurchaseGoodsEvaluationQuery.class);
        List<PurchaseGoodsEvaluationVo> voList = purchaseGoodAppService.getEvaluationList(query);
        PurchaseGoodsEvaluationCountVo countVo = purchaseGoodAppService.getEvaluationCount(query);
        Map<String, Object> data = Maps.newHashMap();
        data.put("evaluationList", voList);
        data.put("count", countVo.getTotalCount());
        return ResultData.createSuccess(data);
    }

    /**
     * 评论列表数量
     */
    @RequestMapping("getEvaluationCount")
    @ResponseBody
    public ResultData getEvaluationCount(String modelJson) {
        PurchaseGoodsEvaluationQuery query = JsonUtils.toObject(modelJson, PurchaseGoodsEvaluationQuery.class);
        PurchaseGoodsEvaluationCountVo vo = purchaseGoodAppService.getEvaluationCount(query);
        return ResultData.createSuccess(vo);
    }

    /**
     * {purchaseGood/getMaterialByGoodsId}
     *
     * @param goodsId
     * @return
     */
    @RequestMapping("getMaterialByGoodsId")
    @ResponseBody
    public ResultData getMaterialByGoodsId(String goodsId, OrderBizType orderBizType) {
        List<CargoMaterialLibraryVo> voList = purchaseGoodAppService.getMaterialByGoodsId(goodsId, orderBizType);
        return ResultData.createSuccess("materialList", voList);
    }


    @RequestMapping("getSimpleDetail")
    @ResponseBody
    public ResultData getSimpleDetail(String goodId) {
        BaseGoodVo baseGoodVo = purchaseGoodAppService.getSimpleDetail(goodId);
        return ResultData.createSuccess(baseGoodVo);
    }

    /**
     * 商品利润
     */
    @RequestMapping("getGoodProfit")
    @ResponseBody
    public ResultData getGoodProfit(String goodId) {
        String storeLevelId = AccountUtils.getCurrStoreLevelId();
        BigDecimal profit = purchaseGoodAppService.getGoodProfit(goodId, storeLevelId);
        return ResultData.createSuccess("profit", profit.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    }

    @RequestMapping("changeGoodNum")
    @ResponseBody
    public ResultData changeGoodNum(String modelJson) {
        ValidateUtils.notNull(modelJson, "参数不能为空");

        ChangeGoodNumQuery query = JsonUtils.toObject(modelJson, ChangeGoodNumQuery.class);
        String storeLevelId = AccountUtils.getCurrStoreLevelId();
        query.setStoreLevelId(storeLevelId);
        ChangeGoodNumVo changeGoodNumVo = purchaseGoodAppService.changeGoodNum(query);
        return ResultData.createSuccess(changeGoodNumVo);
    }

    /**
     * 商品详情页面模板
     */
    @RequestMapping("getGoodDetailTpl")
    @ResponseBody
    public ResultData getGoodDetailTpl(String modelJson) {
        ValidateUtils.notNull(modelJson, "参数不能为空");

        TplQuery tplQuery = JsonUtils.toObject(modelJson, TplQuery.class);
        tplQuery.setPageTplClassify(PageTplClassify.mobileb2b.name());
        tplQuery.setOrderBizType(OrderBizType.purchase.name());
        tplQuery.setClientType(ClientType.mobile);
        GoodPageTplVo goodPageTplVo = purchaseGoodAppService.getPageTpl(tplQuery);
        List<GoodPageTplModelVo> modelVoList = Lists.newArrayList();
        if (goodPageTplVo != null && goodPageTplVo.getModelList() != null) {
            modelVoList = goodPageTplVo.getModelList();
        }
        return ResultData.createSuccess("modelList", modelVoList);
    }
}
