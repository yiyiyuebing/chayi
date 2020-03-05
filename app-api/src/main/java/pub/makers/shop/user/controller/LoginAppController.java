package pub.makers.shop.user.controller;

import com.dev.base.json.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.service.SmsAppService;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.store.vo.SubbranchVo;
import pub.makers.shop.user.pojo.RestPwdVo;
import pub.makers.shop.user.service.LoginAppService;
import pub.makers.shop.user.service.VerifyImageAppService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("login")
@Controller
public class LoginAppController {

	@Autowired
	private SmsAppService smsService;
	@Autowired
	private LoginAppService loginService;
	@Autowired
	private VerifyImageAppService vImgService;
	
	@RequestMapping("doRegister")
    @ResponseBody
    public ResultData doRegister(String subJson, String yzm) {

        ValidateUtils.notNull(yzm, "验证码不能为空！");
        ValidateUtils.notNull(subJson, "注册信息不完善！");
        SubbranchVo subbranchVo = JsonUtils.toObject(subJson, SubbranchVo.class);
        
        ValidateUtils.isTrue(smsService.icCodeValid("user_login", subbranchVo.getMobile(), yzm), "验证码不正确");
        
        SubbranchVo svo = loginService.register(subbranchVo);
        if (StringUtils.isBlank(svo.getId())) {
            return ResultData.createFail("注册失败！");
        }
        
        return ResultData.createSuccess("id", svo.getId());
    }
    
    @RequestMapping("getVerifyImg")
    public void getVerifyImg(HttpServletRequest request, HttpServletResponse response, String requestId) throws IOException{
    	
    	ValidateUtils.notNull(requestId, "requestId不能为空");
    	vImgService.showVerifyImage(request, response, requestId);
    }
	
    
    /**
	 * 根据手机号获取服务端验证码
	 * @param request
	 * @param phoneCode 手机号
	 * @param iptVerifyCode 验证码
	 * @param requestId 请求ID
	 * @return
	 */
	@RequestMapping(value = "sendSms" )
	@ResponseBody
	public ResultData verifyPhone(HttpServletRequest request, String phoneCode, String smsType, String iptVerifyCode, String requestId) {
		
		ValidateUtils.notNull(iptVerifyCode, "验证码不能为空");
		ValidateUtils.notNull(requestId, "请求ID不能为空");
		
		ValidateUtils.isTrue(vImgService.isCodeValid(requestId, iptVerifyCode), "校验码不正确，请重新输入并校验");
		
		smsService.sendCheckCode(smsType, phoneCode);
		vImgService.delYzm(requestId);
		
		return ResultData.createSuccess();
	}
	
	@RequestMapping(value = "checkVerifyCode" )
	@ResponseBody
	public ResultData checkVerifyCode(String smsType, String phoneCode, String verifyCode){
		
		ValidateUtils.isTrue(smsService.icCodeValid(smsType, phoneCode, verifyCode), "验证码不正确");
		
		return ResultData.createSuccess();
	}
	
	@RequestMapping(value = "resetPwd" )
	@ResponseBody
	public ResultData resetPwd(@RequestBody RestPwdVo restPwdVo){

		ValidateUtils.notNull(restPwdVo.getVerifyCode(), "参数错误");
		ValidateUtils.notNull(restPwdVo.getMobile(), "参数错误");
		ValidateUtils.notNull(restPwdVo.getPassword(), "参数错误");
		ValidateUtils.notNull(restPwdVo.getRepassword(), "参数错误");

		// 检查短信验证码是否正确
		smsService.icCodeValid("resetPwd", restPwdVo.getMobile(), restPwdVo.getVerifyCode());

		loginService.resetPwd(restPwdVo);

		return ResultData.createSuccess();
	}
}
