package pub.makers.shop.logistics.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.base.json.JsonUtils;

import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseOrder.pojo.TradeContext;
import pub.makers.shop.logistics.entity.FreightTpl;
import pub.makers.shop.logistics.service.FreightTplAppService;
import pub.makers.shop.logistics.vo.FreightResultVo;
import pub.makers.shop.tradeOrder.vo.IndentVo;

/**
 * 运费模板控制器
 * @author apple
 *
 */
@Controller
@RequestMapping("feright")
public class FreightTplController {

	@Autowired
	private FreightTplAppService freightAppService;
	
	/**
	 * 查询可用的选项
	 * @param indentJson 订单
	 * @param tradeContextJson 交易上下文
	 * @return
	 */
	@RequestMapping("showOptions")
	@ResponseBody
	public ResultData showOptions(HttpServletResponse response, String indentJson, String tradeContextJson){
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		
		ValidateUtils.notNull(indentJson, "订单信息不能为空");
		ValidateUtils.notNull(tradeContextJson, "交易上下文信息不能为空");
		
		IndentVo indentVo = JsonUtils.toObject(indentJson, IndentVo.class);
		TradeContext tradeContext = JsonUtils.toObject(tradeContextJson, TradeContext.class);
//		ValidateUtils.notNull(tradeContext.getArea(), "下单地区信息有错");
		ValidateUtils.notNull(tradeContext.getCity(), "下单地区信息有错");
		
		FreightResultVo result = freightAppService.showTradeOptions(indentVo, tradeContext);
		
		return ResultData.createSuccess("result", result);
	}
	
	/**
	 * 查询运费模板的首选规则
	 * @param skuId
	 * @param relType
	 * @param tradeContextJson
	 * @return
	 */
	@RequestMapping("showPrimaryTpl")
	@ResponseBody
	public ResultData showPrimaryTpl(String skuId, String relType, String tradeContextJson){
		
		ValidateUtils.notNull(skuId, "skuId不能为空");
		ValidateUtils.notNull(relType, "relType不能为空");
		ValidateUtils.notNull(tradeContextJson, "交易上下文信息不能为空");
		
		TradeContext tradeContext = JsonUtils.toObject(tradeContextJson, TradeContext.class);
		
		FreightTpl tpl = freightAppService.queryPrimaryTpl(skuId, tradeContext, relType);
		
		return ResultData.createSuccess("tpl", tpl);
	}
	
}
