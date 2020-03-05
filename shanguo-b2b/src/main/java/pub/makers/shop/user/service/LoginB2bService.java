package pub.makers.shop.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;

import pub.makers.jedis.JedisCallback;
import pub.makers.shop.base.entity.CommonText;
import pub.makers.shop.base.service.CommonTextBizService;
import pub.makers.shop.base.service.SmsService;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.entity.VtwoStoreRole;
import pub.makers.shop.store.pojo.SubbranchInfo;
import pub.makers.shop.store.service.SubbranchBizService;
import pub.makers.shop.store.service.SubbranchLoginBizService;
import pub.makers.shop.store.vo.SubbranchVo;
import pub.makers.shop.user.utils.VerifyCodeUtils;
import redis.clients.jedis.Jedis;

/**
 * Created by dy on 2017/6/6.
 */
@Service
public class LoginB2bService {

    @Reference(version = "1.0.0")
    private SubbranchBizService subbranchBizService;
    @Reference(version = "1.0.0")
    private SubbranchLoginBizService loginBizService;
    @Reference(version = "1.0.0")
    private CommonTextBizService commonTextBizService;



    /**
     * 登录操作
     * @param request
     * @param phone
     * @param password
     * @return
     */
    public SubbranchVo doLogin(HttpServletRequest request, String phone, String password) {
        return loginBizService.login(phone, password);
    }

    public SubbranchVo register(SubbranchVo svo) {
        return loginBizService.register(svo);
    }

    public SubbranchInfo doRegister(SubbranchInfo subbranchInfo) {
        return loginBizService.doRegister(subbranchInfo);
    }


    public Subbranch validateMobile(String phone) {
        return loginBizService.ShopInfoByMobile(phone);
    }

    public CommonText getCommonText() {
        return commonTextBizService.getCommonTextByType("3");
    }
    public CommonText getAboutUs() {
        return commonTextBizService.getCommonTextByType("1");
    }

    public Subbranch getByMobile(String mobile){
        return subbranchBizService.getByMobile(mobile);
    }

    public VtwoStoreRole getStoreRoleInfo(String mobile){
        return subbranchBizService.getStoreRoleInfo(mobile);
    }
}
