package pub.makers.shop.logistics.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.lantu.base.common.entity.BoolType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.constants.BizErrorCode;
import pub.makers.shop.base.entity.Region;
import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.service.RegionService;
import pub.makers.shop.base.service.SysDictService;
import pub.makers.shop.base.util.SerializeUtils;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderListGoodType;
import pub.makers.shop.baseOrder.pojo.TradeContext;
import pub.makers.shop.logistics.entity.FreightPinkage;
import pub.makers.shop.logistics.entity.FreightShipping;
import pub.makers.shop.logistics.entity.FreightTpl;
import pub.makers.shop.logistics.entity.FreightTplGoodRel;
import pub.makers.shop.logistics.vo.*;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderListVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.tradeOrder.vo.IndentListVo;
import pub.makers.shop.tradeOrder.vo.IndentVo;

import java.math.BigDecimal;
import java.util.*;

@Service(version="1.0.0")
public class FreightTplBizServiceImpl implements FreightTplBizService{


	
	@Autowired
	private FreightTplGoodRelService goodRelService;
	@Autowired
	private FreightTplService freightTplService;
	@Autowired
	private FreightShippingService shippingService;
	@Autowired
	private FreightTplRelBizService tplRelService;
	@Autowired
	private FreightPinkageService fpService;
	@Autowired
	private FreightServiceManager fpManager;
	@Autowired
	private RegionService regionService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private SysDictService sysDictService;

	
	/**
	 * 计算运费模板结果
	 * @param relList
	 * @param context
	 * @return
	 */
	private FreightResultVo showOptions(List<FreightTplRelVo> relList, IndentVo indent1, TradeContext context, String relType) {
		
		
		List<IndentListVo> excludeList = Lists.newArrayList();
		for (IndentListVo ilvo : indent1.getIndentList()){
			// 排除订单中的配件部分
			if ("T".equals(ilvo.getGiftFlag())){
				excludeList.add(ilvo);
			}
			// 排除订单中的样品部分
			else if ("1".equals(ilvo.getIsSample())){
				excludeList.add(ilvo);
			}
			// 排除订单中的赠品部分
			else if (OrderListGoodType.zengpin.name().equals(ilvo.getGoodType())) {
				excludeList.add(ilvo);
			}
		}
		
		List<IndentListVo> oriindentList = Lists.newArrayList(indent1.getIndentList());
		oriindentList.removeAll(excludeList);

		if (oriindentList.isEmpty()) {
			FreightResultVo freightResultVo = new FreightResultVo();
			freightResultVo.setServicerList(new ArrayList<>());
			return freightResultVo;
		}
		
		// 订单中的商品合并
		oriindentList = groupItems(oriindentList);
		
		//  处理context中的regionId问题
		Region r = regionService.getByCityAndRegionName(context.getCity(), context.getArea());
		ValidateUtils.notNull(r, BizErrorCode.COM_INVILID_AREA);
		context.setRegionId(r.getId() + "");
		
		// 先应用类目运费模板
		List<IndentListVo> indentList = Lists.newArrayList(oriindentList);
		
		// 记录每个商品有那些可选运费方式
		Multimap<String, FreightVo> fvoMap = ArrayListMultimap.create();
		
		for (FreightTplRelVo rel : relList){
			
			FreightTpl tpl = getDetailById(rel.getTplId());
			List<FreightVo> fvoList = applyFreightTpl(rel.getIndentList(), tpl, context);
			for (FreightVo fvo : fvoList){
				
				for (IndentListVo relVo : fvo.getIndentList()){
					fvoMap.put(relVo.getTradeGoodSkuId(), fvo);
				}
				// 能够应用到全局运费模板的商品不在应用商品特定运费模板
				indentList.removeAll(fvo.getIndentList());
			}
			
		}
		
		
		for (IndentListVo vo : indentList){
			
			FreightTplGoodRel gr = goodRelService.getRelBySkuIdAndType(vo.getTradeGoodSkuId(), relType);
			// 对样品的特殊处理
			if (gr == null && "1".equals(vo.getIsSample())){
				gr = new FreightTplGoodRel();
				// 设置样品固定规则模板
				gr.setTplId("SAMPLE_TPL");
			}
			// 对赠品特殊处理
			else if (gr == null && BoolType.T.name().equals(vo.getGiftFlag())){
				gr = new FreightTplGoodRel();
				// 设置赠品品固定规则模板
				gr.setTplId("GIFT_TPL");
			}
			
			
			ValidateUtils.notNull(gr, BizErrorCode.LOG_NOT_CONFIG);
			// TODO 商品没有配置运费模板改如何处理
			FreightTpl tpl = getDetailById(gr.getTplId());
			ValidateUtils.notNull(tpl, BizErrorCode.LOG_NOT_VALID);
			
			List<FreightVo> fvoList = applyFreightTpl(Lists.newArrayList(vo), tpl, context);
			
			// 将运费按服务商进行拆分
			for (FreightVo fvo : fvoList){
				for (IndentListVo relVo : fvo.getIndentList()){
					fvoMap.put(relVo.getTradeGoodSkuId(), fvo);
				}
			}
			
		}
		
		// 可选物流方式中间数据
		Multimap<String, FreightServicerVo> servicerMap = ArrayListMultimap.create(); 
		Multimap<String, FreightServicerVo> serviceGoodMap = ArrayListMultimap.create();
		
		// 封装返回结果
		FreightResultVo result = new FreightResultVo();
		
		// 按照商品ID来组织数据结构
		for (String goodSkuId : fvoMap.keySet()){
			Map<String, FreightVo> options = Maps.newHashMap();
			for (FreightVo fvo : fvoMap.get(goodSkuId)){
				// 删除不必要的返回值
				fvo.setIndentList(null);
				options.put(fvo.getServicerId(), fvo);
				if (!fvo.isFreeFalg()){
					servicerMap.put(goodSkuId, new FreightServicerVo(fvo.getServicerId(), fvo.getServicerName()));
				}
			}
			result.addGoodFreight(goodSkuId, options);
		}
		
		// 查询可用物流供应商列表
		for (String goodSkuId : servicerMap.keySet()){
			for (FreightServicerVo svo : servicerMap.get(goodSkuId)){
				serviceGoodMap.put(svo.getServicerId(), svo);
			}
		}
		List<FreightServicerVo> availableServicers = Lists.newArrayList();
		
		// 计算需要运费的商品的数量
		int needFreightCount = 0;
		for (String skuId : result.getGoodFreights().keySet()){
			Map<String, FreightVo> goodFreight = result.getGoodFreights().get(skuId);
			FreightVo fvo = goodFreight.get(goodFreight.keySet().iterator().next());
			if (fvo.isFreeFalg() == false){
				needFreightCount++;
			}
		}
		
		// 没有计费运费模板，总邮费为0
		if (needFreightCount == 0){
			String byServiceId = "baoyou";
			String byServiceName = "快递";
			
			Map<String, Map<String, FreightVo>> goodFreights = result.getGoodFreights();
			if (goodFreights.size() > 0){
				Map<String, FreightVo> fMap = goodFreights.get(goodFreights.keySet().iterator().next());
				if (fMap.size() > 0){
					FreightVo fvo = fMap.get(fMap.keySet().iterator().next());
					byServiceId = fvo.getServicerId();
					byServiceName = fvo.getServicerName();
				}
			}
			
			FreightServicerVo svo = new FreightServicerVo();
			svo.setServicerId(byServiceId);
			svo.setServicerName(byServiceName);
			svo.setFirstFreight(BigDecimal.ZERO);
			svo.setIncrFreight(BigDecimal.ZERO);
			svo.setTotalFreight(BigDecimal.ZERO);
			availableServicers.add(svo);
		}
		// 有计费运费模板，计算总邮费
		else {
			for (String servicerId : serviceGoodMap.keySet()){
				Collection<FreightServicerVo> svoC = serviceGoodMap.get(servicerId);
				// 物流匹配到商品的数量应该等于需要使用收费方案商品的数量
				// TODO 需要考虑到单个商品匹配到免费的场景
				if (svoC.size() != needFreightCount || svoC.size() == 0){
					continue;
				}
				
				FreightServicerVo svo = svoC.iterator().next();
				BigDecimal firstFreightSum = BigDecimal.ZERO;
				BigDecimal avgFirstFreight = BigDecimal.ZERO;
				BigDecimal incrFreight = BigDecimal.ZERO;
				int freightPayCount = 0;
				
				// 计算该物流方式的总运费
				Map<String, Map<String, FreightVo>> goodFreights = result.getGoodFreights();
				for (String skuId : goodFreights.keySet()){
					Map<String, FreightVo> fMap = goodFreights.get(skuId);
					FreightVo fvo = fMap.get(svo.getServicerId());
					if (fvo == null || fvo.isFreeFalg() == true){
						continue;
					}
					
					freightPayCount++;
					// 首件费用累加
					firstFreightSum = firstFreightSum.add(fvo.getFirstFreight());
					// 增量累加
					incrFreight = incrFreight.add(fvo.getIncrFreight());
				}
				// 如果商品数量大于1，那么首件运费=首件运费之和-首件运费平均值
				if (goodFreights.size() > 1){
					avgFirstFreight = firstFreightSum.divide(new BigDecimal(freightPayCount), 0, BigDecimal.ROUND_DOWN);
				}
				
				// 首件金额向下取整
				BigDecimal firstFreight = firstFreightSum.subtract(avgFirstFreight).setScale(0, BigDecimal.ROUND_DOWN);
				
				svo.setFirstFreight(firstFreight);
				svo.setIncrFreight(incrFreight);
				svo.setTotalFreight(firstFreight.add(incrFreight));
				
				availableServicers.add(svo);
			}
		}
		
		List<SysDict> dictList = sysDictService.list(Conds.get().eq("parentId", "5").order("order_num desc"));
		final Map<String, Integer> sortMap = Maps.newHashMap();
		for (int i = 0; i < dictList.size(); i++){
			sortMap.put(dictList.get(i).getCode(), i);
		}
		
		availableServicers.sort(new Comparator<FreightServicerVo>() {

			@Override
			public int compare(FreightServicerVo o1, FreightServicerVo o2) {
				
				Integer o1Sort = sortMap.get(o1.getServicerId());
				Integer o2Sort = sortMap.get(o2.getServicerId());
				if (o1Sort == null || o2Sort == null){
					return -100;
				}
				
				return o1Sort - o2Sort;
			}
		});
		// 查询运送方式字典
		
		result.setServicerList(availableServicers);
		
		return result;
	}

