package pub.makers.shop.user.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dev.base.json.JsonUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.enums.SmsType;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.store.pojo.SubbranchInfo;
import pub.makers.shop.store.vo.*;
import pub.makers.shop.user.service.UserB2bService;
import pub.makers.shop.user.utils.AccountUtils;

/**
 * Created by Administrator on 2017/6/6.
 */
@Controller
@RequestMapping("/user")
public class UserInfoController {
    @Autowired
    private UserB2bService userB2bService;


    @RequestMapping("/userInfo")
    public String userInfo(Model model) {

    	String shopId = AccountUtils.getCurrShopId();
        SubbranchVo sv = userB2bService.shopInfo(shopId);
        List<ImageVo> imageVos = sv.getPicList();
        StringBuffer buf = new StringBuffer();
         for (ImageVo iv : imageVos) {
             String url = iv.getUrl();
             buf.append(url+",");
         }
         if (buf.length()>0) {
            buf.deleteCharAt(buf.length()-1);
         }

        model.addAttribute("buf",buf);
        model.addAttribute("sv",sv);

        return "www/user/userinfo";
    }

    @RequestMapping("/shopInfo")
    @ResponseBody
    public ResultData shopInfo() {
        String shopId = AccountUtils.getCurrShopId();
        SubbranchVo shopInfo = userB2bService.shopInfo(shopId);
        return ResultData.createSuccess("shopInfo", shopInfo);
    }


    @RequestMapping("/test1")
    @ResponseBody
    public ResultData test() {

        String id = "346372182137708544";
        SubbranchVo subbranchVo = userB2bService.shopInfo(id);

        return ResultData.createSuccess("subbranchVo",subbranchVo);
    }


    @RequestMapping("/updatePassword")
    public String updatePassword(Model model) {

        String shopId = AccountUtils.getCurrShopId();
        SubbranchVo sv = userB2bService.shopInfo(shopId);

        model.addAttribute("sv",sv);
        return "www/user/update_password";
    }


    @RequestMapping("/updatePassword2")
    public String updatePassword2() {

        return "www/user/update_password2";
    }


    @RequestMapping("/updatePassword3")
    public String updatePassword3() {

        return "www/user/update_password3";
    }


    @RequestMapping("/upPassword")
    @ResponseBody
    public ResultData upPassword(String password, String mobile) {
        String id = null;

        if (StringUtils.isBlank(mobile)) {
            id = AccountUtils.getCurrShopId();
        }
        if (StringUtils.isNotEmpty(password)) {
            userB2bService.updatePassword(id, password, mobile);
            return ResultData.createSuccess();
        } else {
            return ResultData.createFail("修改失败");
        }

    }


    @RequestMapping("/send")
    @ResponseBody
    public ResultData sendCheckCode( String phoneNmuber) {
        userB2bService.sendCheckCode(phoneNmuber);
        return  ResultData.createSuccess();
    }

    @RequestMapping("/sendPhoneCode")
    @ResponseBody
    public ResultData sendPhoneCode(String mobile, String msgType) {
        ValidateUtils.notNull(mobile, "手机号不能为空");
        ValidateUtils.notNull(msgType, "信息发送类型错误");
        userB2bService.sendPhoneCode(msgType, mobile);
        return  ResultData.createSuccess();
    }


    @RequestMapping("/checkCode")
    @ResponseBody
    public ResultData checkCode( String phoneNmuber, String yzm) {

        if (StringUtils.isNotEmpty(phoneNmuber)&& StringUtils.isNotEmpty(yzm)) {

            boolean rerult = userB2bService.checkCode( phoneNmuber, yzm);

            return ResultData.createSuccess("rerult", rerult);

        } else {
            return ResultData.createFail();
        }

    }


    @RequestMapping("/site")
    public String site() {
        return "www/user/receipt_address";
    }


    @RequestMapping("/address")
    @ResponseBody
    public ResultData address() {

        String userId= AccountUtils.getCurrShopId();
        List<GoodReceiptAddrVo> graList = userB2bService.listGoodReceiptAddr(userId);
        return ResultData.createSuccess("graList",graList);
    }


    @RequestMapping("/addressDetails")
    @ResponseBody
    public ResultData addressDetails (String id) {

        GoodReceiptAddrVo addr = userB2bService.getById(id);
        return  ResultData.createSuccess("addr",addr);
    }


    @RequestMapping("/addAddress")
    @ResponseBody
    public ResultData addAddress (@RequestParam(value = "modelJsonStr", required = true)String modelJsonStr) {

        if (StringUtils.isNotEmpty(modelJsonStr)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                GoodReceiptAddrVo addr = objectMapper.readValue(modelJsonStr,GoodReceiptAddrVo.class);
                addr.setUserId(AccountUtils.getCurrShopId());
                userB2bService.createGoodReceiptAddr(addr);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultData.createFail();
            }
            return ResultData.createSuccess();
        } else {
            return ResultData.createFail();
        }
    }


    @RequestMapping("/updateAddress")
    @ResponseBody
    public ResultData updateAddress (@RequestParam(value = "modelJsonStr", required = true)String modelJsonStr) {

        if (StringUtils.isNotEmpty(modelJsonStr)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                GoodReceiptAddrVo addr = objectMapper.readValue(modelJsonStr,GoodReceiptAddrVo.class);
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


    @RequestMapping("/delAddress")
    @ResponseBody
    public ResultData delAddress(String addrId) {

        String userId = AccountUtils.getCurrShopId();;
        if (StringUtils.isNotEmpty(addrId)) {
            userB2bService.delGoodReceiptAddr( addrId, userId);
        } else {
            return ResultData.createFail();
        }
        return ResultData.createSuccess();

    }


    @RequestMapping("/updateDefaultAddr")
    @ResponseBody
    public ResultData updateDefaultAddr(String addrId) {

        String userId = AccountUtils.getCurrShopId();;
        if (StringUtils.isNotEmpty(addrId)) {
            userB2bService.updateDefaultAddr(addrId, userId);
        } else {
            return ResultData.createFail();
        }
        return ResultData.createSuccess();

    }


    @RequestMapping("/updateShop")
    @ResponseBody
    public ResultData updateShop(@RequestParam(value = "modelJsonStr", required = true) String modelJsonStr) {

        if (StringUtils.isNotEmpty(modelJsonStr)) {
            ObjectMapper objectMapper = new ObjectMapper();
            SubbranchInfo  info = null;
            try {
                info = objectMapper.readValue(modelJsonStr, SubbranchInfo.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            userB2bService.updateShop(info);


            return ResultData.createSuccess();

        }else {
         return ResultData.createFail();
        }
    }

    @RequestMapping("/bindBandCard")
    @ResponseBody
    public ResultData bindBandCard(String modelJson, String yzmCode) {
        ValidateUtils.notNull(modelJson, "银行卡信息不完善");
        ValidateUtils.notNull(yzmCode, "验证码不能为空");
        BankCardExtendVo info = JsonUtils.toObject(modelJson, BankCardExtendVo.class);
        //验证手机号
        ValidateUtils.isTrue(userB2bService.checkPhoneCode(info.getMobile(), yzmCode, SmsType.bankcard.name()), "验证码验证失败");
        userB2bService.bindBandCard(info);
        return ResultData.createSuccess();

    }

}
