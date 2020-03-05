package pub.makers.shop.express.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.express.service.ExpressService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dy on 2017/7/18.
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
    @RequestMapping("expressInfo")
    @ResponseBody
    private ResultData express(String num, HttpServletRequest request,
                                        HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=utf-8");
        String res = null;
        if (StringUtils.isNotBlank(num)) {
            res = expressService.getExpressInfo(num);
        } else {
           return ResultData.createFail();
        }
        if (StringUtils.isNotBlank(res)) {
            return ResultData.createSuccess(JSONObject.parse(res));
        } else {
            return ResultData.createFail();
        }
    }
}
