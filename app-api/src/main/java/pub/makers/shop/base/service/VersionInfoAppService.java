package pub.makers.shop.base.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import pub.makers.shop.base.entity.VersionInfo;

import java.util.Map;

/**
 * Created by dy on 2017/5/5.
 */
@Service
public class VersionInfoAppService {

    @Reference(version = "1.0.0")
    private VersionInfoBizService versionInfoBizService;

    /**
     * 检查客户端版本信息
     * @param platform
     * @param currVersion
     * @return
     */
    public Map<String, Object> checkNewVersion(String platform, String currVersion) {
        Map<String, Object> resultData = Maps.newHashMap();
        VersionInfo version = versionInfoBizService.getLastVersion(platform);
        int sv = Integer.valueOf(version.getSupportVersion().replaceAll("\\.", ""));
        int cv = Integer.valueOf(currVersion.replaceAll("\\.", ""));
        if (sv <= cv) {
            resultData.put("updateFlag", "F");
            resultData.put("versionInfo", version);
            resultData.put("msg", "已经是最新版本");
        } else {
            resultData.put("updateFlag", "T");
            resultData.put("versionInfo", version);
            resultData.put("msg", "请更新版本");
        }
        return  resultData;
    }
}
