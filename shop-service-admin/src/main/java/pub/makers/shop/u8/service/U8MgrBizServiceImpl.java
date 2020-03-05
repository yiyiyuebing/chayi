package pub.makers.shop.u8.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.lantu.base.common.entity.BoolType;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.baseOrder.entity.OrderListPayment;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderStatus;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.BaseOrder;
import pub.makers.shop.baseOrder.pojo.PaymentQuery;
import pub.makers.shop.baseOrder.service.OrderPaymentBizService;
import pub.makers.shop.cargo.entity.CargoClassify;
import pub.makers.shop.cargo.entity.CargoSku;
import pub.makers.shop.cargo.service.CargoClassifyBizService;
import pub.makers.shop.cargo.service.CargoSkuBizService;
import pub.makers.shop.finance.entity.FinanceAccountsPay;
import pub.makers.shop.finance.entity.FinanceAccountspayItem;
import pub.makers.shop.finance.service.FinanceAccountsPayAdminService;
import pub.makers.shop.finance.service.FinanceAccountsPayBizService;
import pub.makers.shop.finance.service.FinanceAccountspayItemBizService;
import pub.makers.shop.message.enums.MessageStatus;
import pub.makers.shop.message.service.MessageBizService;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSample;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSampleBizService;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrder;
import pub.makers.shop.store.entity.SalesReturnReason;
import pub.makers.shop.store.entity.StoreLevel;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.entity.VtwoGoodPackage;
import pub.makers.shop.store.service.*;
import pub.makers.shop.tradeGoods.entity.TradeHeadStore;
import pub.makers.shop.tradeGoods.service.TradeHeadStoreBizService;
import pub.makers.shop.tradeOrder.entity.Indent;
import pub.makers.shop.tradeOrder.vo.IndentU8Vo;
import pub.makers.shop.u8.util.U8EaiUtil;
import pub.makers.shop.u8.vo.U8OrderListVo;
import pub.makers.shop.u8.vo.U8OrderVo;
import pub.makers.shop.user.entity.WeixinUserInfo;
import pub.makers.shop.user.service.WeixinUserInfoBizService;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dy on 2017/6/24.
 */
@Service(version = "1.0.0")
public class U8MgrBizServiceImpl implements U8MgrBizSerivce {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Reference(version = "1.0.0")
    private FinanceAccountsPayBizService financeAccountsPayBizService;
    @Autowired
    private FinanceAccountsPayAdminService financeAccountsPayAdminService;
    @Reference(version = "1.0.0")
    private WeixinUserInfoBizService weixinUserInfoBizService;
    @Reference(version = "1.0.0")
    private SubbranchBizService subbranchBizService;
    @Reference(version = "1.0.0")
    private SalesReturnReasonBizService salesReturnReasonBizService;
    @Reference(version = "1.0.0")
    private SubbranchAccountBizService subbranchAccountBizService;
    @Reference(version = "1.0.0")
    private TradeHeadStoreBizService headStoreBizService;
    @Reference(version = "1.0.0")
    private CargoSkuBizService cargoSkuBizService;
    @Reference(version = "1.0.0")
    private VtwoGoodPackageBizService vtwoGoodPackageBizService;
    @Reference(version = "1.0.0")
    private PurchaseGoodsBizService purchaseGoodsBizService;
    @Reference(version = "1.0.0")
    private CargoClassifyBizService cargoClassifyBizService;
    @Reference(version = "1.0.0")
    private PurchaseGoodsSampleBizService purchaseGoodsSampleBizService;
    @Reference(version = "1.0.0")
    private FinanceAccountspayItemBizService financeAccountspayItemBizService;
    @Reference(version = "1.0.0")
    private MessageBizService messageBizService;
    @Reference(version = "1.0.0")
    private OrderPaymentBizService orderPaymentBizService;
    @Reference(version = "1.0.0")
    private StoreLevelMgrBizService storeLevelMgrBizService;