	public FreightTpl getDetailById(String tplId) {
		
		FreightTpl tpl = freightTplService.getById(tplId);
		List<FreightShipping> shipList = shippingService.listDetailByTplId(tpl.getTplId());
		tpl.setShippingList(shipList);
		List<FreightPinkage> fpList = fpService.list(Conds.get().eq("tplId", tplId));
		tpl.setPinkageList(fpList);
		
		return tpl;
	}
	
	
	
	private List<FreightVo> applyFreightTpl(List<IndentListVo> ivoList, FreightTpl tpl, TradeContext context){
		
		List<FreightVo> fvoList = Lists.newArrayList();
		
		// 免费规则大于收费规则
		if(BoolType.T.name().equals(tpl.getLimitFreeFlag()) && tpl.getPinkageList().size() > 0){
			
			// 规则是按照金额
			for (FreightPinkage fp : tpl.getPinkageList()){
				FreightPinkageRuleService pinkageRuleService = fpManager.getPinkageRuleService(fp.getCondType());
				ValidateUtils.notNull(pinkageRuleService, String.format("包邮规则{%s}配置有误，未知的计价方式{%s}，请联系管理员", tpl.getName(), fp.getCondType()));
				FreightVo fv = pinkageRuleService.calcFreight(fp, ivoList, context);
				if (fv != null){
					fv.setTplDesc(tpl.getName());
					fvoList.add(fv);
				}
			}
			
		}
		
		// 没有匹配到免费规则时，则使用运费规则
		if (fvoList.size() == 0){
			// 根据计价方式获取运费服务
			FreightPricingService fpService = fpManager.getPricingService(tpl.getPriceType());
			// 根据运费模板和商品的商品信息计算运费，根据商品的运费模板配置查询出可选的配送方式以及金额
			for (IndentListVo ivo : ivoList){
				
				List<FreightVo> calcResult = fpService.calcFreight(tpl.getShippingList(), ivo, context);
				for (FreightVo fv : calcResult){
					fv.setTplDesc(tpl.getName());
				}
				fvoList.addAll(calcResult);
			}
		}
		
		return fvoList;
		
	}

