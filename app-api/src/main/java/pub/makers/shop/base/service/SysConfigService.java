package pub.makers.shop.base.service;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;

import pub.makers.shop.base.entity.SysDict;

@Service
public class SysConfigService {
	
	@Reference(version="1.0.0")
	private SysDictBizService dictService;
	
	public boolean canRegister(){
		
		SysDict dict = ListUtils.getSingle(dictService.list("syscfg", "register"));
		boolean canRegister = dict != null && BoolType.T.name().equals(dict.getValue());
		
		return canRegister;
	}
}
