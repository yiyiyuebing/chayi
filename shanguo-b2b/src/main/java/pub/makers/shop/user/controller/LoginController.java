package pub.makers.shop.user.controller;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lantu.base.common.entity.BoolType;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.base.json.JsonUtils;
import com.lantu.base.util.HttpUtil;

import pub.makers.base.exception.ValidateUtils;
import pub.makers.common.util.ValidateCodeUtil;
import pub.makers.shop.base.entity.CommonText;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.pojo.SubbranchInfo;
import pub.makers.shop.store.vo.SubbranchVo;
import pub.makers.shop.user.service.LoginB2bService;
import pub.makers.shop.user.service.UserB2bService;

/**
 * Created by dy on 2017/6/6.
 */
@Controller
@RequestMapping("/passport")
public class LoginController {

    @Autowired
    private LoginB2bService loginB2bService;
    @Autowired
    private UserB2bService userB2bService;

    /**
     * 登录页面
     *
     * @return
     */
    @RequestMapping("login")
    public String GoodsDetails() {
        return "www/user/login";
    }

    @RequestMapping("registerInfo/{moblie}")
    public String registerInfo(@PathVariable String moblie, Model model) {
        model.addAttribute("moblie", moblie);
        return "www/user/register_user_info";
    }

    @RequestMapping("registerInfoComplete/{shopId}")
    public String registerInfoComplete(@PathVariable String shopId, Model model) {
        model.addAttribute("shopId", shopId);
        return "www/user/register_user_info_1";
    }

    @RequestMapping("registerInfoBandcard/{shopId}")
    public String registerInfoBandCard(@PathVariable String shopId, Model model) {
        model.addAttribute("shopId", shopId);
        return "www/user/register_bind_bandcard";
    }

    @RequestMapping("registerAudit")
    public String registerInfoConf() {
        return "www/user/register_audit";
    }

    @RequestMapping("resetPasswordTel")
    public String resetPasswordTel() {
        return "www/user/reset_password_tel";
    }

    @RequestMapping("resetPassword")
    public String resetPassword(String mobile, Model model) {
        model.addAttribute("mobile", mobile);
        return "www/user/reset_password";
    }
    @RequestMapping("restPasswordSuccess")
    public String restPasswordSuccess(){
        return "www/user/reset_password_success";
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

//        password = DigestUtils.md5Hex(password);
        SubbranchVo svo = loginB2bService.doLogin(request, phone, password);

        if (BoolType.T.name().equals(svo.getIsSubAccount())) {
            return ResultData.createFail("您暂无权限查看");
        }

        if (StringUtils.isBlank(svo.getName())) {
            return ResultData.createFail("500014", "店铺信息不完善", svo.getId());
        }

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("store_level_id", svo.getLevelId());
        httpSession.setAttribute("login_name", svo.getName());

        Cookie cookie = new Cookie("b2b_token", svo.getLoginToken());
        cookie.setMaxAge(24 * 60 * 60 * 30); //有效期限一个月
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResultData.createSuccess(svo);
    }

    @RequestMapping("validateMobile")
    @ResponseBody
    public ResultData validateMobile(String phone) {
        Subbranch svo = loginB2bService.validateMobile(phone);
        if (svo != null) {
            return ResultData.createSuccess();
        } else {
            return ResultData.createFail("帐号不存在！");
        }
    }

    @RequestMapping("doRegister")
    @ResponseBody
    public ResultData doRegister(String subJson, String yzm, HttpServletRequest request, HttpServletResponse response) {

        if (StringUtils.isBlank(yzm)) {
            return ResultData.createFail("验证码不能为空！");
        }

        if (StringUtils.isBlank(subJson)) {
            return ResultData.createFail("注册信息不完善！");
        }
        SubbranchVo subbranchVo = JsonUtils.toObject(subJson, SubbranchVo.class);
        if (!userB2bService.checkCode(subbranchVo.getMobile(), yzm)) {
            return ResultData.createFail("验证码验证失败！");
        }

        //获得HttpSession对象
        HttpSession session = request.getSession();
        //设置session对象5分钟失效
        session.setMaxInactiveInterval(5 * 60);
        //将验证码保存在session对象中
        session.setAttribute(subbranchVo.getMobile(), subbranchVo);

        return ResultData.createSuccess("mobile", subbranchVo.getMobile());
    }

