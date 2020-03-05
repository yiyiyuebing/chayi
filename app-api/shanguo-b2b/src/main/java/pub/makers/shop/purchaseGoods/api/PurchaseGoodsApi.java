package pub.makers.shop.purchaseGoods.api;

import com.dev.base.json.JsonUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
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
import pub.makers.shop.cargo.vo.GoodPageTplModelVo;
import pub.makers.shop.cargo.vo.GoodPageTplVo;
import pub.makers.shop.cart.vo.ChangeGoodNumVo;
import pub.makers.shop.purchaseGoods.pojo.PurchaseGoodsEvaluationQuery;
import pub.makers.shop.purchaseGoods.pojo.PurchaseGoodsQuery;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsB2bService;
import pub.makers.shop.purchaseGoods.vo.PurchaseClassifyVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsEvaluationCountVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsEvaluationVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsVo;
import pub.makers.shop.user.utils.AccountUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/6/30.
 */
@Controller
@RequestMapping("weixin/purchaseGoods")
public class PurchaseGoodsApi {
    @Autowired
    private PurchaseGoodsB2bService purchaseGoodsB2bService;

    /**
     * 一级分类列表
     */
    @RequestMapping("getFirstClassifyList")
    @ResponseBody
    public ResultData getFirstClassifyList() {
        String storeLevelId = AccountUtils.getCurrStoreLevelId();
        List<PurchaseClassifyVo> classifyVoList = purchaseGoodsB2bService.getFirstClassifyList(storeLevelId);
        return ResultData.createSuccess("classifyList", classifyVoList);
    }

    /**
     * 分类列表
     */
    @RequestMapping("getClassifyList")
    @ResponseBody
    public ResultData getListSearchParams() {
        String storeLevelId = AccountUtils.getCurrStoreLevelId();
        List<PurchaseClassifyVo> classifyVoList = purchaseGoodsB2bService.getClassifyList(storeLevelId);
        return ResultData.createSuccess("classifyList", classifyVoList);
    }

    /**
     * 列表查询条件
     */
    @RequestMapping("getListSearchParams")
    @ResponseBody
    public ResultData getListSearchParams(String classifyId) {
        String storeLevelId = AccountUtils.getCurrStoreLevelId();
        ResultData resultData = purchaseGoodsB2bService.getListSearchParams(classifyId, storeLevelId);
        return resultData;
    }

    /**
     * 商品列表
     */
    @RequestMapping("getSearchGoodsList")
    @ResponseBody
    public ResultData getSearchGoodsList(String modelJson) {
        ValidateUtils.notNull(modelJson, "参数不能为空");

        String storeLevelId = AccountUtils.getCurrStoreLevelId();
        PurchaseGoodsQuery purchaseGoodsQuery = JsonUtils.toObject(modelJson, PurchaseGoodsQuery.class);
        purchaseGoodsQuery.setStoreLevelId(storeLevelId);
        purchaseGoodsQuery.setClientType(ClientType.mobile);
        if (StringUtils.isEmpty(purchaseGoodsQuery.getSaleNumSort()) && StringUtils.isEmpty(purchaseGoodsQuery.getPriceSort()) && StringUtils.isEmpty(purchaseGoodsQuery.getCreateTimeSort())) {
            purchaseGoodsQuery.setOrderIndex("1");
        }
        List<BaseGoodVo> voList = purchaseGoodsB2bService.getSearchGoodsList(purchaseGoodsQuery);
        return ResultData.createSuccess("goodsList", voList);
    }

    /**
     * 商品详情
     */
    @RequestMapping("getGoodsDetail")
    @ResponseBody
    public ResultData getGoodsDetail(String goodId) {
        String storeLevelId = AccountUtils.getCurrStoreLevelId();
        PurchaseGoodsVo vo = purchaseGoodsB2bService.getGoodsDetail(goodId, storeLevelId);
        return ResultData.createSuccess(vo);
    }

    /**
     * 评论列表
     */
    @RequestMapping("getEvaluationList")
    @ResponseBody
    public ResultData getEvaluationList(String modelJson) {
        PurchaseGoodsEvaluationQuery query = JsonUtils.toObject(modelJson, PurchaseGoodsEvaluationQuery.class);
        List<PurchaseGoodsEvaluationVo> voList = purchaseGoodsB2bService.getEvaluationList(query);
        PurchaseGoodsEvaluationCountVo countVo = purchaseGoodsB2bService.getEvaluationCount(query);
        Map<String, Object> data = Maps.newHashMap();
        data.put("evaluationList", voList);
        data.put("count", countVo.getTotalCount());
        return ResultData.createSuccess(data);
    }

    /**
     * 计算商品变化数量
     */
    @RequestMapping("changeGoodNum")
    @ResponseBody
    public ResultData changeGoodNum(String modelJson) {
        ValidateUtils.notNull(modelJson, "参数不能为空");

        ChangeGoodNumQuery query = JsonUtils.toObject(modelJson, ChangeGoodNumQuery.class);
        String storeLevelId = AccountUtils.getCurrStoreLevelId();
        query.setStoreLevelId(storeLevelId);
        query.setOrderBizType(OrderBizType.purchase);
        ChangeGoodNumVo changeGoodNumVo = purchaseGoodsB2bService.changeGoodNum(query);
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
        String storeLevelId = AccountUtils.getCurrStoreLevelId();
        tplQuery.setStoreLevelId(storeLevelId);
        GoodPageTplVo goodPageTplVo = purchaseGoodsB2bService.getPageTpl(tplQuery);
        List<GoodPageTplModelVo> modelVoList = Lists.newArrayList();
        if (goodPageTplVo != null && goodPageTplVo.getModelList() != null) {
            modelVoList = goodPageTplVo.getModelList();
        }
        return ResultData.createSuccess("modelList", modelVoList);
    }
}
