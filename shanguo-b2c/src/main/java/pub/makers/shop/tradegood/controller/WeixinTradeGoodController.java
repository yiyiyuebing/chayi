package pub.makers.shop.tradegood.controller;

import com.dev.base.json.JsonUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseGood.enums.PageTplClassify;
import pub.makers.shop.baseGood.pojo.ChangeGoodNumQuery;
import pub.makers.shop.baseGood.pojo.TplQuery;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.pojo.PromotionGoodQuery;
import pub.makers.shop.cargo.entity.vo.CargoBrandVo;
import pub.makers.shop.cargo.vo.GoodPageTplModelVo;
import pub.makers.shop.cargo.vo.GoodPageTplVo;
import pub.makers.shop.cart.vo.ChangeGoodNumVo;
import pub.makers.shop.promotion.vo.GoodPromotionalInfoVo;
import pub.makers.shop.tradeGoods.entity.Image;
import pub.makers.shop.tradeGoods.vo.*;
import pub.makers.shop.tradegood.service.WeixinTradeGoodAppService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/5/25.
 */
@Controller
@RequestMapping("weixin/good")
public class WeixinTradeGoodController {
    @Autowired
    private WeixinTradeGoodAppService weixinTradeGoodAppService;

    /**
     * 商品列表
     *
     * @return
     */
    @RequestMapping("getSearchGoodListSer")
    @ResponseBody
    public ResultData getSearchGoodListSer(String json, String userId) {
        ValidateUtils.notNull(json, "请选择查询条件");

        GoodSearchInfo goodSearchInfo = JsonUtils.toObject(json, GoodSearchInfo.class);
        goodSearchInfo.setStoreLevelId(weixinTradeGoodAppService.getStoreLevelId(userId));
        goodSearchInfo.setShopId(userId);
        List<BaseGoodVo> list = weixinTradeGoodAppService.getSearchGoodListSer(goodSearchInfo);
        return ResultData.createSuccess(list);
    }

    /**
     * 商品列表
     *
     * @return
     */
    @RequestMapping("getListSearchParams")
    @ResponseBody
    public ResultData getListSearchParams() {
        return weixinTradeGoodAppService.getListSearchParams();
    }

    /**
     * 商品促销信息
     *
     * @return
     */
    @RequestMapping("applyPromotionRule")
    @ResponseBody
    public ResultData applyPromotionRule(String json, HttpServletResponse response) {
        ValidateUtils.notNull(json, "请选择查询条件");

        PromotionGoodQuery query = JsonUtils.toObject(json, PromotionGoodQuery.class);
        Map<String, GoodPromotionalInfoVo> data = weixinTradeGoodAppService.applyPromotionRule(query);
        return ResultData.createSuccess(data);
    }

    /**
     * 商品详情
     *
     * @param goodId
     * @return
     */
    @RequestMapping("getGoodDetail")
    @ResponseBody
    public ResultData getGoodDetailCon(String goodId, String userId) {
        ValidateUtils.notNull(goodId, "商品id为空");

        String storeLevelId = weixinTradeGoodAppService.getStoreLevelId(userId);
        TradeGoodVo vo = weixinTradeGoodAppService.getShanguoGoodDetailSer(goodId, storeLevelId);
        return ResultData.createSuccess(vo);
    }

    /**
     * 商品sku详情
     *
     * @param response
     * @return
     */
    @RequestMapping("getGoodSkuDetail")
    @ResponseBody
    public ResultData getGoodSkuDetailCon(String skuId, HttpServletResponse response) {
        ValidateUtils.notNull(skuId, "商品skuId为空");

        TradeGoodSkuVo vo = weixinTradeGoodAppService.getGoodSkuDetail(skuId);
        return ResultData.createSuccess(vo);
    }

    /**
     * 根据商品ID查询商品大图相册
     *
     * @param goodId
     * @return
     */
    @RequestMapping("listDetailImage")
    @ResponseBody
    public ResultData listDetailImage(HttpServletResponse response, String goodId) {
        ValidateUtils.notNull(goodId, "商品id为空");

        List<Image> imageList = weixinTradeGoodAppService.listDetailImageByGoodId(goodId);
        return ResultData.createSuccess(imageList);
    }

