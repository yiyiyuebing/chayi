package pub.makers.shop.user.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.service.SubbranchBizService;
import pub.makers.shop.store.service.SubbranchLoginBizService;
import pub.makers.shop.store.vo.SubbranchVo;
import pub.makers.shop.user.pojo.RestPwdVo;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dy on 2017/6/6.
 */
@Service
public class LoginAppService {

    @Reference(version = "1.0.0")
    private SubbranchBizService subbranchBizService;
    @Reference(version = "1.0.0")
    private SubbranchLoginBizService loginBizService;


    /**
     * 登录操作
     * @param request
     * @param phone
     * @param password
     * @return
     */
    public SubbranchVo doLogin(HttpServletRequest request, String phone, String password) {
    	
    	// 对登陆密码进行MD5加密
    	
    	return loginBizService.login(phone, password);
    }

    public SubbranchVo register(SubbranchVo svo) {
        return loginBizService.register(svo);
    }


    public Subbranch validateMobile(String phone) {
        return loginBizService.ShopInfoByMobile(phone);
    }

    public void resetPwd(RestPwdVo restPwdVo) {
        ValidateUtils.isTrue(restPwdVo.getPassword().equals(restPwdVo.getRepassword()), "两次输入密码不一样");
        subbranchBizService.updatePassword(null, restPwdVo.getPassword(), restPwdVo.getMobile());
    }
}
