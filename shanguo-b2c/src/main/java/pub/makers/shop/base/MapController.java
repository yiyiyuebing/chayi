package pub.makers.shop.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kok on 2017/9/29.
 */
@Controller
@RequestMapping("weixin/map")
public class MapController {

    @RequestMapping(method= RequestMethod.GET)
    public String index() throws Exception{
        return "redirect:" + "/weixinfront/static/home_map.html?share=0&id=0&storeId=0&flag=T";
    }
}
