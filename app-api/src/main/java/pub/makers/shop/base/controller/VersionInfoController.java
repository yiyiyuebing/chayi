package pub.makers.shop.base.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.shop.base.entity.VersionInfo;
import pub.makers.shop.base.service.VersionInfoAppService;
import pub.makers.shop.base.service.VersionInfoBizService;
import pub.makers.shop.base.vo.ResultData;

import java.util.Map;

/**
 * Created by dy on 2017/5/5.
 */
@Controller
@RequestMapping("mobile/version")
public class VersionInfoController {

    @Autowired
    private VersionInfoAppService versionInfoAppService;

    @RequestMapping(value = "checkNewVersion")
    @ResponseBody
    public Map<String, Object> checkNewVersion(@RequestParam(required = true) String platform, @RequestParam(required = true) String currVersion){
        Map<String, Object> resultData = Maps.newHashMap();
        try{

            resultData = versionInfoAppService.checkNewVersion(platform, currVersion);

        }
        catch (Exception e){
            resultData.put("msg", "获取版本信息失败");
        }
        return resultData;
    }


}