    public void u8OrderSync(String orderId, OrderType orderType, OrderBizType orderBizType, Integer stage) {
        logger.info("U8 同步开始~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        FinanceAccountsPay financeAccountspayOldVo = financeAccountsPayBizService.getByOrderId(orderId);
        U8OrderVo indentU8Vo = getU8OrderVo(orderId, orderBizType);
        String orderIdInfo = "订单号：" + indentU8Vo.getName() + ",订单ID：" + indentU8Vo.getId() + "--->";
        if (orderType == OrderType.normal || orderType == OrderType.coupon) {
            if (financeAccountspayOldVo == null) {
                accountNormal(indentU8Vo);
            } else {
                logger.error(orderIdInfo + " U8销售单："+ financeAccountspayOldVo.getU8OrderId() + " 销售单已存在");
            }
        } else if (orderType == OrderType.presell) {
            if (financeAccountspayOldVo == null) {
                accountPresell(indentU8Vo, null);
            } else {
                logger.error(orderIdInfo + " U8销售单："+ financeAccountspayOldVo.getU8OrderId() + " 销售单已存在");
            }

        }
        logger.info(orderIdInfo + " U8 同步结束");

    }

    private void accountPresell(U8OrderVo indentU8Vo, FinanceAccountsPay financeAccountspay) {

        String orderIdInfo = "订单号：" + indentU8Vo.getName() + ",订单ID：" + indentU8Vo.getId() + "--->";

        fmtU8OrderVo(indentU8Vo); //订单数据处理

        if (indentU8Vo.getIndentList() == null || !(indentU8Vo.getIndentList().size() > 0)) {
            return;
        }

        financeAccountspay = financeAccountspay != null ? financeAccountspay : new FinanceAccountsPay();
        FinanceAccountspayItem financeAccountspayItemVo = financeAccountspay.getId() != null
                ? financeAccountspayItemBizService.getFacpiByFacp(financeAccountspay.getId()) : new FinanceAccountspayItem();

        fmtFinanceAccountsPay(financeAccountspay, indentU8Vo);
        financeAccountspay = financeAccountsPayBizService.saveOrUpdate(financeAccountspay);
        fmtFinanceAccountsPayItem(financeAccountspayItemVo, financeAccountspay, indentU8Vo);

        financeAccountspayItemBizService.saveOrUpdate(financeAccountspayItemVo);

        /*logger.info(orderIdInfo + "u8InterfaceService account msg :{}",
                financeAccountspay.getId() + "创建成功，收款单号为：" + indentU8Vo.getId());*/
        try {
            String u8OrderId = U8EaiUtil.U8EaiPurchaseSalesOrder(indentU8Vo, financeAccountspay.getId() + "", indentU8Vo.getOrderBizType());
            if (u8OrderId == null || "".equals(u8OrderId)) {
                logger.error(orderIdInfo + " U8对接销售单失败：返回信息为空！");
                financeAccountspay.setStatue(1);
                // 记录销售单对接失败信息
                messageBizService.addErrorMsg(indentU8Vo.getBuyerId(), indentU8Vo.getSubbranchId(), MessageStatus.error.getDbData(), String.format("%s同步销售单失败，请及时处理！", indentU8Vo.getName()));
            } else {
                financeAccountspay.setU8OrderId(u8OrderId);
                financeAccountsPayBizService.saveOrUpdate(financeAccountspay);
                logger.info(orderIdInfo + " U8对接销售单成功");
            }
        } catch (Exception e) {
            logger.error(orderIdInfo + " U8对接销售单失败" + e);
            financeAccountspay.setStatue(1);
            financeAccountsPayBizService.saveOrUpdate(financeAccountspay);
            messageBizService.addErrorMsg(indentU8Vo.getBuyerId(), indentU8Vo.getSubbranchId(), MessageStatus.error.getDbData(), String.format("%s同步销售单失败，请及时处理！", indentU8Vo.getName()));
        }

        try {
            String u8AccountId = U8EaiUtil.U8Accept(financeAccountspay, financeAccountspayItemVo, indentU8Vo.getName());
            if (u8AccountId == null || "".equals(u8AccountId)) {
                logger.error(orderIdInfo + " U8对接收款单失败：返回信息为空！");
                financeAccountspay.setStatue(1);
                messageBizService.addErrorMsg(indentU8Vo.getBuyerId(), indentU8Vo.getSubbranchId(), MessageStatus.error.getDbData(), String.format("%s同步收款单失败，请及时处理！", indentU8Vo.getName()));
            } else {
                financeAccountspay.setU8AccountsId(u8AccountId);
                financeAccountsPayBizService.saveOrUpdate(financeAccountspay);
                logger.info(orderIdInfo + " U8对接收款单成功 :{}",
                        " u8AccountId=" + u8AccountId);
            }
        } catch (Exception e) {
            logger.error(orderIdInfo + " U8对接收款单失败:", e);
            financeAccountspay.setStatue(1);
            financeAccountsPayBizService.saveOrUpdate(financeAccountspay);
            messageBizService.addErrorMsg(indentU8Vo.getBuyerId(), indentU8Vo.getSubbranchId(), MessageStatus.error.getDbData(), String.format("%s同步收款单失败，请及时处理！", indentU8Vo.getName()));
        }


    }

    /**
     * 同步正常销售单和销售单
     * @param indentU8Vo
     */
    private void accountNormal(U8OrderVo indentU8Vo) {
        String orderIdInfo = "订单号：" + indentU8Vo.getName() + ",订单ID：" + indentU8Vo.getId() + "--->";
        try {


                fmtU8OrderVo(indentU8Vo); //订单数据处理

                if (indentU8Vo.getIndentList() == null || !(indentU8Vo.getIndentList().size() > 0)) {
                    return;
                }

                FinanceAccountsPay financeAccountspay = new FinanceAccountsPay();
                fmtFinanceAccountsPay(financeAccountspay, indentU8Vo);
                financeAccountspay = financeAccountsPayBizService.saveOrUpdate(financeAccountspay);


                FinanceAccountspayItem financeAccountspayItemVo = new FinanceAccountspayItem();
                fmtFinanceAccountsPayItem(financeAccountspayItemVo, financeAccountspay, indentU8Vo);
                financeAccountspayItemBizService.saveOrUpdate(financeAccountspayItemVo);


                logger.info(orderIdInfo + "创建收款单成功, 收款单号为：" + indentU8Vo.getId());
                try {
                    String u8OrderId = U8EaiUtil.U8EaiPurchaseSalesOrder(indentU8Vo, financeAccountspay.getId() + "", indentU8Vo.getOrderBizType());
                    if (u8OrderId == null || "".equals(u8OrderId)) {
                        logger.error(orderIdInfo + " U8对接销售单失败 :{}", "返回信息为空！");
                        // 记录销售单对接失败信息
                        messageBizService.addErrorMsg(indentU8Vo.getBuyerId(), indentU8Vo.getSubbranchId(), MessageStatus.error.getDbData(), String.format("%s同步销售单失败，请及时处理！", indentU8Vo.getName()));
                    } else {
                        financeAccountspay.setU8OrderId(u8OrderId);
                        financeAccountsPayBizService.saveOrUpdate(financeAccountspay);
                        logger.info(orderIdInfo + " U8对接销售单成功 :{}",
                                "u8OrderId=" + u8OrderId);
                    }
                } catch (Exception e) {
                    logger.error(orderIdInfo + " U8对接销售单失败 :{}", e.getMessage());
                    financeAccountspay.setStatue(1);
                    financeAccountsPayBizService.saveOrUpdate(financeAccountspay);
                    messageBizService.addErrorMsg(indentU8Vo.getBuyerId(), indentU8Vo.getSubbranchId(), MessageStatus.error.getDbData(), String.format("%s同步销售单失败，请及时处理！", indentU8Vo.getName()));
                }

                if (OrderType.coupon.name().equals(indentU8Vo.getOrderType())) { //提货券无需同步收款单
                    return;
                }

                try {
                    String u8AccountId = U8EaiUtil.U8Accept(financeAccountspay, financeAccountspayItemVo, indentU8Vo.getName());
                    if (u8AccountId == null || "".equals(u8AccountId)) {
                        logger.error(orderIdInfo + " U8对接收款单失败 :{}", "返回信息为空！");
                        messageBizService.addErrorMsg(indentU8Vo.getBuyerId(), indentU8Vo.getSubbranchId(), MessageStatus.error.getDbData(), String.format("%s同步收款单失败，请及时处理！", indentU8Vo.getName()));
                    } else {
                        financeAccountspay.setU8AccountsId(u8AccountId);
                        financeAccountsPayBizService.saveOrUpdate(financeAccountspay);
                        logger.info(orderIdInfo + " U8对接收款单成功 :{}",
                                " u8AccountId=" + u8AccountId);
                    }
                } catch (Exception e) {
                    logger.error(orderIdInfo + " U8对接收款单失败 :{}", e);
                    financeAccountspay.setStatue(1);
                    financeAccountsPayBizService.saveOrUpdate(financeAccountspay);
                    messageBizService.addErrorMsg(indentU8Vo.getBuyerId(), indentU8Vo.getSubbranchId(), MessageStatus.error.getDbData(), String.format("%s同步收款单失败，请及时处理！", indentU8Vo.getName()));
                }


        } catch (NumberFormatException e) {
            logger.error(orderIdInfo + " NumberFormatException error: {}", e);
            e.printStackTrace();
        } catch (Exception e) {
            logger.error(orderIdInfo + " Exception error: {}", e);
            e.printStackTrace();
        }
    }

    @Override
    public String addU8Order() {
        // TODO Auto-generated method stub
        logger.info("addU8Order");
        String result = "同步成功";
        try {
            List<FinanceAccountsPay> financeAccountspayVoList = financeAccountsPayAdminService.getVoByU8Missing("order");
            if (financeAccountspayVoList != null) {
                for(FinanceAccountsPay financeAccountspayVo : financeAccountspayVoList){
                    Long orderId = financeAccountspayVo.getOrderId();
                    U8OrderVo indentU8Vo = getU8OrderVo(orderId.toString(), OrderBizType.valueOf(financeAccountspayVo.getOrderBizType()));
                    String orderIdInfo = "订单号：" + indentU8Vo.getName() + ",订单ID：" + indentU8Vo.getId() + "--->";
                    if (indentU8Vo == null) {
                        continue;
                    }

                    if (StringUtils.isNotBlank(financeAccountspayVo.getOrderType()) && OrderType.valueOf(financeAccountspayVo.getOrderType()) == OrderType.presell) {
                        continue;
                    }

                    fmtU8OrderVo(indentU8Vo); //订单数据处理
                    if (indentU8Vo.getIndentList() == null || !(indentU8Vo.getIndentList().size() > 0)) {
                        return null;
                    }
                    String u8OrderId = null;
                    try {
                        u8OrderId = U8EaiUtil.U8EaiSalesOrder(indentU8Vo, financeAccountspayVo.getId().toString(), OrderBizType.valueOf(financeAccountspayVo.getOrderBizType()));
                    } catch (Exception e) {
                        logger.error(orderIdInfo + " addU8Order U8对接销售单失败 :{}", e);
                        return e.getMessage();
                    }
                    try {
                        if (u8OrderId == null || "".equals(u8OrderId)) {
                            logger.error(orderIdInfo + " addU8Order U8对接销售单失败 :{}", "返回信息为空！");
                        } else {
                            financeAccountspayVo.setU8OrderId(u8OrderId);
                            financeAccountsPayBizService.saveOrUpdate(financeAccountspayVo);
                            logger.info(orderIdInfo + " addU8Order U8对接销售单成功 :{}",
                                    "u8OrderId=" + u8OrderId);
                        }
                        result = "同步成功";
                    } catch (Exception e) {
                        logger.error(orderIdInfo + " addU8Order U8对接销售单失败 :{}", e);
                        result = "同步失败";
                    }
                }
            } else {
                logger.error(" addU8Order msg : {}", "不存在未同步U8销售订单信息！");
                result =  "同步成功！不存在未同步U8销售订单信息!";
            }
        } catch (NumberFormatException e) {
            logger.error("account NumberFormatException error: {}", e);
            e.printStackTrace();
            result = "同步失败";
        } catch (Exception e) {
            logger.error("account Exception error: {}", e);
            e.printStackTrace();
            result = "同步失败";
        }
        return result;
    }

    @Override
    public String addU8Accept() {
        // TODO Auto-generated method stub
        logger.info("u8InterfaceService addU8Accept");
        String result = "同步成功";
        try {
            List<FinanceAccountsPay> financeAccountspayVoList = financeAccountsPayAdminService.getVoByU8Missing("accept");
            if (financeAccountspayVoList != null) {
                for(FinanceAccountsPay financeAccountspayVo : financeAccountspayVoList){
                    Long orderId = financeAccountspayVo.getOrderId();
                    U8OrderVo indentU8Vo = getU8OrderVo(orderId.toString(), OrderBizType.valueOf(financeAccountspayVo.getOrderBizType()));
                    String orderIdInfo = "订单号：" + indentU8Vo.getName() + ",订单ID：" + indentU8Vo.getId() + "--->";
                    FinanceAccountspayItem financeAccountspayItemVo =financeAccountspayItemBizService.getFacpiByFacp(financeAccountspayVo.getId());
                    String u8AccountId = "";
                    try {
                        u8AccountId = U8EaiUtil.U8Accept(financeAccountspayVo, financeAccountspayItemVo, indentU8Vo.getName());
                    } catch (Exception e) {
                        logger.error(orderIdInfo + " addU8Accept U8对接收款单失败 :{}", e);
                        return e.getMessage();
                    }
                    try {
                        if (u8AccountId == null || "".equals(u8AccountId)) {
                            logger.error(orderIdInfo + " addU8Accept U8对接收款单失败 :{}", "返回信息为空！");
                        } else {
                            financeAccountspayVo.setU8AccountsId(u8AccountId);
                            financeAccountsPayBizService.saveOrUpdate(financeAccountspayVo);
                            logger.info(orderIdInfo + " addU8Accept U8对接收款单成功 :{}",
                                    " u8AccountId=" + u8AccountId);
                            result = "同步成功";
                        }
                    } catch (Exception e) {
                        logger.error(orderIdInfo + " addU8Accept U8对接收款单失败 :{}", e);
                        result = "同步失败";
                    }
                }
            } else {
                result = "同步成功！不存在未同步U8收款单信息！";
                logger.error(" addU8Accept msg : {}", "不存在未同步U8收款单信息！");
            }
        } catch (NumberFormatException e) {
            logger.error(" account NumberFormatException error: {}", e);
            e.printStackTrace();
            result = "同步失败";
        } catch (Exception e) {
            logger.error("u8InterfaceService account Exception error: {}", e);
            e.printStackTrace();
            result = "同步失败";
        }
        return result;
    }

    @Override
    public String asychStockFromU8(String cInvCode) throws Exception {
        return U8EaiUtil.U8EaiGetStock(cInvCode);
    }


    private void fmtFinanceAccountsPay(FinanceAccountsPay financeAccountspay, U8OrderVo indentU8Vo) {
        financeAccountspay.setOrderId(Long.parseLong(indentU8Vo.getId()));
        financeAccountspay.setStatue(0);
        financeAccountspay.setVouchtype("48");
        financeAccountspay.setVouchdate(new Date());
        financeAccountspay.setCustomerid(Long.parseLong(indentU8Vo.getSubbranchId()));
        financeAccountspay.setCustomercode(indentU8Vo.getShopCode() == null ? "" : indentU8Vo.getShopCode());
        financeAccountspay.setCustomername(indentU8Vo.getStoreName());
        financeAccountspay.setDepartmentcode(indentU8Vo.getDepartmentCode());
        financeAccountspay.setDigest(indentU8Vo.getBuyerRemark());
        financeAccountspay.setOrderType(indentU8Vo.getOrderType());
        financeAccountspay.setOrderBizType(indentU8Vo.getOrderBizType());
        financeAccountspay.setBalancecode("现金");

        if (indentU8Vo.getPayType() == 1) {
            financeAccountspay.setBalanceitemcode("10120701");
        } else {
            financeAccountspay.setBalanceitemcode("10120702");
        }

        financeAccountspay.setForeigncurrency("人民币");
        financeAccountspay.setCurrencyrate(1.00D);
        financeAccountspay.setAmount(Double.parseDouble(indentU8Vo.getPaymentAmount()));
        financeAccountspay.setOriginalamount(Double.parseDouble(indentU8Vo.getPaymentAmount()));
        financeAccountspay.setOperator("demo");
        financeAccountspay.setFlag("AR");
        financeAccountspay.setNote(indentU8Vo.getBuyerRemark());
    }

    private void fmtFinanceAccountsPayItem(FinanceAccountspayItem financeAccountspayItem, FinanceAccountsPay financeAccountspay, U8OrderVo indentU8Vo) {
        financeAccountspayItem.setMainid(financeAccountspay.getId());
        financeAccountspayItem.setType(0);
        financeAccountspayItem.setCustomerid(Long.parseLong(indentU8Vo.getSubbranchId()));
        financeAccountspayItem.setCustomercode(indentU8Vo.getShopCode());
        financeAccountspayItem.setCustomername(indentU8Vo.getStoreName());
        financeAccountspayItem.setDepartmentcode(indentU8Vo.getDepartmentCode());
        financeAccountspayItem.setAmount(new BigDecimal(indentU8Vo.getPaymentAmount()));
        financeAccountspayItem.setOriginalamount(new BigDecimal(indentU8Vo.getPaymentAmount()));
        financeAccountspayItem.setItemcode("11220101");
    }



    private void fmtU8OrderVo(U8OrderVo indentU8Vo) {

        BigDecimal amount = new BigDecimal("0");
        List<U8OrderListVo> voList = new ArrayList<>();
        if (StringUtils.isNotBlank(indentU8Vo.getBuyerCarriage())
                && Double.parseDouble(indentU8Vo.getBuyerCarriage()) != 0) {	  //运费
            U8OrderListVo carriage = new U8OrderListVo();
            carriage.setIsLoose("1");
            carriage.setCargoNo("70001");
            carriage.setSupplyPrice(indentU8Vo.getBuyerCarriage());
            carriage.setNumber(1);
            double sumAmount = Double.parseDouble(indentU8Vo.getBuyerCarriage()) * carriage.getNumber();
            amount = amount.add(new BigDecimal(sumAmount)); //运费价格
            carriage.setFinalAmount(sumAmount + "");
            voList.add(carriage);	//运费
        }


        VtwoGoodPackage vtwoGoodPackage = null;

        for (U8OrderListVo indentListVo : indentU8Vo.getIndentList()) {
            BigDecimal supplyPrice = new BigDecimal(
                    indentListVo.getSupplyPrice() == null ? "0" : indentListVo.getSupplyPrice() + "");
            BigDecimal num = new BigDecimal(
                    indentListVo.getNumber() == null ? "0" : indentListVo.getNumber() + "");


            if (indentListVo.getCargoNo() == null &&
                    "0".equals(indentListVo.getCargoSkuId()) &&
                    Long.valueOf(indentListVo.getTradeGoodSkuId()) < 100000000000L && OrderBizType.trade.name().equals(indentU8Vo.getOrderType())) {
                vtwoGoodPackage = vtwoGoodPackageBizService.getGoodPackageByBoomId(indentListVo.getTradeGoodSkuId());
                indentListVo.setCargoNo(vtwoGoodPackage.getCinvcodeParent());
            }
            String cargoNo = "";
            if (!"1".equals(indentListVo.getIsSample())) {
//                        cargoNo = indentListVo.getTradeGoodSku().getCode();
                amount = amount.add(supplyPrice.multiply(num)); //获取总供货价格
            }

            if (OrderType.presell.name().equals(indentU8Vo.getOrderType())) {
                indentListVo.setRemark("预售");
            }

            if ("T".equals(indentListVo.getGiftFlag())) {
                indentListVo.setRemark("赠品");
            }

            CargoClassify classify = new CargoClassify();
            if (indentU8Vo.getOrderBizType().equals("purchase")) {
                indentListVo.setIsLoose("1");

                classify = cargoClassifyBizService.getCargoClassifyById(indentListVo.getClassifyId());
                //代包服务处理
                if (classify != null && classify.getName().equals("散茶") && !"1".equals(indentListVo.getIsSample())) {
                    //散茶
                    U8OrderListVo sancha = new U8OrderListVo();
//                            cargoNo = cargoNo.substring(2, cargoNo.length());
//                            indentListVo.setCargoNo(cargoNo);
                    sancha.setTradeGoodSkuId(indentListVo.getTradeGoodSkuId());
                    sancha.setIsPur("1");
                    sancha.setIsLoose("1");
//                            if (StringUtils.isNotBlank(cargoNo)) {
//                                sancha.setCargoNo(cargoNo);
//                            }
                    sancha.setCargoNo(indentListVo.getCargoNo());
                    sancha.setIndentId(indentListVo.getIndentId());
                    sancha.setSupplyPrice(indentListVo.getSupplyPrice());


                    sancha.setNumber(indentListVo.getNumber());
                    double sumAmount = Double.parseDouble(indentListVo.getSupplyPrice()) * indentListVo.getNumber();
                    sancha.setFinalAmount(sumAmount + "");
                    voList.add(sancha);

                } else if(indentListVo.getIsSample() != null && BoolType.T.name().equals(indentListVo.getIsSample())) {
                    //样品
                    PurchaseGoodsSample sampleVo = new PurchaseGoodsSample();
                    sampleVo= purchaseGoodsSampleBizService.getPurGoodsSampleByGoodsId(indentListVo.getGoodId());
                    indentListVo.setSampleNum(Float.parseFloat(sampleVo.getSampleSku().replace("斤", "")) * indentListVo.getNumber() + ""); //样品数量（购买数量*样品规格）
                    indentListVo.setFinalAmount((sampleVo.getSamplePrice() * indentListVo.getNumber()) + "");  //样品的价格 (购买数量*样品价格)
                    amount = amount.add(new BigDecimal(sampleVo.getSamplePrice() * indentListVo.getNumber())); //加上样品的总价格 (购买数量*样品价格)
                    indentListVo.setCargoNo(sampleVo.getSampleCode());	 //样式编码
                    indentListVo.setRemark("样品：" + sampleVo.getSampleSku());		//备注（样品：样品规格  如：样品：0.1斤）
                    voList.add(indentListVo);
                } else {
                    if (!OrderType.normal.name().equals(indentU8Vo.getOrderType())) {
                        indentListVo.setSupplyPrice(indentListVo.getFinalAmount());
                    }
                    voList.add(indentListVo);
                }
            } else {
                voList.add(indentListVo);
            }
        }

        indentU8Vo.setIndentList(voList);
        indentU8Vo.setAmount(amount);

    }

    @Override
    public U8OrderVo getU8OrderVo(String orderId, OrderBizType orderBizType) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", orderId);

        String sql = "";
        if (OrderBizType.trade == orderBizType) {
            sql = FreeMarkerHelper.getValueFromTpl("sql/u8/findTradeU8OrderVo.sql", map);
        } else {
            sql = FreeMarkerHelper.getValueFromTpl("sql/u8/findPurchaseU8OrderVo.sql", map);
        }
        U8OrderVo u8OrderVo = jdbcTemplate.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(U8OrderVo.class));
        if (u8OrderVo.getTradeHeadStoreId() != null) {
            TradeHeadStore tradeHeadStoreVo = headStoreBizService.getById(u8OrderVo
                    .getTradeHeadStoreId());
            if (tradeHeadStoreVo != null) {
                u8OrderVo.setTradeHeadStoreId(u8OrderVo.getTradeHeadStoreId() + "");
                u8OrderVo.setStoreName(tradeHeadStoreVo.getName());
            }
        }
        if (u8OrderVo.getSubbranchId() != null) {
            Subbranch subbranch = subbranchAccountBizService.getMainSubbranch(u8OrderVo.getSubbranchId() + "");
            if (subbranch != null) {
                u8OrderVo.setSubbranchId(u8OrderVo.getSubbranchId() + "");
                u8OrderVo.setStoreName(subbranch.getName());
                u8OrderVo.setShopCode(subbranch.getNumber());
                u8OrderVo.setDepartmentCode(subbranch.getDepartmentNum());
            }
        }