	@Override
	public FreightResultVo queryTradeOrderOptions(IndentVo indent1, TradeContext context) {
		
		IndentVo newIndent = SerializeUtils.cloneObject(indent1);
		// 处理商品总价
		for (IndentListVo ilvo : newIndent.getIndentList()){
			
			BigDecimal totalAmount = new BigDecimal(ilvo.getTradeGoodAmount()).multiply(new BigDecimal(ilvo.getNumber()));
			ilvo.setTradeGoodAmount(totalAmount.toString());
			ilvo.setTotalAmount(totalAmount);
			ilvo.setFinalAmount(totalAmount.toString());
		}
		
		List<FreightTplRelVo> relList = tplRelService.queryTradeGoodRel(newIndent);
		
		return showOptions(relList, newIndent, context, OrderBizType.trade.name());
	}

	@Override
	public BigDecimal calcTradeOrderFreight(IndentVo indent, TradeContext context, String servicerId) {
		
		FreightResultVo freightResultVo = queryTradeOrderOptions(indent, context);
        List<FreightServicerVo> servicerList = freightResultVo.getServicerList();
        BigDecimal buyerCarriage = new BigDecimal(0);
        for (FreightServicerVo freightServicerVo : servicerList) {
			if (StringUtils.isBlank(indent.getExpressId()) || indent.getExpressId().equals(freightServicerVo.getServicerId())) {
                buyerCarriage = buyerCarriage.add(freightServicerVo.getTotalFreight());
                break;
            }
        }
        
        return buyerCarriage;
	}

