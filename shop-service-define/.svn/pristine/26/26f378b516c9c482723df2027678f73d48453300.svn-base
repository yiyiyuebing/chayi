package pub.makers.shop.store.vo;

import pub.makers.shop.store.entity.GoodReceiptAddr;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kok on 2017/6/8.
 */
public class GoodReceiptAddrVo implements Serializable {
    private String id;

    private String userId; //用户Id

    private String receiptName; //收货人名称

    private String mobile; //手机号码

    private String fiexdPhone; //固定号码

    private String receiptEmail; //邮箱地址

    private Integer status; //状态(0-非默认,1-默认选中地址)

    private String provinceCode; //省编码

    private String provinceName; //省名称

    private String cityCode; //市编码

    private String cityName; //市名称

    private String areaCode; //区编码

    private String areaName; //区名称

    private String detailAddr; //详细地址

    private Date updateTime; //更新时间

    private Date createTime; //创建时间

    private String mailbox; //邮编

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReceiptName() {
        return receiptName;
    }

    public void setReceiptName(String receiptName) {
        this.receiptName = receiptName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFiexdPhone() {
        return fiexdPhone;
    }

    public void setFiexdPhone(String fiexdPhone) {
        this.fiexdPhone = fiexdPhone;
    }

    public String getReceiptEmail() {
        return receiptEmail;
    }

    public void setReceiptEmail(String receiptEmail) {
        this.receiptEmail = receiptEmail;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getDetailAddr() {
        return detailAddr;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public static GoodReceiptAddrVo fromGoodReceiptAddr(GoodReceiptAddr addr) {
        if (addr == null) {
            return null;
        }
        GoodReceiptAddrVo vo = new GoodReceiptAddrVo();
        vo.setId(addr.getId() == null ? null : addr.getId().toString());
        vo.setUserId(addr.getUserId() == null ? null : addr.getUserId().toString());
        vo.setReceiptName(addr.getReceiptName());
        vo.setMobile(addr.getMobile());
        vo.setFiexdPhone(addr.getFiexdPhone());
        vo.setReceiptEmail(addr.getReceiptEmail());
        vo.setStatus(addr.getStatus());
        vo.setProvinceCode(addr.getProvinceCode());
        vo.setProvinceName(addr.getProvinceName());
        vo.setCityCode(addr.getCityCode());
        vo.setCityName(addr.getCityName());
        vo.setAreaCode(addr.getAreaCode());
        vo.setAreaName(addr.getAreaName());
        vo.setDetailAddr(addr.getDetailAddr());
        vo.setUpdateTime(addr.getUpdateTime());
        vo.setCreateTime(addr.getCreateTime());
        vo.setMailbox(addr.getMailbox());
        return vo;
    }

    public GoodReceiptAddr toGoodReceiptAddr() {
        GoodReceiptAddr addr = new GoodReceiptAddr();
        addr.setId(this.getId() == null ? null : Long.valueOf(this.getId()));
        addr.setUserId(this.getUserId() == null ? null : Long.valueOf(this.getUserId()));
        addr.setReceiptName(this.getReceiptName());
        addr.setMobile(this.getMobile());
        addr.setFiexdPhone(this.getFiexdPhone());
        addr.setReceiptEmail(this.getReceiptEmail());
        addr.setStatus(this.getStatus());
        addr.setProvinceCode(this.getProvinceCode());
        addr.setProvinceName(this.getProvinceName());
        addr.setCityCode(this.getCityCode());
        addr.setCityName(this.getCityName());
        addr.setAreaCode(this.getAreaCode());
        addr.setAreaName(this.getAreaName());
        addr.setDetailAddr(this.getDetailAddr());
        addr.setUpdateTime(this.getUpdateTime());
        addr.setCreateTime(this.getCreateTime());
        addr.setMailbox(this.getMailbox());
        return addr;
    }
}
