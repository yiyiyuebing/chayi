package pub.makers.shop.u8.util;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import pub.makers.shop.base.util.PropertiesLoader;
import pub.makers.shop.base.util.XMLUtil;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.finance.entity.FinanceAccountsPay;
import pub.makers.shop.finance.entity.FinanceAccountspayItem;
import pub.makers.shop.finance.vo.FinanceAccountspayItemVo;
import pub.makers.shop.finance.vo.FinanceAccountspayVo;
import pub.makers.shop.u8.vo.U8OrderListVo;
import pub.makers.shop.u8.vo.U8OrderVo;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2017/6/24.
 */
public class U8EaiUtil {
    private static Logger logger= LoggerFactory.getLogger(U8EaiUtil.class);
    private static PropertiesLoader loader = new PropertiesLoader("u8.properties");
    private static String u8url=loader.getProperty("u8.url");
    private static String sender=loader.getProperty("u8.sender");


    private U8EaiUtil(){
    }
    private static  String XMLtoStr(Document document) {
        String result = null;
        if (document != null) {
            result=document.asXML();
        }
        return result;
    }
    private static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
        date = calendar.getTime();
        return date;
    }

    private static String sendU8(Document doc) throws Exception{
//		 URL url = new URL("http://110.80.34.146:995/U8EAI/import.asp");
        URL url = new URL(u8url);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setConnectTimeout(3000000);
        con.setReadTimeout(3000000);
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setAllowUserInteraction(false);
        con.setUseCaches(false);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-type","application/x-www-form-urlencoded");
        //发送Request消息
        OutputStream out = con.getOutputStream();
        DataOutputStream dos = new DataOutputStream(out);
        String str=XMLtoStr(doc);
        logger.error("U8EaiUtil 传值给U8 --> {}",str);
        dos.write(str.getBytes("utf-8"));

        //获取Response消息
        InputStream in = con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in,"utf-8"));
        StringBuilder sb = new StringBuilder();
        String s = null;
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        String responseXml = sb.toString();
        logger.error("U8EaiUtil U8返回值 --> {}",responseXml);
        Document document = DocumentHelper.parseText(responseXml);
        Element root = document.getRootElement();
        Element item=root.element("item");
        String u8Key="";
        if(item !=null){
            Attribute attribute=item.attribute("u8key");
            if(attribute!=null){
                u8Key=attribute.getText();
            }else{
                attribute=item.attribute("dsc");
                throw new Exception(attribute.getText());
            }
        }
        return u8Key;
    }

    public static String U8Accept(FinanceAccountsPay vo, FinanceAccountspayItem voD, String orderNo)throws Exception{
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("utf-8");
        Element ufInterface = doc.addElement("ufinterface");
        ufInterface.addAttribute("roottag", "accept");
        ufInterface.addAttribute("bignoreextenduserdefines", "y");
        ufInterface.addAttribute("receiver", "u8");
        ufInterface.addAttribute("sender",sender);
        ufInterface.addAttribute("proc", "add");
        ufInterface.addAttribute("codeexchanged","N");
        ufInterface.addAttribute("exportneedexch","N");
        ufInterface.addAttribute("display","收款单");
        ufInterface.addAttribute("family","应收管理");
        Element accept= ufInterface.addElement("accept");
        Element header= accept.addElement("header");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curTimes = sdf.format(new Date());
        int month = new Date().getMonth()+1;//月
        header.addElement("vouchtype").addText("48");
        header.addElement("vouchcode").addText("0000000003");
//    	header.addElement("vouchcode").addText("0000000031");
        header.addElement("vouchdate").addText(curTimes);
        header.addElement("period").addText(month+"");
        header.addElement("customercode").addText(vo.getCustomercode());
        header.addElement("departmentcode").addText(vo.getDepartmentcode());
        header.addElement("personcode");
        header.addElement("itemclasscode");
        header.addElement("itemcode");
        header.addElement("itemname");
//    	header.addElement("VT_ID").addText("131383");

        header.addElement("orderid");
        if ("10120701".equals(vo.getBalanceitemcode())) {
            header.addElement("balancecode").addText("A");
        } else {
            header.addElement("balancecode").addText("B");
        }

        header.addElement("notecode");
        String digest="";
        if(vo.getDigest()!=null){
            digest=vo.getDigest();
        }
        logger.info("digest-------"+digest);
        if(digest.length()>10){
            digest = digest.substring(0,10)+"...";
        }
//    	header.addElement("digest").addText("优茶联："+vo.getCode()+" "+digest);
        header.addElement("digest").addText("优茶联："+orderNo);
        header.addElement("oppositebankcode");
        header.addElement("foreigncurrency").addText("人民币");
        header.addElement("currencyrate").addText("1");
        header.addElement("amount").addText(vo.getAmount()+"");
        header.addElement("originalamount").addText(vo.getOriginalamount() + "");
        header.addElement("operator").addText("demo");
        header.addElement("balanceitemcode").addText(vo.getBalanceitemcode());
        header.addElement("flag").addText("AR");
        header.addElement("sitemcode");
        header.addElement("citemname");
        header.addElement("oppositebankname");
        header.addElement("bankname");
        header.addElement("bankaccount");
        header.addElement("define1");
        header.addElement("define2");
        header.addElement("define3");
        header.addElement("define4");
        header.addElement("define5");
        header.addElement("define6");
        header.addElement("define7");
        header.addElement("define8");
        header.addElement("define9");
        header.addElement("define10");
        header.addElement("define11").addText(vo.getId() + "");
        header.addElement("define12");
        header.addElement("define13");
        header.addElement("define14");
        header.addElement("define15");
        header.addElement("define16");
        header.addElement("ccontracttype");
        header.addElement("ccontractid");
        header.addElement("iamount_s").addText("0");
        header.addElement("startflag").addText("0");

        Element body  = accept.addElement("body");
        Element entry = body.addElement("entry");
        entry.addElement("mainid").addText("1111");
        entry.addElement("type").addText("0");
        entry.addElement("customercode").addText(voD.getCustomercode());
        entry.addElement("originalamount").addText(voD.getOriginalamount() + "");
        entry.addElement("amount").addText(voD.getAmount()+"");
        entry.addElement("itemcode").addText(voD.getItemcode());
        entry.addElement("projectclass");
        entry.addElement("project");
        entry.addElement("departmentcode").addText(voD.getDepartmentcode());
        entry.addElement("personcode");
        entry.addElement("orderid");
        entry.addElement("itemname");
        entry.addElement("ccontype");
        entry.addElement("cconid");
        entry.addElement("iamt_s").addText("0");
        entry.addElement("iramt_s").addText("0");
        return sendU8(doc);
    }

    public static String U8EaiSalesOrder(U8OrderVo vo, String financeAcountId, OrderBizType orderBizType) throws Exception{
//		String operType="sale";
        String address=vo.getProvince() +
                (StringUtils.isNotBlank(vo.getCity()) ? vo.getCity() : "") +
                (StringUtils.isNotBlank(vo.getTown()) ? vo.getTown() : "") +
                vo.getAddress() +" 收货人:"+vo.getReceiver()+" 联系电话:"+vo.getReceiverPhone();
        String remarks="订单号："+vo.getName();
        if(vo.getBuyerRemark()!=null){
            remarks +=" 买家留言："+vo.getBuyerRemark();
        }
        List<U8OrderListVo> indentList= vo.getIndentList();
        //发票抬头
        String invoice="";
        if(vo.getNeedInvoice()){
            invoice="发票类型："+vo.getInvoiceName();
            if(vo.getInvoiceContent()!=null){
                invoice +=" 发票抬头："+vo.getInvoiceContent();
            }
        }
        Date createDate = vo.getCreateTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = sdf.format(createDate);
        Date nexDate= getNextDay(new Date()) ;
        String nextTime = sdf.format(nexDate);
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("utf-8");
        Element ufInterface = doc.addElement("ufinterface");
        ufInterface.addAttribute("roottag", "saleorder");
        ufInterface.addAttribute("receiver", "u8");
        ufInterface.addAttribute("sender", sender);
        ufInterface.addAttribute("proc", "add");
//		if (operType.equals("sale")){
        ufInterface.addAttribute("display", "销售订单");
//		}else {
//			ufInterface.addAttribute("display", "进货订单");
//		}
        ufInterface.addAttribute("exportneedexch","N");
        ufInterface.addAttribute("codeexchanged","N");
        Element saleOrder= ufInterface.addElement("saleorder");
        Element header= saleOrder.addElement("header");
        header.addElement("id");
        header.addElement("outid");
        if (orderBizType == OrderBizType.trade){
            header.addElement("typecode").addText("16");
        }else {
            header.addElement("typecode").addText("17");
        }
        header.addElement("date").addText(createTime);
        header.addElement("code");
        //店铺编号
        header.addElement("custcode").addText(vo.getShopCode());
        //部门编号
        header.addElement("deptcode").addText(vo.getDepartmentCode());
        header.addElement("currency").addText("人民币");
        header.addElement("currencyrate").addText("1");
        header.addElement("taxrate").addText("0");
        header.addElement("sendaddress").addText(address);
        header.addElement("memo").addText(remarks+" "+invoice);
        header.addElement("maker").addText("demo");
//		if (operType.equals("sale")){
        header.addElement("businesstype").addText("普通销售");
//		}else {
//			header.addElement("businesstype").addText("普通进货");
//		}
        header.addElement("disflag").addText("01");
        header.addElement("cusname");
        header.addElement("personcode");
        header.addElement("sendcode");
        header.addElement("ccusperson");
        header.addElement("ccuspersoncode");
        header.addElement("paycondition_code");
        header.addElement("earnest");
        header.addElement("define1");
        header.addElement("define2");
        header.addElement("define3");
        header.addElement("define4");
        header.addElement("define5");
        header.addElement("define6");
        header.addElement("define7");
        header.addElement("define8");
        header.addElement("define9");
        header.addElement("define10");
        header.addElement("define11").addText(financeAcountId);
        header.addElement("define12");
        header.addElement("define13");
        header.addElement("define14");
        header.addElement("define15");
        header.addElement("define16");
        header.addElement("caddcode");
        header.addElement("cgatheringplan");
        header.addElement("dpredatebt");
        header.addElement("dpremodatebt");
        header.addElement("bmustbook");
        header.addElement("fbookratio");
        header.addElement("fbooksum");
        header.addElement("fbooknatsum");
        header.addElement("retaildates");

        BigDecimal divideFlag=new BigDecimal("1");
        Element body  = saleOrder.addElement("body");
        for(int i=0;i<indentList.size();i++){
            U8OrderListVo indentVoD=new U8OrderListVo();
            BigDecimal singlePrice =new BigDecimal("0");
            BigDecimal numFlag =new BigDecimal("0");
            BigDecimal totalPrice =new BigDecimal("0");
            BigDecimal totalPriceFax =new BigDecimal("0");
            BigDecimal priceFax =new BigDecimal("0");
            BigDecimal totalFaxPrice =new BigDecimal("0");
            int num =1;
            indentVoD=indentList.get(i);
            //购买商品数量
            num=indentVoD.getNumber();
            //供货价单价
            singlePrice=new BigDecimal(indentVoD.getSupplyPrice());
            numFlag=new BigDecimal(num);
            //总价
            if (orderBizType == OrderBizType.purchase && indentVoD.getIsLoose().equals("1")){
                totalPrice = new BigDecimal(indentVoD.getFinalAmount());
            }else {
                totalPrice=singlePrice.multiply(numFlag);
            }
            totalPrice=singlePrice.multiply(numFlag);
            //无税金额=价税合计/(1+0)，税额=价税合计 - 无税金额，均保留两位小数点。
            //总价不含税
            totalPriceFax=totalPrice.divide(divideFlag,2,BigDecimal.ROUND_HALF_UP);
            //除税单价
            priceFax=totalPriceFax.divide(new BigDecimal(num),2,BigDecimal.ROUND_HALF_UP);
            //总税额
            totalFaxPrice=totalPrice.subtract(totalPriceFax);
            //商品编号
            String goodNo = "";
//			if (operType.equals("sale")){
            goodNo=indentVoD.getCargoNo();
//			}else {
//				goodNo=indentVoD.getTradeGoodSku().getCode();
//			}
            //  String goodNo=indentVoD.getCargoNo();
            Element entry = body.addElement("entry");
            entry.addElement("id");
            entry.addElement("body_outid");
            //货品编号
            entry.addElement("inventorycode").addText(goodNo);
            //数量
            entry.addElement("quantity").addText(num+"");
            entry.addElement("num").addText(num+"");
            //报价 含税
            entry.addElement("quotedprice").addText(singlePrice+"");
            entry.addElement("taxunitprice").addText(singlePrice+"");
            //总价不含税
            entry.addElement("money").addText(totalPriceFax+"");
            //税额
            entry.addElement("tax").addText("0");
            //总价含税
            entry.addElement("sum").addText(totalPrice+"");
            //折扣
            entry.addElement("discount").addText("0");
            //单价不含税
            entry.addElement("natunitprice").addText(priceFax+"");
            entry.addElement("unitprice").addText(priceFax+"");

            entry.addElement("natmoney").addText(totalPriceFax+"");
            entry.addElement("natsum").addText(totalPrice+"");
            //税额
            entry.addElement("nattax").addText("0");
            //折扣率
            entry.addElement("discountrate").addText("100");
            entry.addElement("discountrate2").addText("100");
            //税点
            entry.addElement("taxrate").addText("0");
            entry.addElement("natdiscount").addText("0");
            entry.addElement("itemcode").addText("101");
            entry.addElement("item_class").addText("00");
//			if (operType.equals("sale")){
            entry.addElement("itemname").addText("正常销售");
//			}else {
//				entry.addElement("itemname").addText("正常进货");
//			}
            //预发货日期 sysdate+1天
            entry.addElement("dpredate").addText(nextTime);
            entry.addElement("dpremodate").addText(nextTime);
            entry.addElement("bsaleprice").addText("1");
            entry.addElement("bgift").addText("0");
            //计量单位
            entry.addElement("unitcode").addText("6004");
            entry.addElement("fcusminprice");
            entry.addElement("demandtype");
            entry.addElement("preparedate");

            entry.addElement("assistantunit");

            //备注
            if (indentVoD.getRemark()!=null){
                entry.addElement("memo").addText(indentVoD.getRemark());
            }else {
                entry.addElement("memo");
            }
            entry.addElement("mid");
            entry.addElement("define22");
            entry.addElement("define23");
            entry.addElement("define24");
            entry.addElement("define25");
            entry.addElement("define26");
            entry.addElement("define27");
            entry.addElement("itemclass_name");
            entry.addElement("free1");
            entry.addElement("free2");
            entry.addElement("free3");
            entry.addElement("free4");
            entry.addElement("free5");
            entry.addElement("free6");
            entry.addElement("free7");
            entry.addElement("free8");
            entry.addElement("free9");
            entry.addElement("free10");
            entry.addElement("dreleasedate");
            entry.addElement("demandcode");
            entry.addElement("demandmemo");
            entry.addElement("cdetailsdemandcode");
            entry.addElement("cdetailsdemandmemo");
            entry.addElement("retailxsquantity");
            entry.addElement("retailxsmoney");
            entry.addElement("retailkcquantity");
            entry.addElement("retailyhmoney");
            entry.addElement("cparentcode");
            entry.addElement("cchildcode");
            entry.addElement("icalctype");
            entry.addElement("fchildqty");
            entry.addElement("cbdefine1");
            entry.addElement("cbdefine2");
            entry.addElement("cbdefine3");
            entry.addElement("cbdefine4");
            entry.addElement("cbdefine5");
            entry.addElement("cbdefine6");
        }
        return sendU8(doc);
    }


    public static String U8EaiPurchaseSalesOrder(U8OrderVo vo, String financeAcountId, String operType) throws Exception{
//		String operType="sale";

        String getProvince = StringUtils.isNotBlank(vo.getProvince()) ? vo.getProvince() : "";
        String getCity = StringUtils.isNotBlank(vo.getCity()) ? vo.getCity() : "";
        String getTown = StringUtils.isNotBlank(vo.getTown()) ? vo.getTown() : "";
        String getAddress = StringUtils.isNotBlank(vo.getAddress()) ? vo.getAddress() : "";

        String address=getProvince + getCity + getTown + getAddress +" 收货人:"+vo.getReceiver()+" 联系电话:"+vo.getReceiverPhone();
        String remarks="订单号："+vo.getName();
        if(vo.getBuyerRemark()!=null){
            remarks +=" 买家留言："+vo.getBuyerRemark();
        }
        List<U8OrderListVo> indentList= vo.getIndentList();
        //发票抬头
        String invoice="";
        if(vo.getNeedInvoice()){
            invoice="发票类型："+vo.getInvoiceName();
            if(vo.getInvoiceContent()!=null){
                invoice +=" 发票抬头："+vo.getInvoiceContent();
            }
        }
        Date createDate = vo.getPayTime(); //支付时间为准
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = sdf.format(createDate);
        Date nexDate= getNextDay(new Date()) ;
        String nextTime = sdf.format(nexDate);
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("utf-8");
        Element ufInterface = doc.addElement("ufinterface");
        ufInterface.addAttribute("roottag", "saleorder");
        ufInterface.addAttribute("receiver", "u8");
        ufInterface.addAttribute("sender", sender);
        ufInterface.addAttribute("proc", "add");
//		if (operType.equals("sale")){
        ufInterface.addAttribute("display", "销售订单");
//		}else {
//			ufInterface.addAttribute("display", "进货订单");
//		}
        ufInterface.addAttribute("exportneedexch","N");
        ufInterface.addAttribute("codeexchanged","N");
        Element saleOrder= ufInterface.addElement("saleorder");
        Element header= saleOrder.addElement("header");
        header.addElement("id");
        header.addElement("outid");
        if ("trade".equals(operType)){
            header.addElement("typecode").addText("16");
        }else {
            header.addElement("typecode").addText("17");
        }
        header.addElement("date").addText(createTime);
        header.addElement("code");
        //店铺编号
        header.addElement("custcode").addText(vo.getShopCode());
        //部门编号
        header.addElement("deptcode").addText(vo.getDepartmentCode());
        header.addElement("currency").addText("人民币");
        header.addElement("currencyrate").addText("1");
        header.addElement("taxrate").addText("0");
        header.addElement("sendaddress").addText(address);
        header.addElement("memo").addText(remarks+" "+invoice);
        header.addElement("maker").addText("demo");
//		if (operType.equals("sale")){
        header.addElement("businesstype").addText("普通销售");
//		}else {
//			header.addElement("businesstype").addText("普通进货");
//		}
        header.addElement("disflag").addText("01");
        header.addElement("cusname");
        header.addElement("personcode");
        header.addElement("sendcode");
        header.addElement("ccusperson");
        header.addElement("ccuspersoncode");
        header.addElement("paycondition_code");
        header.addElement("earnest");
        header.addElement("define1");
        header.addElement("define2");
        header.addElement("define3");
        header.addElement("define4");
        header.addElement("define5");
        header.addElement("define6");
        header.addElement("define7");
        header.addElement("define8");
        header.addElement("define9");
        header.addElement("define10");
        header.addElement("define11").addText(financeAcountId);
        header.addElement("define12");
        header.addElement("define13");
        header.addElement("define14");
        header.addElement("define15");
        header.addElement("define16");
        header.addElement("caddcode");
        header.addElement("cgatheringplan");
        header.addElement("dpredatebt");
        header.addElement("dpremodatebt");
        header.addElement("bmustbook");
        header.addElement("fbookratio");
        header.addElement("fbooksum");
        header.addElement("fbooknatsum");
        header.addElement("retaildates");

        BigDecimal divideFlag = new BigDecimal("1");
        Element body  = saleOrder.addElement("body");
        for(int i=0;i<indentList.size();i++){
            U8OrderListVo indentVoD=new U8OrderListVo();
            BigDecimal singlePrice =new BigDecimal("0");
            BigDecimal numFlag =new BigDecimal("0");
            BigDecimal totalPrice =new BigDecimal("0");
            BigDecimal totalPriceFax =new BigDecimal("0");
            BigDecimal priceFax =new BigDecimal("0");
            BigDecimal totalFaxPrice =new BigDecimal("0");
            int num =1;
            float sampleNum = 1;
            indentVoD=indentList.get(i);
            Element entry = body.addElement("entry");
            if (!"1".equals(indentVoD.getIsSample())) {

                num=indentVoD.getNumber();
                //供货价单价
                singlePrice=new BigDecimal(indentVoD.getSupplyPrice());
                numFlag=new BigDecimal(num);
                //总价
                if (operType.equals("purchase") && indentVoD.getIsLoose().equals("1")){
                    totalPrice = new BigDecimal(indentVoD.getFinalAmount());
                }else {
                    totalPrice=singlePrice.multiply(numFlag);
                }
                totalPrice = singlePrice.multiply(numFlag);
                //无税金额=价税合计/(1+税率)，税额=价税合计 - 无税金额，均保留两位小数点。
                //总价不含税
                totalPriceFax = totalPrice.divide(divideFlag,2,BigDecimal.ROUND_HALF_UP);
                //除税单价
                priceFax = totalPriceFax.divide(new BigDecimal(num),2,BigDecimal.ROUND_HALF_UP);
                //总税额
//				totalFaxPrice=totalPrice.subtract(totalPriceFax);
                //商品编号
                String goodNo = "";
//			if (operType.equals("sale")){
                goodNo=indentVoD.getCargoNo();
//			goodNo = indentVoD.getTradeGoodSkuId();
//			}else {
//				goodNo=indentVoD.getTradeGoodSku().getCode();
//			}
                //  String goodNo=indentVoD.getCargoNo();

                entry.addElement("id");
                entry.addElement("body_outid");
                //货品编号
                entry.addElement("inventorycode").addText(goodNo);
                //数量
                entry.addElement("quantity").addText(num+"");
                entry.addElement("num").addText(num+"");

            } else {

                if (StringUtils.isNotBlank(indentVoD.getSampleNum())) {
                    sampleNum = Float.parseFloat(indentVoD.getSampleNum());
                }
                num = indentVoD.getNumber(); //购买数量
                singlePrice = new BigDecimal(indentVoD.getSupplyPrice());	//供货价单价
                numFlag = new BigDecimal(sampleNum);	//样品购买斤数

                BigDecimal sampleTotalPrice = new BigDecimal("0"); //样品的总价价格
                //总价
                if (operType.equals("purchase") && indentVoD.getIsLoose().equals("1")){
                    totalPrice = new BigDecimal(indentVoD.getFinalAmount());
                    sampleTotalPrice = new BigDecimal(indentVoD.getFinalAmount());
                }else {
                    totalPrice = singlePrice.multiply(numFlag);
                }

                totalPrice = singlePrice.multiply(new BigDecimal(num)); //供货价*购买数量

                sampleTotalPrice = singlePrice.multiply(numFlag);  //供货价*样品斤数

                //无税金额=价税合计/(1+税率)，税额=价税合计 - 无税金额，均保留两位小数点。
                //总价不含税
                totalPriceFax = sampleTotalPrice.divide(divideFlag, 2,  BigDecimal.ROUND_HALF_UP);
                //除税单价
                priceFax = sampleTotalPrice.divide(new BigDecimal(sampleNum), 2, BigDecimal.ROUND_HALF_UP);
                //总税额
//				totalFaxPrice=totalPrice.subtract(totalPriceFax);

                String goodNo = indentVoD.getCargoNo();	//商品编号
                entry.addElement("id");
                entry.addElement("body_outid");
                entry.addElement("inventorycode").addText(goodNo); //货品编号
                entry.addElement("quantity").addText(sampleNum+""); //数量
                entry.addElement("num").addText(sampleNum+"");
            }


            //购买商品数量


            entry.addElement("quotedprice").addText(singlePrice+"");	//报价
            entry.addElement("taxunitprice").addText(singlePrice+"");	//含税单价

            entry.addElement("money").addText(totalPriceFax+""); //总价不含税
            //税额
            entry.addElement("tax").addText("0");
            //总价含税
            entry.addElement("sum").addText(totalPrice+"");
            //折扣
            entry.addElement("discount").addText("0");
            //单价不含税
            entry.addElement("natunitprice").addText(priceFax+"");
            entry.addElement("unitprice").addText(priceFax+"");

            entry.addElement("natmoney").addText(totalPriceFax+"");
            entry.addElement("natsum").addText(totalPrice+"");
            //税额
            entry.addElement("nattax").addText("0");
            //折扣率
            entry.addElement("discountrate").addText("100");
            entry.addElement("discountrate2").addText("100");
            //税点
            entry.addElement("taxrate").addText("0");
            entry.addElement("natdiscount").addText("0");
            entry.addElement("itemcode").addText("101");
            entry.addElement("item_class").addText("00");
//			if (operType.equals("sale")){
            entry.addElement("itemname").addText("正常销售");
//			}else {
//				entry.addElement("itemname").addText("正常进货");
//			}
            //预发货日期 sysdate+1天
            entry.addElement("dpredate").addText(nextTime);
            entry.addElement("dpremodate").addText(nextTime);
            entry.addElement("bsaleprice").addText("1");
            entry.addElement("bgift").addText("0");
            //计量单位
            entry.addElement("unitcode").addText("6004");
            entry.addElement("fcusminprice");
            entry.addElement("demandtype");
            entry.addElement("preparedate");

            entry.addElement("assistantunit");

            //备注
            if (indentVoD.getRemark()!=null){
                entry.addElement("memo").addText(indentVoD.getRemark());
            }else {
                entry.addElement("memo");
            }
            entry.addElement("mid");
            entry.addElement("define22");
            entry.addElement("define23");
            entry.addElement("define24");
            entry.addElement("define25");
            entry.addElement("define26");
            entry.addElement("define27");
            entry.addElement("itemclass_name");
            entry.addElement("free1");
            entry.addElement("free2");
            entry.addElement("free3");
            entry.addElement("free4");
            entry.addElement("free5");
            entry.addElement("free6");
            entry.addElement("free7");
            entry.addElement("free8");
            entry.addElement("free9");
            entry.addElement("free10");
            entry.addElement("dreleasedate");
            entry.addElement("demandcode");
            entry.addElement("demandmemo");
            entry.addElement("cdetailsdemandcode");
            entry.addElement("cdetailsdemandmemo");
            entry.addElement("retailxsquantity");
            entry.addElement("retailxsmoney");
            entry.addElement("retailkcquantity");
            entry.addElement("retailyhmoney");
            entry.addElement("cparentcode");
            entry.addElement("cchildcode");
            entry.addElement("icalctype");
            entry.addElement("fchildqty");
            entry.addElement("cbdefine1");
            entry.addElement("cbdefine2");
            entry.addElement("cbdefine3");
            entry.addElement("cbdefine4");
            entry.addElement("cbdefine5");
            entry.addElement("cbdefine6");
        }
        return sendU8(doc);
    }

    private static String syncU8(Document doc) throws Exception{
        URL url = new URL(u8url);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setConnectTimeout(3000000);
        con.setReadTimeout(3000000);
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setAllowUserInteraction(false);
        con.setUseCaches(false);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-type","application/x-www-form-urlencoded");
        //发送Request消息
        OutputStream out = con.getOutputStream();
        DataOutputStream dos = new DataOutputStream(out);
        String str=XMLtoStr(doc);
        logger.error("U8EaiUtil 传值给U8 --> {}",str);
        dos.write(str.getBytes("utf-8"));

        //获取Response消息
        InputStream in = con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in,"utf-8"));
        StringBuilder sb = new StringBuilder();
        String s = null;
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        String responseXml = sb.toString();
        logger.error("U8EaiUtil U8返回值 --> {}",responseXml);
        String res = XMLUtil.doXMLParseForU8(responseXml);
        return res;
    }

    public static String U8EaiGetStock(String cInvCode)throws Exception {
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("utf-8");
        Element ufInterface = doc.addElement("ufinterface");
        ufInterface.addAttribute("roottag", "SQLEXE");
        ufInterface.addAttribute("receiver", "u8");
        ufInterface.addAttribute("sender", sender);
        ufInterface.addAttribute("proc", "depart");
        ufInterface.addAttribute("exportneedexch","N");
        ufInterface.addAttribute("codeexchanged","N");
        Element accept = ufInterface.addElement("sql");
        String sqlString = "SELECT * FROM view_CurrentStock_sc ";
        if (cInvCode != null && !"".equals(cInvCode)){
            sqlString=sqlString+" where cInvCode in ("+cInvCode+")";
        }
        accept.addAttribute("value",sqlString);
        return syncU8(doc);
    }

    public static String U8EaiGetBom(String cinvcodeParent)throws Exception {
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("utf-8");
        Element ufInterface = doc.addElement("ufinterface");
        ufInterface.addAttribute("roottag", "SQLEXE");
        ufInterface.addAttribute("receiver", "u8");
        ufInterface.addAttribute("sender", sender);
        ufInterface.addAttribute("proc", "depart");
        ufInterface.addAttribute("codeexchanged", "n");
        Element accept = ufInterface.addElement("sql");
        String sqlString = "SELECT bomId as BomId,cinvcode_parent as cinvcodeParent, cinvcode_child as cinvcodeChild, BaseQtyN as baseQtyN  FROM view_bom_gx ";

        if (cinvcodeParent != null && !"".equals(cinvcodeParent)){
            sqlString=sqlString+" where cinvcode_parent = '"+cinvcodeParent+"' ";
        }

        accept.addAttribute("value",sqlString);
        return syncU8(doc);
    }
}
