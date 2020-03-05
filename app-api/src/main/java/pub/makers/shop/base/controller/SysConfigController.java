package pub.makers.shop.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pub.makers.shop.base.service.SysConfigService;
import pub.makers.shop.base.vo.ResultData;

@Controller
@RequestMapping("/cfg")
public class SysConfigController {

	@Autowired
	private SysConfigService cfgService;
	
	@RequestMapping("reg")
	@ResponseBody
	public ResultData canRegister(){
		
		boolean canReg = cfgService.canRegister();
		
		return ResultData.createSuccess("reg", canReg);
	}
}
