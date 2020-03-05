package pub.makers.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lantu.base.util.WebUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dy on 2017/8/22.
 */
@Controller
@RequestMapping("/")
public class CommonController {
    @RequestMapping(value = "/shanguoyinyi/**")
    public String redirectSangou(HttpServletRequest request) {
    	
        String uri = request.getRequestURI();
        String paramStr = WebUtil.getParamStr(request);
        
        return String.format("redirect:http://youchalian.com/%s?%s", uri, paramStr);
        
    }
}
