package pub.makers.shop.marketing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.marketing.param.StudyParam;
import pub.makers.shop.marketing.service.MarktingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dy on 2017/9/30.
 */
@Controller
@RequestMapping("markting")
public class MarktingController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MarktingService marktingService;

    @RequestMapping("/findStudyAllList")
    @ResponseBody
    public Map<String, Object> findStudyAllList(StudyParam vo, HttpServletRequest request){
        logger.debug("findStudyAllList ");
        // 如果查询条件不为空则添加查询条件
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Paging paging = Paging.build3(request);
            result.put("code", 1);
            result.put("msg", "成功");
            result.put("data", marktingService.findStudyAllList(vo, paging));
        } catch (Exception e) {
            logger.error("查询文章列表分页异常<findVisitorList>:", e);
            result.put("code", 0);
            result.put("msg", "系统出错！");
        }
        return result;
    }
}
