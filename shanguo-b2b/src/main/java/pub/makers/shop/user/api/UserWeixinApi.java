package pub.makers.shop.user.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.dev.base.json.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lantu.base.common.entity.BoolType;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateException;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.common.service.SmsB2bService;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.entity.VtwoStoreRole;
import pub.makers.shop.store.pojo.SubbranchInfo;
import pub.makers.shop.store.service.SubbranchBizService;
import pub.makers.shop.store.vo.GoodReceiptAddrVo;
import pub.makers.shop.store.vo.SubbranchVo;
import pub.makers.shop.user.pojo.RestPwdVo;
import pub.makers.shop.user.service.LoginB2bService;
import pub.makers.shop.user.service.UserB2bService;
import pub.makers.shop.user.service.VerifyImageAppService;
import pub.makers.shop.user.utils.AccountUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/weixin/user")
public class UserWeixinApi {

    @Autowired
    private LoginB2bService loginB2bService;
    @Autowired
    private VerifyImageAppService vimgService;
    @Autowired
    private SmsB2bService smsService;
    @Autowired
    private UserB2bService userB2bService;
    @Autowired
    private VerifyImageAppService vImgService;


    @RequestMapping("userinfo.js")
    @ResponseBody
    public String userInfoScript(HttpServletResponse response) {

        String shopId = AccountUtils.getCurrShopId(false);
        String isLogin = StringUtils.isBlank(shopId) ? "false" : "true";
        response.setContentType("application/x-javascript");
        return String.format("var _b2b_user = {shopId: '%s', isLogin: %s}", shopId, isLogin);
    }


    /**
     * 登录操作
     *
     * @return
     */
    @RequestMapping("doLogin")
    @ResponseBody
    public ResultData doLogin(HttpServletRequest request, HttpServletResponse response, String phone, String password) {
        ValidateUtils.notNull(phone, "手机号不能为空");
        ValidateUtils.notNull(password, "密码不能为空");
        
       /* ValidateUtils.isTrue(smsService.icCodeValid("user_login", phone, yzm), "验证码不正确");*/

       // password = DigestUtils.md5Hex(password);
            Subbranch subbranch = loginB2bService.getByMobile(phone);
            ValidateUtils.notNull(subbranch, "52001", "店铺不存在");
//                ValidateUtils.isTrue(!BoolType.T.name().equals(subbranch.getDelFlag()),"2" , "账号不存在，请重新注册");
            ValidateUtils.isTrue(!BoolType.T.name().equals(subbranch.getIsSubAccount()), "52005", "您暂无权限查看");
            ValidateUtils.isTrue(BoolType.T.name().equals(subbranch.getIsValid()), "52003", "账号已禁用");
            VtwoStoreRole storeRole = loginB2bService.getStoreRoleInfo(phone);
            ValidateUtils.notNull(storeRole, "52001", "店铺不存在");
             ValidateUtils.isTrue(storeRole.getStatus() != null && storeRole.getStatus() == 2, "52002", "未审核通过");
            SubbranchVo svo = loginB2bService.doLogin(request, phone, password);

            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("store_level_id", svo.getLevelId());
            httpSession.setAttribute("login_name", svo.getName());

            Cookie cookie = new Cookie("b2b_token", svo.getLoginToken());
            cookie.setMaxAge(24 * 60 * 60 * 30); //有效期限一个月
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResultData.createSuccess(svo);
    }

    @RequestMapping("doRegister")
    @ResponseBody
    public ResultData doRegister(String subJson, String yzm) {

        ValidateUtils.notNull(yzm, "验证码不能为空！");
        ValidateUtils.notNull(subJson, "注册信息不完善！");
        SubbranchVo subbranchVo = JsonUtils.toObject(subJson, SubbranchVo.class);

        ValidateUtils.isTrue(smsService.icCodeValid("user_login", subbranchVo.getMobile(), yzm), "验证码不正确");

        SubbranchVo svo = loginB2bService.register(subbranchVo);
        if (StringUtils.isBlank(svo.getId())) {
            return ResultData.createFail("注册失败！");
        }

        return ResultData.createSuccess("id", svo.getId());
    }

    @RequestMapping("getVerifyImg")
    public void getVerifyImg(HttpServletRequest request, HttpServletResponse response, String requestId) throws IOException {

        vimgService.showVerifyImage(request, response, requestId);
    }


    /**
     * 根据手机号获取服务端验证码
     *
     * @param request
     * @param phoneCode     手机号
     * @param iptVerifyCode 验证码
     * @param requestId     请求ID
     * @return
     */
    @RequestMapping(value = "/verifyPhone.do")
    @ResponseBody
    public ResultData verifyPhone(HttpServletRequest request, String phoneCode, String iptVerifyCode, String requestId) {

        ValidateUtils.notNull(iptVerifyCode, "iptVerifyCode不能为空");
        ValidateUtils.notNull(requestId, "requestId不能为空");

        ValidateUtils.isTrue(vimgService.isCodeValid(requestId, iptVerifyCode), "校验码不正确");

        smsService.sendCheckCode("user_login", phoneCode);

        return ResultData.createSuccess();
    }

