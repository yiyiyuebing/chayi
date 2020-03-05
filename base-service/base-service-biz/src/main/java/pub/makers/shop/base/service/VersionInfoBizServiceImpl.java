package pub.makers.shop.base.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.entity.VersionInfo;

import java.util.Map;

/**
 * Created by dy on 2017/5/5.
 */
@Service(version = "1.0.0")
public class VersionInfoBizServiceImpl implements VersionInfoBizService {

    @Autowired
    private VersionInfoService versionInfoService;

    @Override
    public VersionInfo getLastVersion(String platform) {
        return versionInfoService.get(Conds.get().eq("platform", platform));
    }
}
