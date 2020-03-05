package pub.makers.shop.marketing.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.marketing.entity.VtwoStudyVisitor;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.service.SubbranchAccountBizService;
import pub.makers.shop.user.entity.WeixinUserInfo;
import pub.makers.shop.user.service.WeixinUserInfoBizService;

import java.util.Date;
import java.util.Map;

/**
 * Created by dy on 2017/5/5.
 */
@Service(version = "1.0.0")
public class VtwoStudyVisitorAdminServiceImpl implements VtwoStudyVisitorMgrBizService {

    private final String getWxUserInfoStmt = "select * from weixin_user_info where ID = ?";

    @Autowired
    private VtwoStudyVisitorService vtwoStudyVisitorService;

    @Reference(version = "1.0.0")
    private SubbranchAccountBizService subbranchAccountBizService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Reference(version = "1.0.0")
    private WeixinUserInfoBizService weixinUserInfoBizService;

    @Override
    public long getStudyVisitorCountByStudyId(long studyId, String userType) {
        if (StringUtils.isNotBlank(userType)) {
            return vtwoStudyVisitorService.count(Conds.get().eq("study_id", studyId).eq("status", 0).eq("user_type", userType));
        } else {
            return vtwoStudyVisitorService.count(Conds.get().eq("study_id", studyId).eq("status", 0));
        }
    }

    @Override
    public VtwoStudyVisitor getStudyVisitorByStudyIdAndUserId(long studyId, long userId) {
        return vtwoStudyVisitorService.get(Conds.get().eq("study_id", studyId).eq("status", 0).eq("user_id", userId));
    }

    @Override
    public void saveOrUpdateStudyVisitor(long studyId, Long userId, String userType) {

        VtwoStudyVisitor vtwoStudyVisitor = getStudyVisitorByStudyIdAndUserId(studyId, userId);
        if (vtwoStudyVisitor == null) {
            vtwoStudyVisitor = new VtwoStudyVisitor();
        }
        //超找店铺信息
        Subbranch subbranch = subbranchAccountBizService.getMainSubbranch(userId.toString());
        if (subbranch == null) {
            //查找微信用户信息
            WeixinUserInfo weixinUserInfo = weixinUserInfoBizService.getWxUserById(userId);
            if (weixinUserInfo == null) {
                vtwoStudyVisitor.setUserName("游客");
                vtwoStudyVisitor.setHeadImg("http://youchalian.com/shanguoyinyi/www/img/share-icon.png");
                vtwoStudyVisitor.setUserType(1);
            } else {
                vtwoStudyVisitor.setUserName(weixinUserInfo.getNickname());
                vtwoStudyVisitor.setHeadImg(weixinUserInfo.getHeadImgUrl());
                vtwoStudyVisitor.setUserType(1);
            }
        } else {
            vtwoStudyVisitor.setUserName(subbranch.getName());
            vtwoStudyVisitor.setUserType(0);
            vtwoStudyVisitor.setHeadImg(subbranch.getHeadImgUrl());
        }

        if (vtwoStudyVisitor.getId() != null) {
            vtwoStudyVisitor.setUpdateTime(new Date());
            vtwoStudyVisitorService.update(vtwoStudyVisitor);
        } else {
            vtwoStudyVisitor.setId(IdGenerator.getDefault().nextId());
            vtwoStudyVisitor.setUserId(userId);
            vtwoStudyVisitor.setStudyId(studyId);
            vtwoStudyVisitor.setUpdateTime(new Date());
            vtwoStudyVisitor.setCreateTime(new Date());
            vtwoStudyVisitor.setStatus(0);
            vtwoStudyVisitorService.insert(vtwoStudyVisitor);
        }

    }

    @Override
    public Map<String, Object> getWxUserInfo(long userId) {
        return jdbcTemplate.queryForMap(getWxUserInfoStmt, userId);
    }

}