    //重置密码接收验证码
    @RequestMapping(value = "/yzmOfReset")
    @ResponseBody
    public ResultData sendyzm(HttpServletRequest request, String phoneCode, String iptVerifyCode, String requestId) {

        ValidateUtils.notNull(iptVerifyCode, "iptVerifyCode不能为空");
        ValidateUtils.notNull(requestId, "requestId不能为空");

        ValidateUtils.isTrue(vimgService.isCodeValid(requestId, iptVerifyCode), "校验码不正确");

        smsService.sendCheckCode("resetPwd", phoneCode);

        return ResultData.createSuccess();
    }

    //查找收货地址
    @RequestMapping("/address")
    @ResponseBody
    public ResultData address() {

        String userId = AccountUtils.getCurrShopId();
        List<GoodReceiptAddrVo> addressList = userB2bService.listGoodReceiptAddr(userId);
        return ResultData.createSuccess("addressList", addressList);
    }

    //返回默认地址
    @RequestMapping("/defaultAddress")
    @ResponseBody
    public ResultData defaultAddress() {

        String userId = AccountUtils.getCurrShopId();
        List<GoodReceiptAddrVo> addressList = userB2bService.listGoodReceiptAddr(userId);
        GoodReceiptAddrVo defaultAddress = addressList.get(0);
        return ResultData.createSuccess("defaultAddress", defaultAddress);
    }

    //添加收货地址
    @RequestMapping("/addAddress")
    @ResponseBody
    public ResultData addAddress(String modelJsonStr) {
        ValidateUtils.notNull(modelJsonStr, "");
//        ObjectMapper objectMapper = new ObjectMapper();
        GoodReceiptAddrVo addr = JsonUtils.toObject(modelJsonStr, GoodReceiptAddrVo.class);
        addr.setUserId(AccountUtils.getCurrShopId());
        userB2bService.createGoodReceiptAddr(addr);
        return ResultData.createSuccess();
    }

