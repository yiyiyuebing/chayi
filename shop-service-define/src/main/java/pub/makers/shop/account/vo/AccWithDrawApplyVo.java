package pub.makers.shop.account.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by devpc on 2017/9/29.
 */
public class AccWithDrawApplyVo implements Serializable {
    private String id;

    private String userId;//用户ID

    private String userType;//用户类型

    private BigDecimal amount;//提现金额

    private String withdrawType;//

    private String applyMan;//申请人

    private String applyPhone;

    private Date applyTime;//申请时间

    private String applyStatus;

    private String reviewMan;//审核人

    private String reviewReason;//审核原因

    private Date reviewTime;//申请时间

    private String operMan;

    private Date operTime;//操作时间

    private String bankAddr;

    private String bankCategory;//所属银行

    private String ownerName;//户名

    private Long storeId;//店铺id

    private String name;//店铺名称

    private String headImgUrl;//头像图片

    private String concatName;//客户名称

    private String departmentNum;//部门编码

    private String number;//店铺编码

    private String bankName;//银行卡名称

    private String bankCard;//银行卡号

    private String mobile;//银行预留手机号

    private Long connectId;//关联ID（店铺或者会员）

    private Integer connectType;//关联类型(1:分店;0:会员;2:总店)

    private String bankAddress;//开户行

}