	@Override
	public FreightResultVo queryPurchaseOrderOptions(PurchaseOrderVo order, TradeContext context) {
		
		List<FreightTplRelVo> relList = tplRelService.queryPurchaseGoodRel(order);
		IndentVo indent = purchaseOrderToIndent(order);
		
		return showOptions(relList, indent, context, OrderBizType.purchase.name());
	}

	@Override
	public BigDecimal calcPurchaseOrderFreight(PurchaseOrderVo order, TradeContext context, String servicerId) {
		
		FreightResultVo freightResultVo = this.queryPurchaseOrderOptions(order, context);
		List<FreightServicerVo> servicerList = freightResultVo.getServicerList();
        BigDecimal buyerCarriage = new BigDecimal(0);
        for (FreightServicerVo freightServicerVo : servicerList) {
            if (StringUtils.isBlank(order.getExpressId()) || order.getExpressId().equals(freightServicerVo.getServicerId())) {
                buyerCarriage = buyerCarriage.add(freightServicerVo.getTotalFreight());
                break;
            }
        }
        
		return buyerCarriage;
	}


	private IndentVo purchaseOrderToIndent(PurchaseOrderVo order){
		
		IndentVo iv = new IndentVo();
		List<IndentListVo> indentList = Lists.newArrayList();
		
		for (PurchaseOrderListVo pvo : order.getOrderListVos()){
			
			IndentListVo ilvo = pvo.toIndentListVo();
			
			indentList.add(ilvo);
		}
		iv.setIndentList(indentList);
		
		return iv;
	}

