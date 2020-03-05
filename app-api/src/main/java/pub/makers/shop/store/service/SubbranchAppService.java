package pub.makers.shop.store.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import pub.makers.shop.base.service.SmsService;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.vo.SubbranchVo;

import java.util.List;

@Service
public class SubbranchAppService {

	@Reference(version="1.0.0")
	private SubbranchAccountBizService accountBizService;
	@Reference(version="1.0.0")
	private SmsService sms;
	
	public boolean isSubAccount(String subbranchId){
		
		return accountBizService.isSubAccount(subbranchId);
	}
	
	public Subbranch addSubAccount(String parentId, Subbranch subAccount){
		
		return accountBizService.addSubAccount(parentId, subAccount);
	}
	
	public Subbranch editSubAccount(Subbranch subAccount){
		
		return accountBizService.editSubAccount(subAccount);
	}
	
	
	public void updateIsValid(String subAccountid, String isValid){
		
		accountBizService.updateIsValid(subAccountid, isValid);
	}
	
	public List<Subbranch> listSubAccountByParent(String parentId){
		
		return accountBizService.listSubAccountByParent(parentId);
	}
	
	public ResultData querySummary(String shopId){
		
		return accountBizService.querySummary(shopId);
	}
	
	public void delete(String subAccountid) {
		
		accountBizService.delete(subAccountid);
		
	}

	public List<SubbranchVo> findNearBy(Double lat, Double lng, Integer distance) {
		
		return accountBizService.findNearBy(lat, lng, distance);
	}
}
