package pub.makers.shop.index.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseGood.enums.PageTplClassify;
import pub.makers.shop.baseGood.pojo.TplQuery;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.cargo.vo.GoodPageTplModelVo;
import pub.makers.shop.cargo.vo.GoodPageTplVo;
import pub.makers.shop.index.entity.IndexAdImages;
import pub.makers.shop.index.enums.IndexAdPlatform;
import pub.makers.shop.index.enums.IndexAdPost;
import pub.makers.shop.index.service.IndexB2bService;
import pub.makers.shop.index.vo.IndentMobileModuleVo;
import pub.makers.shop.purchaseGoods.entity.PurchaseSearchKeyword;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsB2bService;
import pub.makers.shop.user.utils.AccountUtils;

import java.util.List;

@Controller
@RequestMapping("weixin/index")
public class IndexWeixinApi {

	@Autowired
    private IndexB2bService indexB2bService;
	@Autowired
	private PurchaseGoodsB2bService purchaseGoodsB2bService;
	
	/**
	 * 首页幻灯片轮播图
	 * @return
	 */
	@RequestMapping("ad")
	@ResponseBody
	public ResultData indexAd(){
		
		List<IndexAdImages> mainSliders = indexB2bService.getIndexAdImages(IndexAdPost.mIndexMain, null, 10, IndexAdPlatform.h5);
		List<IndexAdImages> subSliders = indexB2bService.getIndexAdImages(IndexAdPost.mIndexSub, null, 10, IndexAdPlatform.h5);
		
		return ResultData.createSuccess("main", mainSliders).put("sub", subSliders);
	}
	
	/**
	 * 首页楼层
	 * @return
	 */
	@RequestMapping("indexFloor")
	@ResponseBody
	public ResultData indexFloor(){
		// 微信B2B服务
		String storeLevelId = AccountUtils.getCurrStoreLevelId();
		List<IndentMobileModuleVo> moduleVoList = indexB2bService.getIndexFloorList(storeLevelId);
		return ResultData.createSuccess("floorList", moduleVoList);
	}

	/**
	 * 首页模板
	 * @return
	 */
	@RequestMapping("indexTpl")
	@ResponseBody
	public ResultData indexTpl(){
		TplQuery tplQuery = new TplQuery();
		tplQuery.setPageTplClassify(PageTplClassify.mobileb2bbottom.name());
		tplQuery.setOrderBizType(OrderBizType.purchase.name());
		tplQuery.setClientType(ClientType.mobile);
		String storeLevelId = AccountUtils.getCurrStoreLevelId();
		tplQuery.setStoreLevelId(storeLevelId);
		GoodPageTplVo goodPageTplVo = purchaseGoodsB2bService.getPageTpl(tplQuery);
		GoodPageTplModelVo modelVo = null;
		if (goodPageTplVo != null && goodPageTplVo.getModelList() != null && !goodPageTplVo.getModelList().isEmpty()) {
			modelVo = goodPageTplVo.getModelList().get(0);
		}
		return ResultData.createSuccess(modelVo);
	}

	/**
	 * 搜索关键词
	 */
	@RequestMapping("headKeyword")
	@ResponseBody
	public ResultData headKeyword() {
		List<PurchaseSearchKeyword> purchaseSearchKeywords = indexB2bService.getKeywordList(6, IndexAdPlatform.h5);
		return ResultData.createSuccess(purchaseSearchKeywords);
	}
}
