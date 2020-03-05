package pub.makers.shop.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pub.makers.shop.test.service.TestService;

@Controller
@RequestMapping("test")
public class TestController {

	@Autowired
	private TestService testService;
	
	@RequestMapping("test")
	public void test1(){
		testService.test1();
		System.out.println(1);
	}
}
