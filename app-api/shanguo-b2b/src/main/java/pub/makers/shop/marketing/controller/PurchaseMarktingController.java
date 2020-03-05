package pub.makers.shop.marketing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.index.vo.BaseArticleInfo;
import pub.makers.shop.marketing.entity.Toutiao;
import pub.makers.shop.marketing.service.PurchaseMarktingB2bService;
import pub.makers.shop.marketing.vo.OnlineStudyVo;
import pub.makers.shop.purchaseGoods.pojo.PurchaseGoodsQuery;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsVo;

import java.io.IOException;
import java.util.List;

/**
 * Created by dy on 2017/6/5.
 */
@Controller
@RequestMapping("/news")
public class PurchaseMarktingController {


    @Autowired
    private PurchaseMarktingB2bService purchaseMarktingB2bService;

    @RequestMapping("")
    public String articleListAll(Model model) {
        String type = "gonggao";
        model.addAttribute("type", type);
        return "www/markting/article_list";
    }


    @RequestMapping("/list/{type}")
    public String articleList(@PathVariable String type, Model model) {
        if (StringUtils.isBlank(type)) {
            type = "gonggao";
        }
        model.addAttribute("type", type);
        return "www/markting/article_list";
    }

    @RequestMapping("/marktingArticleList")
    @ResponseBody
    public ResultData marktingArticleList(Integer pageNum, Integer pageSize, String type) {
        BaseArticleInfo baseArticleInfo = new BaseArticleInfo();
        baseArticleInfo = purchaseMarktingB2bService.marktingArticleList(pageNum, pageSize, type);
        return ResultData.createSuccess("baseArticleInfo", baseArticleInfo);
    }


    @RequestMapping("/detail/{type}/{articleId}.html")
    public String detailWithType(@PathVariable String type, @PathVariable String articleId, Model model) {
        OnlineStudyVo onlineStudyVo = purchaseMarktingB2bService.getOnlineStudyById(articleId);
        model.addAttribute("onlineStudy", onlineStudyVo);
        model.addAttribute("type", type);
        return "www/markting/article_detail";
    }

    @RequestMapping("/gonggaoDetail/{type}/{toutiaoId}.html")
    public String gonggaoDetailWithType(@PathVariable String toutiaoId, @PathVariable String type, Model model) {
        Toutiao toutiao = purchaseMarktingB2bService.getToutiaoById(toutiaoId);
        toutiao.setCreateTime(toutiao.getDateCreated());
        model.addAttribute("onlineStudy", toutiao);
        model.addAttribute("type", type);
        model.addAttribute("articleType", "gonggao");
        return "www/markting/article_detail";
    }



}
