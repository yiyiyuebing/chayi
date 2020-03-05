package pub.makers.shop.freight.controller;

import com.dev.base.json.JsonUtils;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseOrder.pojo.TradeContext;
import pub.makers.shop.freight.service.FreightTplB2bService;
import pub.makers.shop.logistics.entity.FreightTpl;
import pub.makers.shop.logistics.vo.FreightResultVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderListVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * 运费模版控制器
 * Created by dy on 2017/6/17.
 */
@Controller
@RequestMapping("/feright")
public class FreightTplController {

    @Autowired
    private FreightTplB2bService freightTplB2bService;

    /**
     * 查询运费模板的首选规则
     * @param skuId
     * @param relType
     * @param tradeContextJson
     * @return
     */
    @RequestMapping("/showPrimaryTpl")
    @ResponseBody
    public ResultData showPrimaryTpl(String skuId, String relType, String tradeContextJson){

        ValidateUtils.notNull(skuId, "skuId不能为空");
        ValidateUtils.notNull(relType, "relType不能为空");
        ValidateUtils.notNull(tradeContextJson, "交易上下文信息不能为空");

        TradeContext tradeContext = JsonUtils.toObject(tradeContextJson, TradeContext.class);

        FreightTpl tpl = freightTplB2bService.queryPrimaryTpl(skuId, tradeContext, relType);

        return ResultData.createSuccess("tpl", tpl);
    }

    /**
     * 查询物流选项
     * @return
     */
    @RequestMapping("showCarriageOptions")
    @ResponseBody
    public ResultData showCarriageOptions(HttpServletRequest request, HttpServletResponse response, String listJson, String tradeContextJson){

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=utf-8");
        ValidateUtils.notNull(listJson, "商品信息不能为空");
        TradeContext tradeContext = new TradeContext();
        if (StringUtils.isNotBlank(tradeContextJson)) {
            tradeContext = JsonUtils.toObject(tradeContextJson, TradeContext.class);
        } else {
            String ip = null;
            try {
                ip = getRemortIP(request);
                tradeContext = freightTplB2bService.getIpCity(ip);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        List<PurchaseOrderListVo> orderListVos = JsonUtils.toObject(listJson, ListUtils.getCollectionType(List.class, PurchaseOrderListVo.class));
//        ValidateUtils.notNull(tradeContext.getArea(), "当前地区信息有错");
        ValidateUtils.notNull(tradeContext.getCity(), "当前地区信息有错");
        FreightResultVo fvo = freightTplB2bService.showPurchaseOptions(orderListVos, tradeContext);
        return ResultData.createSuccess("fvo", fvo);
    }


    /**
     * 获取客户端ip
     * @param request
     * @return
     * @throws java.net.UnknownHostException
     */
    private String getRemortIP(HttpServletRequest request) throws UnknownHostException {
        String client_ip = request.getHeader("x-forwarded-for");
        if(client_ip == null || client_ip.length() == 0 || "unknown".equalsIgnoreCase(client_ip)) {
            client_ip = request.getHeader("Proxy-Client-IP");
        }
        if(client_ip == null || client_ip.length() == 0 || "unknown".equalsIgnoreCase(client_ip)) {
            client_ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(client_ip == null || client_ip.length() == 0 || "unknown".equalsIgnoreCase(client_ip)) {
            client_ip = request.getRemoteAddr();
            if(client_ip.equals("127.0.0.1") || client_ip.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                client_ip = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(client_ip != null && client_ip.length() > 15){ //"***.***.***.***".length() = 15
            if(client_ip.indexOf(",") > 0){
                client_ip = client_ip.substring(0,client_ip.indexOf(","));
            }
        }
        return client_ip;
//        String server_ip = InetAddress.getLocalHost().getHostAddress();
    }
}
