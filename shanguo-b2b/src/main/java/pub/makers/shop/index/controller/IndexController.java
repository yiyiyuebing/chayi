package pub.makers.shop.index.controller;

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.index.entity.IndexAdImages;
import pub.makers.shop.index.enums.IndexAdPlatform;
import pub.makers.shop.index.enums.IndexAdPost;
import pub.makers.shop.index.service.IndexB2bService;
import pub.makers.shop.index.vo.BaseArticleInfo;
import pub.makers.shop.purchaseGoods.entity.PurchaseSearchKeyword;
import pub.makers.shop.user.utils.AccountUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/6/2.
 */
@Controller
public class IndexController {

    private Logger logger = Logger.getLogger(IndexController.class);

    @Autowired
    private IndexB2bService indexB2bService;

    @RequestMapping("/search/headKeyword")
    @ResponseBody
    public ResultData headKeyword() {
        List<PurchaseSearchKeyword> purchaseSearchKeywords = indexB2bService.getKeywordList(7, IndexAdPlatform.pc);
        return ResultData.createSuccess(purchaseSearchKeywords);
    }

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("mainsliderAds", indexB2bService.getIndexAdImages(IndexAdPost.mainslider, null, 5, IndexAdPlatform.pc));
        model.addAttribute("subimagesAds", indexB2bService.getIndexAdImages(IndexAdPost.subimages, null, 4, IndexAdPlatform.pc));
        return "www/index/index";
    }
    
    @RequestMapping("/index")
    public String index2(Model model) {
        model.addAttribute("mainsliderAds", indexB2bService.getIndexAdImages(IndexAdPost.mainslider, null, 5, IndexAdPlatform.pc));
        model.addAttribute("subimagesAds", indexB2bService.getIndexAdImages(IndexAdPost.subimages, null, 4, IndexAdPlatform.pc));
        return "www/index/index";
    }

    @RequestMapping("/index/getIndexAdImages")
    @ResponseBody
    public ResultData getIndexAdImages(){
        List<IndexAdImages> indexAdImagesList = indexB2bService.getIndexAdImages(IndexAdPost.top, null, 1, IndexAdPlatform.pc);
        return ResultData.createSuccess(indexAdImagesList);
    }

    @RequestMapping("/index/getFooterAdImages")
    @ResponseBody
    public ResultData getFooterAdImages(){
        List<IndexAdImages> indexFooterAdImagesList = indexB2bService.getIndexAdImages(IndexAdPost.bottomad, null, 1, IndexAdPlatform.pc);
        return ResultData.createSuccess(indexFooterAdImagesList);
    }

    @RequestMapping("/index/ads")
    @ResponseBody
    public ResultData ads() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("mainsliderAds", indexB2bService.getIndexAdImages(IndexAdPost.mainslider, null, 5, IndexAdPlatform.pc));
        resultMap.put("subimagesAds", indexB2bService.getIndexAdImages(IndexAdPost.subimages, null, 4, IndexAdPlatform.pc));
        resultMap.put("newsadAds", indexB2bService.getIndexAdImages(IndexAdPost.newsad, null, 3, IndexAdPlatform.pc));
        return ResultData.createSuccess("resultMap", resultMap);
    }


    @RequestMapping("/index/indexOnlineInfoList")
    @ResponseBody
    public ResultData indexOnlineInfoList(String queryParamStr) {
        List<BaseArticleInfo> baseArticleInfos = Lists.newArrayList();
        baseArticleInfos = indexB2bService.indexOnlineInfoList();
        return ResultData.createSuccess("baseArticleInfos", baseArticleInfos);
    }

    @RequestMapping("/index/indexModuleInfo")
    @ResponseBody
    public ResultData indexModuleInfo(String type, Integer limit) {
        String storeLevelId = AccountUtils.getCurrStoreLevelId();
        return indexB2bService.indexModuleInfo(type, storeLevelId, limit);
    }

}