    /**
     * 首页分类
     *
     * @param limit
     * @param response
     * @return
     */
    @RequestMapping("goodColumn/getAllGoodColumns")
    @ResponseBody
    public ResultData getAllGoodColumns(@RequestParam(defaultValue = "6") String limit, HttpServletResponse response) {
        List<GoodsColumnVo> goodsColumns = weixinTradeGoodAppService.getAllGoodsColumn(Integer.parseInt(limit));
        return ResultData.createSuccess(goodsColumns);
    }

    /**
     * 促销区域详情
     */
    @RequestMapping("goodColumn/getGoodsColumnDetail")
    @ResponseBody
    public ResultData getGoodsColumnDetail(String id) {
        ValidateUtils.notNull(id, "促销区域id为空");

        GoodsColumnVo vo = weixinTradeGoodAppService.getGoodsColumnDetail(id);
        return ResultData.createSuccess(vo);
    }

    /**
     * 商品主题馆列表
     *
     * @param response
     * @return
     */
    @RequestMapping("goodColumn/getAllGoodTheme")
    @ResponseBody
    public ResultData getAllGoodTheme(@RequestParam(defaultValue = "8") String limit, HttpServletResponse response) {
        List<GoodsThemeVo> themeList = weixinTradeGoodAppService.selectGoodsThemeForIndex(Integer.parseInt(limit));
        return ResultData.createSuccess(themeList);
    }

    /**
     * 商品分类首页
     * return 一级货品分类，品牌，用途
     */
    @RequestMapping("sortTypeIndex")
    @ResponseBody
    public ResultData sortTypeIndex(HttpServletResponse response) {
        ResultData resultData = weixinTradeGoodAppService.sortTypeIndex();
        return resultData;
    }

    /**
     * 分类菜单
     *
     * @param parentId
     * @param response
     * @return
     */
    @RequestMapping("sortTypeByParentId")
    @ResponseBody
    public ResultData sortTypeByParentId(String parentId, HttpServletResponse response) {
        ValidateUtils.notNull(parentId, "请选择分类");

        List<TradeGoodsClassifyVo> type = weixinTradeGoodAppService.getClassifyListByParentId(parentId);
        return ResultData.createSuccess(type);
    }

    /**
     * 一级分类菜单
     *
     * @param response
     * @return
     */
    @RequestMapping("firstSortType")
    @ResponseBody
    public ResultData firstSortType(HttpServletResponse response) {
        List<TradeGoodsClassifyVo> type = weixinTradeGoodAppService.getClassifyListAll();
        List<Map<String, Object>> typeList = Lists.newArrayList();
        for (TradeGoodsClassifyVo vo : type) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("classify", vo.getId());
            map.put("imgUrl", vo.getImgUrl());
            map.put("text", vo.getName());
            typeList.add(map);
        }
        return ResultData.createSuccess(typeList);
    }

    /**
     * 商品分类首页
     * 根据classify品牌筛选
     */
    @RequestMapping("sortTypeIndexBrand")
    @ResponseBody
    public ResultData sortTypeIndexBrand(HttpServletResponse response, String classify) {
        ValidateUtils.notNull(classify, "请选择分类");

        List<CargoBrandVo> list = weixinTradeGoodAppService.getCargoBrandList(classify, null);//品牌
        return ResultData.createSuccess(list);
    }

    /**
     * 计算商品变化数量
     */
    @RequestMapping("changeGoodNum")
    @ResponseBody
    public ResultData changeGoodNum(String modelJson, HttpServletResponse response) {
        ValidateUtils.notNull(modelJson, "请输入参数");

        ChangeGoodNumQuery query = JsonUtils.toObject(modelJson, ChangeGoodNumQuery.class);
        ChangeGoodNumVo changeGoodNumVo = weixinTradeGoodAppService.changeGoodNum(query);
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
        tplQuery.setPageTplClassify(PageTplClassify.mobileb2c.name());
        tplQuery.setOrderBizType(OrderBizType.trade.name());
        tplQuery.setClientType(ClientType.mobile);
        GoodPageTplVo goodPageTplVo = weixinTradeGoodAppService.getPageTpl(tplQuery);
        List<GoodPageTplModelVo> modelVoList = Lists.newArrayList();
        if (goodPageTplVo != null && goodPageTplVo.getModelList() != null) {
            modelVoList = goodPageTplVo.getModelList();
        }
        return ResultData.createSuccess("modelList", modelVoList);
    }
}
