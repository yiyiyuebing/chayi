package pub.makers.shop.logistics.utils;

import java.util.Set;

import com.google.common.collect.Sets;

public class RegionUtils {

	public static boolean regionMatch(String regionIds, String regionId){
		
		Set<String> regionSet = Sets.newHashSet(regionIds.split(","));
		
		String newRegionId = new String(regionId);
		
		return regionSet.contains(newRegionId);
//		while (regionId != "000000"){
//			if (regionSet.contains(newRegionId)){
//				return true;
//			}
//			
//			// 从尾巴开始查找第一个非0的位置
//			char[] rc = newRegionId.toCharArray();
//			int idx = rc.length;
//			for (; idx > 0; idx-- ){
//				if (rc[idx - 1] != '0'){
//					break;
//				}
//			}
//			
//			// 删掉尾巴第一个非0的字符，替换成0
//			if (idx <= 0){
//				break;
//			}
//			newRegionId = newRegionId.substring(0, idx - 1);
//			for (int i = 0; i <= rc.length - idx; i++){
//				newRegionId = newRegionId + "0";
//			}
//			 
//		}
//		
//		// 完全匹配不上，才返回失败
//		return false;
	}
}