    //更新收货地址
    @RequestMapping("/updateAddress")
    @ResponseBody
    public ResultData updateAddress(String modelJsonStr) {
        if (org.apache.commons.lang.StringUtils.isNotEmpty(modelJsonStr)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                GoodReceiptAddrVo addr = objectMapper.readValue(modelJsonStr, GoodReceiptAddrVo.class);
                addr.setUserId(AccountUtils.getCurrShopId());
                userB2bService.updateGoodReceiptAddr(addr);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultData.createFail();
            }
            return ResultData.createSuccess();
        } else {
            return ResultData.createFail();
        }

    }

    //删除地址
    @RequestMapping("/delAddress")
    @ResponseBody
    public ResultData delAddress(String addrId) {

        String userId = AccountUtils.getCurrShopId();
        ;
        if (org.apache.commons.lang.StringUtils.isNotEmpty(addrId)) {
            userB2bService.delGoodReceiptAddr(addrId, userId);
        } else {
            return ResultData.createFail();
        }
        return ResultData.createSuccess();

    }

    //设置默认地址
    @RequestMapping("/updateDefaultAddr")
    @ResponseBody
    public ResultData updateDefaultAddr(String addrId) {

        String userId = AccountUtils.getCurrShopId();
        if (org.apache.commons.lang.StringUtils.isNotEmpty(addrId)) {
            userB2bService.updateDefaultAddr(addrId, userId);
        } else {
            return ResultData.createFail();
        }
        return ResultData.createSuccess();

    }

    //修改密码
    @RequestMapping("/updatePassword")
    @ResponseBody
    public ResultData upPassword(String password, String mobile) {
        String id = null;

        if (org.apache.commons.lang.StringUtils.isBlank(mobile)) {
            id = AccountUtils.getCurrShopId();
        }
        if (org.apache.commons.lang.StringUtils.isNotEmpty(password)) {
            try {
          //      password = DigestUtils.md5Hex(password);
                userB2bService.updatePassword(id, password, mobile);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultData.createFail();
            }
            return ResultData.createSuccess();
        } else {
            return ResultData.createFail();
        }

    }

    //发送验证码
    @RequestMapping("/send")
    @ResponseBody
    public ResultData sendCheckCode(String phoneNmuber) {

        try {
            userB2bService.checkCode(phoneNmuber);
        } catch (Exception e) {
            e.printStackTrace();
            ResultData.createFail();
        }
        return ResultData.createSuccess();
    }

    //检查验证码是否有效
    @RequestMapping("/checkCode")
    @ResponseBody
    public ResultData checkCode(String phoneNmuber, String yzm) {

        if (org.apache.commons.lang.StringUtils.isNotEmpty(phoneNmuber) && org.apache.commons.lang.StringUtils.isNotEmpty(yzm)) {

            boolean rerult = userB2bService.checkCode(phoneNmuber, yzm);

            return ResultData.createSuccess("rerult", rerult);

        } else {
            return ResultData.createFail();
        }

    }

    //更新店铺信息
    @RequestMapping("/updateShop")
    @ResponseBody
    public ResultData updateShop(@RequestParam(value = "modelJsonStr", required = true) String modelJsonStr) {

        if (org.apache.commons.lang.StringUtils.isNotEmpty(modelJsonStr)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                SubbranchInfo info = objectMapper.readValue(modelJsonStr, SubbranchInfo.class);
                userB2bService.updateShop(info);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultData.createFail();
            }
            return ResultData.createSuccess();
        } else {
            return ResultData.createFail();
        }
    }

    //查询用户信息
    @RequestMapping("/userInfo")
    @ResponseBody
    public ResultData userInfo() {

        String shopId = AccountUtils.getCurrShopId();
        SubbranchVo sv = userB2bService.shopInfo(shopId);

        return ResultData.createSuccess(sv);
    }


    /**
     * 根据手机号获取服务端验证码
     *
     * @param request
     * @param phoneCode     手机号
     * @param iptVerifyCode 验证码
     * @param requestId     请求ID
     * @return
     */
    @RequestMapping(value = "/sendSms")
    @ResponseBody
    public ResultData verifyPhone(HttpServletRequest request, String phoneCode, String smsType, String iptVerifyCode, String requestId) {

        ValidateUtils.notNull(iptVerifyCode, "验证码不能为空");
        ValidateUtils.notNull(requestId, "请求ID不能为空");

        ValidateUtils.isTrue(vImgService.isCodeValid(requestId, iptVerifyCode), "校验码不正确");

        smsService.sendCheckCode(smsType, phoneCode);

        return ResultData.createSuccess();
    }

    /**
     * 验证手机验证码
     */
    @RequestMapping(value = "checkVerifyCode")
    @ResponseBody
    public ResultData checkVerifyCode(String smsType, String phoneCode, String verifyCode) {

        ValidateUtils.isTrue(smsService.icCodeValid(smsType, phoneCode, verifyCode), "验证码不正确");

        return ResultData.createSuccess();
    }

    //重置密码
    @RequestMapping(value = "/resetPwd")
    @ResponseBody
    public ResultData resetPwd(@RequestParam(value = "modelJsonStr", required = true) String modelJsonStr) {

        if (org.apache.commons.lang.StringUtils.isNotEmpty(modelJsonStr)) {
//            try {
                RestPwdVo restPwdVo = JsonUtils.toObject(modelJsonStr, RestPwdVo.class);
                /*ValidateUtils.notNull(restPwdVo.getVerifyCode(), "参数错误");
                ValidateUtils.notNull(restPwdVo.getMobile(), "参数错误");
                ValidateUtils.notNull(restPwdVo.getPassword(), "参数错误");
                ValidateUtils.notNull(restPwdVo.getRepassword(), "参数错误");*/
                // 检查短信验证码是否正确
                smsService.icCodeValid("resetPwd", restPwdVo.getMobile(), restPwdVo.getVerifyCode());
                Subbranch subbranch = loginB2bService.getByMobile(restPwdVo.getMobile());
                ValidateUtils.notNull(subbranch, "52001", "店铺不存在");
//                ValidateUtils.isTrue(!BoolType.T.name().equals(subbranch.getDelFlag()),"2" , "账号不存在，请重新注册");
                ValidateUtils.isTrue(BoolType.T.name().equals(subbranch.getIsValid()), "52003", "账号已禁用");
                VtwoStoreRole storeRole = loginB2bService.getStoreRoleInfo(restPwdVo.getMobile());
                ValidateUtils.notNull(storeRole, "52001", "店铺不存在");
                ValidateUtils.isTrue(storeRole.getStatus() != null && storeRole.getStatus() == 2, "52002", "未审核通过");
                userB2bService.resetPwd(restPwdVo);
//            } catch (Exception e) {
//                e.printStackTrace();
//                ResultData resultData = ResultData.createFail();
//                if (e instanceof ValidateException) {
//                    ValidateException exception = (ValidateException) e;
//                    resultData.setErrorCode(exception.getErrorCode());
//                } else {
//                    resultData.setErrorCode("0");
//                }
//                return resultData;
//            }
            return ResultData.createSuccess();
        } else {
            return ResultData.createFail();
        }
    }

}