        if (u8OrderVo.getBuyerId() != null) {
            if (u8OrderVo.getOrderBizType().equals("trade")){
                WeixinUserInfo weixinUserInfo = weixinUserInfoBizService.getWxUserById(Long.parseLong(u8OrderVo.getBuyerId()));
                Map<String, Object> buyer = transBean2Map(weixinUserInfo);
                u8OrderVo.setBuyer(buyer);
            }else {
                Map<String, Object> buyer = transBean2Map(subbranchBizService.shopInfo(u8OrderVo.getBuyerId()));
                u8OrderVo.setBuyer(buyer);
            }
        }
        if (u8OrderVo.getReferrerId() != null) {
            if (u8OrderVo.getOrderBizType().equals("trade")) {
                Map<String, Object> referrer = transBean2Map(weixinUserInfoBizService.getWxUserById(Long.parseLong(u8OrderVo.getBuyerId())));
                u8OrderVo.setReferrer(referrer);
            } else {

                Map<String, Object> buyer = transBean2Map(subbranchBizService.shopInfo(u8OrderVo.getBuyerId()));
                u8OrderVo.setBuyer(buyer);
            }
        }

        if (u8OrderVo.getReturnId() != null) {
            SalesReturnReason reason = salesReturnReasonBizService.getById(u8OrderVo.getReturnId());
            u8OrderVo.setReturnName(StringUtils.isNotEmpty(reason.getReason()) ? reason.getReason() : null);
        }
        if (u8OrderVo.getRefundId() != null) {
            // 后前增加退款id
            SalesReturnReason reason = salesReturnReasonBizService.getById(u8OrderVo.getRefundId());
            u8OrderVo.setRefundName(StringUtils.isNotEmpty(reason.getReason()) ? reason.getReason() : null);
        }

