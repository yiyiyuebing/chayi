package pub.makers.shop.base.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.entity.SysDict;

@Service
public class ErrorMsgService {

	@Autowired
	private SysDictService sysDictService;
	
	@PostConstruct
	public void init(){
		
		List<SysDict> dictList = sysDictService.list(Conds.get().eq("dictType", "error_msg"));
		for (SysDict dict : dictList){
			ValidateUtils.errorCodes.put(dict.getCode(), dict.getValue());
		}
		
	}
}