    @RequestMapping("doRegisterUserInfo")
    @ResponseBody
    public ResultData doRegisterUserInfo(HttpServletRequest request, String subJson) {
        if (StringUtils.isBlank(subJson)) {
            return ResultData.createFail("注册信息不完善！");
        }
        SubbranchInfo subbranchInfo = JsonUtils.toObject(subJson, SubbranchInfo.class);

        //获得HttpSession对象
        HttpSession session = request.getSession();
        //获取注册手机号信息
        if (session.getAttribute(subbranchInfo.getMobile()) == null) {
            return ResultData.createFail("注册失败，请重新注册");
        }
        SubbranchVo subbranchVoInfo = (SubbranchVo) session.getAttribute(subbranchInfo.getMobile());
        subbranchInfo.setPassword(subbranchVoInfo.getPassword());
        subbranchInfo.setRecommendUser(subbranchVoInfo.getRecommendUser());
        session.setAttribute(subbranchInfo.getMobile(), null);

        SubbranchInfo svo = loginB2bService.doRegister(subbranchInfo);
        if (StringUtils.isBlank(svo.getId())) {
            return ResultData.createFail("注册失败");
        }
        return ResultData.createSuccess("id", svo.getId());
    }


    /**
     * 检查是否登录
     *
     * @return
     */
    @RequestMapping("checkLogin")
    @ResponseBody
    public ResultData checkLogin(HttpServletRequest request, HttpServletResponse response) {
        String token = HttpUtil.getCookieValue(request, "b2b_token");
        if (StringUtils.isBlank(token)) {
            return ResultData.createFail("请登录");
        }
        return ResultData.createFail("请登录");
    }


    @RequestMapping("/register")
    public String register(Model model) {
        CommonText commonText = loginB2bService.getCommonText();
        model.addAttribute("commonText", commonText);
        return "www/user/register";
    }


    @RequestMapping("createValidateCode")
    @ResponseBody
    public void createValidateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //获得验证码集合的长度
        int charsLength = ValidateCodeUtil.codeChars.length();
        response.setHeader("ragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        int width = 90, height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics(); //获得用于输出文字的Graphics对象
        Random random = new Random();
        g.setColor(ValidateCodeUtil.getRandomColor(180, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, height));
        g.setColor(ValidateCodeUtil.getRandomColor(120, 180));
        //用户保存最后随机生成的验证码
        StringBuilder validationCode = new StringBuilder();
        //验证码的随机字体
        String[] fontNames = {"Times New Roman", "Book antiqua", "Arial"};
        //随机生成4个验证码
        for (int i = 0; i < 4; i++) {
            //随机设置当前验证码的字符的字体
            g.setFont(new Font(fontNames[random.nextInt(3)], Font.ITALIC, height));
            //随机获得当前验证码的字符
            char codeChar = ValidateCodeUtil.codeChars.charAt(random.nextInt(charsLength));
            validationCode.append(codeChar);
            //随机设置当前验证码字符的颜色
            g.setColor(ValidateCodeUtil.getRandomColor(10, 100));
            //在图形上输出验证码字符，x和y都是随机生成的
            g.drawString(String.valueOf(codeChar), 16 * i + random.nextInt(7), height - random.nextInt(6));
        }
        //获得HttpSession对象
        HttpSession session = request.getSession();
        //设置session对象5分钟失效
        session.setMaxInactiveInterval(5 * 60);
        //将验证码保存在session对象中,key为validation_code
        session.setAttribute("validation_code", validationCode.toString());
        //关闭Graphics对象
        g.dispose();
        OutputStream outS = response.getOutputStream();
        ImageIO.write(image, "JPEG", outS);
    }