        u8OrderVo.setOrderBizType(orderBizType.name());

        u8OrderVo.setIndentList(getU8OrderList(u8OrderVo, u8OrderVo.getSubbranchId(), u8OrderVo.getOrderType(), u8OrderVo.getOrderBizType()));
        return u8OrderVo;
    }

    @Override
    public List<U8OrderListVo> getU8OrderList(U8OrderVo u8OrderVo, String subbranchId, String orderType, String orderBizType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", u8OrderVo.getId());
        String sql = FreeMarkerHelper.getValueFromTpl("sql/u8/findU8OrderListVo.sql", map);

        List<U8OrderListVo> u8OrderListVos = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(U8OrderListVo.class));

        BigDecimal payAmount = BigDecimal.ZERO;
        for (U8OrderListVo u8OrderListVo : u8OrderListVos) {
            if ("T".equals(u8OrderListVo.getGiftFlag())) {
                u8OrderListVo.setSupplyPrice("0.00");
            }
            if (orderBizType.equals("trade")) {
                if (OrderType.normal != OrderType.valueOf(orderType)) {
                    StoreLevel storeLevel = storeLevelMgrBizService.findStoreLevelBySubbranchId(subbranchId);
                    if (storeLevel.getName().indexOf("直营") > -1 || storeLevel.getName().indexOf("员工") > -1) {
                        u8OrderListVo.setSupplyPrice(u8OrderListVo.getFinalAmount());
                        BigDecimal supplyPrice = new BigDecimal(u8OrderListVo.getFinalAmount());
                        BigDecimal totalSupplyPrice = supplyPrice.multiply(new BigDecimal(u8OrderListVo.getNumber()));
                        payAmount = payAmount.add(totalSupplyPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        BigDecimal supplyPrice = new BigDecimal(u8OrderListVo.getSupplyPrice());
                        BigDecimal totalSupplyPrice = supplyPrice.multiply(new BigDecimal(u8OrderListVo.getNumber()));
                        payAmount = payAmount.add(totalSupplyPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                } else {
                    BigDecimal supplyPrice = new BigDecimal(u8OrderListVo.getSupplyPrice());
                    BigDecimal totalSupplyPrice = supplyPrice.multiply(new BigDecimal(u8OrderListVo.getNumber()));
                    payAmount = payAmount.add(totalSupplyPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
            }

        }
        if (orderBizType.equals("trade")) {
            if (OrderType.normal != OrderType.valueOf(orderType)) {
                String carriage = org.apache.commons.lang.StringUtils.isNotBlank(u8OrderVo.getCarriage()) ? u8OrderVo.getCarriage() : "0.00";
                payAmount = payAmount.add(new BigDecimal(carriage)).setScale(2, BigDecimal.ROUND_HALF_UP);
                u8OrderVo.setPaymentAmount(payAmount.toString());
            } else {
                String carriage = org.apache.commons.lang.StringUtils.isNotBlank(u8OrderVo.getCarriage()) ? u8OrderVo.getCarriage() : "0.00";
                payAmount = payAmount.add(new BigDecimal(carriage)).setScale(2, BigDecimal.ROUND_HALF_UP);
                u8OrderVo.setPaymentAmount(payAmount.toString());
            }
        }
        return u8OrderListVos;
    }


    public static Map<String, Object> transBean2Map(Object obj) {
        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }

        return map;

    }

    /**
     * 计算购买的包装个数
     * 通过purPricking分割然后判断第二个个数的长度
     *
     * @param bagValue        eg 1g
     * @param purPackingPrice eg 0.02/斤
     * @param number          购买数量
     * @return
     */
    public static String getPackNum(String bagValue, String purPackingPrice, String number) {
        String returnStr = "";
        try {
            String[] prices = purPackingPrice.split("/");
            BigDecimal bg = new BigDecimal(500);
            if (prices[1].length() == 2) {
                bg = new BigDecimal(1000);
            }
            bg = bg.multiply(new BigDecimal(number));//将购买公斤数转为克数
            BigDecimal g = bg.divide(new BigDecimal(bagValue.replace("g", "")), 0, BigDecimal.ROUND_UP);
            returnStr = g + "";
        } catch (Exception e) {
        }
        return returnStr;
    }
}
