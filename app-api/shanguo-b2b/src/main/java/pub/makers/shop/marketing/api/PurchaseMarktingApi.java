package pub.makers.shop.marketing.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.index.vo.BaseArticleInfo;
import pub.makers.shop.marketing.entity.Toutiao;
import pub.makers.shop.marketing.service.PurchaseMarktingB2bService;
import pub.makers.shop.marketing.vo.OnlineStudyVo;
import pub.makers.shop.user.utils.AccountUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Think on 2017/7/21.
 */
@Controller
@RequestMapping("weixin/markting")
public class PurchaseMarktingApi {

    @Autowired
    private PurchaseMarktingB2bService purchaseMarktingB2bService;

    //公告信息
    @RequestMapping("/gonggaoDetail")
    @ResponseBody
    public ResultData gonggaoDetail(String toutiaoId) {
        Toutiao toutiao = purchaseMarktingB2bService.getToutiaoById(toutiaoId);
        return ResultData.createSuccess(toutiao);
    }
    //文章列表
    @RequestMapping("/marktingArticleList")
    @ResponseBody
    public ResultData marktingArticleList(Integer pageNum, Integer pageSize) {
        BaseArticleInfo baseArticleInfo = new BaseArticleInfo();
        try {
            baseArticleInfo = purchaseMarktingB2bService.marktingArticleList(pageNum, pageSize, "gonggao");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultData.createSuccess("baseArticleInfo", baseArticleInfo);
    }

    //详情
    @RequestMapping("/detail")
    @ResponseBody
    public ResultData detail(String articleId) {
        OnlineStudyVo onlineStudyVo = purchaseMarktingB2bService.getOnlineStudyById(articleId);

        return ResultData.createSuccess(onlineStudyVo);
    }


    /**
     * 查询用户的头条信息
     * @param classifys 多个用,隔开
     * @param
     * @return
     */
    @RequestMapping("/listByUser")
    @ResponseBody
    public ResultData listByUser(HttpServletResponse response, String target, String classifys, int start, int limit ){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=utf-8");
        Paging pi = new Paging();
        pi.setPs(start);
        pi.setPn(limit);
        String userId = AccountUtils.getCurrShopId();
        List<Toutiao> toutiaoList = purchaseMarktingB2bService.listByUser(userId,target,classifys, pi);
        return ResultData.createSuccess("toutiaoList",toutiaoList);
    }

    /**
     * 用户删除一条头条信息
     * @param
     * @param toutiaoId
     */
    @RequestMapping("/delUserToutiao")
    @ResponseBody
    public ResultData delUserToutiao(HttpServletResponse response,String classify, String toutiaoId){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=utf-8");
        String userId = AccountUtils.getCurrShopId();
        purchaseMarktingB2bService.delUserToutiao(userId, classify, toutiaoId);
        return ResultData.createSuccess("msg","操作成功");
    }

    /**
     * 删除用户的所有头条信息
     * @param
     */
    @RequestMapping("/delAllUserToutiao")
    @ResponseBody
    public ResultData delAllUserToutiao(HttpServletResponse response, String classifys){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=utf-8");
        String userId = AccountUtils.getCurrShopId();
        purchaseMarktingB2bService.delAllUserToutiao(userId, classifys);
        return ResultData.createSuccess("msg","操作成功");
    }

    /**
     * 根据主键查询一条头条详情
     * @param toutiaoId
     */
    @RequestMapping("/getById")
    @ResponseBody
    public ResultData getById(HttpServletResponse response,String toutiaoId){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=utf-8");
        purchaseMarktingB2bService.getById(toutiaoId);
        Toutiao toutiao = purchaseMarktingB2bService.getById(toutiaoId);
        return ResultData.createSuccess("toutiao",toutiao);
    }

    /**
     * 查询未读的消息数
     * @param
     * @return
     */
    @RequestMapping("/countUnreaded")
    @ResponseBody
    public ResultData countUnreaded(HttpServletResponse response, String target, String classifys){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=utf-8");
        String userId = AccountUtils.getCurrShopId();
        int count = purchaseMarktingB2bService.countUnreaded(userId, target, classifys);
        return ResultData.createSuccess("countUnreaded",count);
    }

    /**
     * 标记一条为已读
     * @param classify
     */
    @RequestMapping("/markAsReaded")
    @ResponseBody
    public ResultData markAsReaded(HttpServletResponse response, String toutiaoId, String classify){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=utf-8");
        String userId = AccountUtils.getCurrShopId();
        purchaseMarktingB2bService.markAsReaded(userId, toutiaoId, classify);
        return ResultData.createSuccess("msg","操作成功");
    }
}