    @RequestMapping("checkValidateCode3")
    @ResponseBody
    public ResultData checkValidateCode(HttpServletRequest request, HttpServletResponse response, String valideCode, String mobile, String invitePhone) throws IOException {
        Subbranch inputMobileSubbranch = loginB2bService.validateMobile(mobile);
        Subbranch invitePhoneSubbranch = loginB2bService.validateMobile(invitePhone);
        if (inputMobileSubbranch == null) {
            if (invitePhoneSubbranch != null) {
                try {
                    ValidateUtils.notNull(valideCode, "验证码为空");
                    //获得HttpSession对象
                    HttpSession session = request.getSession();
                    //将验证码保存在session对象中,key为validation_code
                    if (session.getAttribute("validation_code") == null) {
                        return ResultData.createFail("验证码已过期");
                    }
                    if (!valideCode.toLowerCase().equals(session.getAttribute("validation_code").toString().toLowerCase())) {
                        return ResultData.createFail("验证码不正确");
                    }
                    session.setAttribute("validation_code", null);
                    return ResultData.createSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResultData.createFail("验证失败");
                }
            } else {
                return ResultData.createFail("邀请人帐号不存在！");
            }
        } else {
            return ResultData.createFail("该手机号已注册!");
        }

    }

    @RequestMapping("checkValidateCode2")
    @ResponseBody
    public ResultData checkValidateCode(HttpServletRequest request, HttpServletResponse response, String valideCode, String mobile) throws IOException {
        Subbranch inputMobileSubbranch = loginB2bService.validateMobile(mobile);
        if (inputMobileSubbranch == null) {
            try {
                ValidateUtils.notNull(valideCode, "验证码为空");
                //获得HttpSession对象
                HttpSession session = request.getSession();
                //将验证码保存在session对象中,key为validation_code
                if (session.getAttribute("validation_code") == null) {
                    return ResultData.createFail("验证码已过期");
                }
                if (!valideCode.toLowerCase().equals(session.getAttribute("validation_code").toString().toLowerCase())) {
                    return ResultData.createFail("验证码不正确");
                }
                session.setAttribute("validation_code", null);
                return ResultData.createSuccess();
            } catch (Exception e) {
                e.printStackTrace();
                return ResultData.createFail("验证失败");
            }

        } else {
            return ResultData.createFail("该手机号已注册!");
        }
    }



    @RequestMapping("checkValidateCode1")
    @ResponseBody
    public ResultData checkValidateCode1(HttpServletRequest request, HttpServletResponse response, String valideCode , String mobile) throws IOException {
        Subbranch inputMobileSubbranch = loginB2bService.validateMobile(mobile);
        if (inputMobileSubbranch == null) {
            return  ResultData.createFail("该手机号未注册！");
        }
        try {
            ValidateUtils.notNull(valideCode, "验证码为空");
            //获得HttpSession对象
            HttpSession session = request.getSession();
            //将验证码保存在session对象中,key为validation_code
            if (session.getAttribute("validation_code") == null) {
                return ResultData.createFail("验证码已过期");
            }
            if (!valideCode.toLowerCase().equals(session.getAttribute("validation_code").toString().toLowerCase())) {
                return ResultData.createFail("验证码不正确");
            }
            session.setAttribute("validation_code", null);
            return ResultData.createSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.createFail("验证失败");
        }

    }

    @RequestMapping("checkImgCode")
    @ResponseBody
    public ResultData checkImgCode(HttpServletRequest request, String valideCode) throws IOException {
        try {
            ValidateUtils.notNull(valideCode, "验证码为空");
            //获得HttpSession对象
            HttpSession session = request.getSession();
            //将验证码保存在session对象中,key为validation_code
            if (session.getAttribute("validation_code") == null) {
                return ResultData.createFail("验证码已过期");
            }
            if (!valideCode.toLowerCase().equals(session.getAttribute("validation_code").toString().toLowerCase())) {
                return ResultData.createFail("验证码不正确");
            }
            session.setAttribute("validation_code", null);
            return ResultData.createSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.createFail("验证失败");
        }

    }



}