	@Override
	public FreightTplVo queryFreightTplByGoodSku(String goodSkuId, TradeContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean hasConfigure(String skuId, String orderType, TradeContext context) {
		
		
		return false;
	}

	@Override
	public FreightTpl queryPrimaryTpl(String skuId, TradeContext context, String relType) {
		
		List<FreightTplRelVo> relList = Lists.newArrayList();
		FreightTpl result = null;
		
		// 查询关联规则
		if (OrderBizType.purchase.name().equals(relType)){
			PurchaseOrderVo order = new PurchaseOrderVo();
			
			PurchaseOrderListVo pl = new PurchaseOrderListVo();
			pl.setMaterialSkuId(skuId);
			pl.setPurGoodsSkuId(skuId);
			pl.setNumber(1);
			pl.setFinalAmount(BigDecimal.ZERO);
			List<PurchaseOrderListVo> plList = Lists.newArrayList(pl);
			order.setOrderListVos(plList);
			
			relList = tplRelService.queryPurchaseGoodRel(order);
		}
		else {
			IndentVo order = new IndentVo();
			IndentListVo ilvo = new IndentListVo();
			ilvo.setCargoSkuId(skuId);
			ilvo.setTradeGoodSkuId(skuId);
			ilvo.setNumber(1);
			ilvo.setTotalAmount(BigDecimal.ZERO);
			ilvo.setTradeGoodAmount("0");
			ilvo.setFinalAmount("0");
			
			List<IndentListVo> indentList = Lists.newArrayList(ilvo);
			order.setIndentList(indentList);
			
			relList = tplRelService.queryTradeGoodRel(order);
		}
		
		if (relList.size() != 0){
			result = getDetailById(relList.get(0).getTplId());
		}
		else {
			FreightTplGoodRel gr = goodRelService.getRelBySkuIdAndType(skuId, relType);
			if (gr != null){
				result = getDetailById(gr.getTplId());
			}
		}
		
		// 查询单品规则
		return result;
	}

	private List<IndentListVo> groupItems(List<IndentListVo> itemList){
		
		Multimap<String, IndentListVo> itemMap = ArrayListMultimap.create();
		for (IndentListVo item : itemList){
			itemMap.put(item.getGoodSkuId(), item);
		}
		
		List<IndentListVo> resultList = Lists.newArrayList();
		for (String goodSkuId : itemMap.keySet()){
			Collection<IndentListVo> itemC = itemMap.get(goodSkuId);
			int totalNum = 0;
			BigDecimal amount = BigDecimal.ZERO;
			for (IndentListVo cItem : itemC){
				totalNum += cItem.getNumber();
				amount = amount.add(cItem.getTotalAmount());
			}
			IndentListVo resItem = itemC.iterator().next();
			IndentListVo nresItem = SerializeUtils.cloneObject(resItem);
			nresItem.setNumber(totalNum);
			nresItem.setTotalAmount(amount);
			nresItem.setFinalAmount(amount.toString());
			nresItem.setTradeGoodAmount(amount.toString());
			
			resultList.add(nresItem);
		}
		
		return resultList;
	}

	@Override
	public FreightResultVo queryOptions(FreightTplQuery query) {
		
		List<FreightTplQueryItem> itemList = query.getItemList();
		// 商城
		if (OrderBizType.trade.equals(query.getOrderBizType())){
			
			IndentVo order = new IndentVo();
			List<IndentListVo> orderItemList = Lists.newArrayList();
			for (FreightTplQueryItem item : itemList){
				IndentListVo ivo = new IndentListVo();
				ivo.setGoodSkuId(item.getGoodSkuId());
				ivo.setBuyNum(item.getBuyNum());
				ivo.setIsSample(item.getIsSample());
				ivo.setGiftFlag(item.getGiftFlag());
				ivo.setTotalAmount(item.getTotalAmount());
				ivo.setTradeGoodAmount(item.getTotalAmount().setScale(2).toString());
				
				orderItemList.add(ivo);
			}
			
			order.setIndentList(orderItemList);
			
			return queryTradeOrderOptions(order, query.getTradeContext());
		}
		// 采购
		else {
			
			PurchaseOrderVo order = new PurchaseOrderVo();
			List<PurchaseOrderListVo> orderItemList = Lists.newArrayList();
			for (FreightTplQueryItem item : itemList){
				PurchaseOrderListVo ivo = new PurchaseOrderListVo();
				ivo.setPurGoodsSkuId(item.getGoodSkuId());
				ivo.setBuyNum(item.getBuyNum());
				ivo.setIsSample(item.getIsSample());
				ivo.setGiftFlag(item.getGiftFlag());
				ivo.setFinalAmount(item.getTotalAmount());
				
				orderItemList.add(ivo);
			}
			
			order.setOrderListVos(orderItemList);
			
			return queryPurchaseOrderOptions(order, query.getTradeContext());
		}
	}

	@Override
	public BigDecimal calcOrderFreight(FreightTplQuery query) {
		
		FreightResultVo freightResultVo = this.queryOptions(query);
		List<FreightServicerVo> servicerList = freightResultVo.getServicerList();
        String serviceId = StringUtils.isNotBlank(query.getServiceId()) ? query.getServiceId() : "";
        BigDecimal buyerCarriage = new BigDecimal(0);
        for (FreightServicerVo freightServicerVo : servicerList) {
            if (StringUtils.isBlank(serviceId) || serviceId.equals(freightServicerVo.getServicerId())) {
                buyerCarriage = buyerCarriage.add(freightServicerVo.getTotalFreight());
                break;
            }
        }
        
		return buyerCarriage;
	}
}
