package pub.makers.shop.baseOrder.utils;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import pub.makers.shop.baseOrder.pojo.BaseOrderItem;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class BaseOrderHelps {

	public static List<? extends BaseOrderItem> groupItems(List<? extends BaseOrderItem> itemList){
		
		LinkedListMultimap<String, BaseOrderItem> itemMap = LinkedListMultimap.create();
		for (BaseOrderItem item : itemList){
			itemMap.put(item.getGoodSkuId(), item);
		}
		
		List<BaseOrderItem> resultList = Lists.newArrayList();
		for (String goodSkuId : itemMap.keySet()){
			Collection<BaseOrderItem> itemC = itemMap.get(goodSkuId);
			int totalNum = 0;
			List<String> idList = Lists.newArrayList();
			BigDecimal sumAmount = BigDecimal.ZERO;
			BigDecimal totalAmount = BigDecimal.ZERO;
			BigDecimal totalDiscount = BigDecimal.ZERO;
			for (BaseOrderItem cItem : itemC){
				totalNum += cItem.getBuyNum();
				if (StringUtils.isNotEmpty(cItem.getItemId())) {
					idList.add(cItem.getItemId());
				}
				sumAmount = sumAmount.add(cItem.getSumAmount() == null ? BigDecimal.ZERO : cItem.getSumAmount());
				totalAmount = totalAmount.add(cItem.getWaitPayAmont() == null ? BigDecimal.ZERO : cItem.getWaitPayAmont());
				totalDiscount = totalDiscount.add(cItem.getDiscountAmount() == null ? BigDecimal.ZERO : cItem.getDiscountAmount());
			}
			BaseOrderItem resItem = null;
			try {
				resItem = (BaseOrderItem) itemC.iterator().next().clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resItem.setBuyNum(totalNum);
			resItem.setId(StringUtils.join(idList, ","));
			resItem.setSumAmount(sumAmount);
			resItem.setWaitPayAmont(totalAmount);
			resItem.setDiscountAmount(totalDiscount);
			resultList.add(resItem);
		}
		
		return resultList;
	}
	
	public static List<? extends BaseOrderItem> splitItems(List<? extends BaseOrderItem> itemList){
		
		List resultList = Lists.newArrayList();
		for (BaseOrderItem item : itemList){
			for (int i = 0; i < item.getBuyNum(); i++){
				try {
					BaseOrderItem newItem = (BaseOrderItem) item.clone();
					newItem.setBuyNum(1);
					resultList.add(newItem);
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return resultList;
	}
}
