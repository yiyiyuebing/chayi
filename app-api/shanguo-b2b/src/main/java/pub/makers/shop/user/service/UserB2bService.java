package pub.makers.shop.user.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.service.SmsService;
import pub.makers.shop.store.pojo.SubbranchInfo;
import pub.makers.shop.store.service.BankCardBizService;
import pub.makers.shop.store.service.GoodReceiptAddrBizService;
import pub.makers.shop.store.service.SubbranchBizService;
import pub.makers.shop.store.vo.BankCardExtendVo;
import pub.makers.shop.store.vo.GoodReceiptAddrVo;
import pub.makers.shop.store.vo.SubbranchVo;
import pub.makers.shop.user.pojo.RestPwdVo;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */
@Service
public class UserB2bService {

    @Reference(version = "1.0.0")
    private SubbranchBizService subbranchBizService;
    @Reference(version = "1.0.0")
    private GoodReceiptAddrBizService goodReceiptAddrBizService;
    @Reference(version = "1.0.0")
    private SmsService smsService;
    @Reference(version = "1.0.0")
    private BankCardBizService bankCardBizService;


    public SubbranchVo shopInfo(String id){
        return  subbranchBizService.shopInfo(id);
    }

    /***
     * 修改店铺个人信息
     * @param info
     */
    public void updateShop(SubbranchInfo info) {
        subbranchBizService.updateShop(info);
    }


    /***
     * 收货地址查看
     * @param userId
     * @return
     */
    public List<GoodReceiptAddrVo> listGoodReceiptAddr(String userId){

        return goodReceiptAddrBizService.listGoodReceiptAddr(userId);
    }


    /**
     * id查找收货地址
     */
    public GoodReceiptAddrVo getById(String id){
       return goodReceiptAddrBizService.getById(id);
   }


    /**
     * 保存收货地址
     */
    public void createGoodReceiptAddr(GoodReceiptAddrVo addr){
       goodReceiptAddrBizService.createGoodReceiptAddr(addr);
   }


    /**
     * 更新收货地址
     */
    public void updateGoodReceiptAddr(GoodReceiptAddrVo addr){
       goodReceiptAddrBizService.updateGoodReceiptAddr(addr);
   }


    /**
     * 删除收获地址
     */
    public void delGoodReceiptAddr(String addrId, String userId){
       goodReceiptAddrBizService.delGoodReceiptAddr(addrId,userId);
   }


    /**
     * 设置默认收货地址
     */
    public void updateDefaultAddr(String addrId, String userId){
        goodReceiptAddrBizService.updateDefaultAddr(addrId,userId);
    }


    /***
     * 发送短信验证
     * @param smsType 验证类型
     * @param phoneNmuber 手机号
     */
    public void sendCheckCode( String phoneNmuber){
        String smsType="updatePassword";
        smsService.sendCheckCode(smsType, phoneNmuber);
    }

    //重置密码
    public void checkCode( String phoneNmuber){
        String smsType="resetPwd";
        smsService.sendCheckCode(smsType,phoneNmuber);
    }

    /**
     * 发送手机验证码
     * @param smsType
     * @param phoneNmuber
     */
    public void sendPhoneCode(String smsType, String phoneNmuber){
        smsService.sendCheckCode(smsType, phoneNmuber);
    }

    /**
     * 验证手机验证码
     * @param mobile
     * @param yzm
     * @param smsType
     * @return
     */
    public boolean checkPhoneCode(String mobile, String yzm, String smsType) {
        return smsService.checkCode(smsType, mobile, yzm);
    }


    /***
     * 验证手机验证码
     * @param smsType
     * @param phoneNmuber
     * @param yzm
     * @return
     */
    public 	boolean checkCode(String phoneNmuber, String yzm){
        String smsType="updatePassword";
        return  smsService.checkCode(smsType,phoneNmuber,yzm);
    }


    /***
     * 修改密码
     * @param id
     * @param password
     */
    public void updatePassword(String id, String password, String mobile){
       subbranchBizService.updatePassword(id, password, mobile);

   }
    public void resetPwd(RestPwdVo restPwdVo) {
        ValidateUtils.isTrue(restPwdVo.getPassword().equals(restPwdVo.getRepassword()), "两次输入密码不一样");
        subbranchBizService.updatePassword(null, restPwdVo.getPassword(), restPwdVo.getMobile());
    }

    public void bindBandCard(BankCardExtendVo info) {
        bankCardBizService.bind(info);
    }
}
