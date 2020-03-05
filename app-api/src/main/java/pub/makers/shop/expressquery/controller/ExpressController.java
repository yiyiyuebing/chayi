package pub.makers.shop.expressquery.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.shop.expressquery.service.ExpressService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dy on 2017/5/4.
 */
@RequestMapping("express")
@Controller
public class ExpressController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ExpressService expressService;
    /**
     * 查询物流信息
     * @param num
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("order/expressInfo")
    @ResponseBody
    private Map<String, Object> express(String num, HttpServletRequest request,
                                        HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=utf-8");
        logger.debug("express");
        Map<String, Object> result = new HashMap<String, Object>();
        String res = null;
        if(num!=null && !"".equals(num)){
            res = expressService.getExpressInfo(num);
        }
        if (StringUtils.isNotBlank(res)) {
            result.put("code",1);
            result.put("msg","成功");
            result.put("data", JSONObject.parse(res));
        } else {
            result.put("code",0);
            result.put("msg","失败");
        }
        return result;
    }

}
