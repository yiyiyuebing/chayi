package pub.makers.shop.logistics.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lantu.base.common.entity.BoolType;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.logistics.service.FreightTplGoodRelService;
import pub.makers.shop.logistics.dao.FreightTplGoodRelDao;
import pub.makers.shop.logistics.entity.FreightTplGoodRel;

@Service
public class FreightTplGoodRelServiceImpl extends BaseCRUDServiceImpl<FreightTplGoodRel, String, FreightTplGoodRelDao>
										implements FreightTplGoodRelService{

	@Override
	public FreightTplGoodRel getRelBySkuIdAndType(String skuId, String relType) {
		
		if (StringUtils.isBlank(skuId)){
			return null;
		}
		
		return get(Conds.get().eq("goodSkuId", skuId).eq("relType", relType).eq("delFlag", BoolType.F.name()).eq("isValid", BoolType.T.name()));
	}
	
}
