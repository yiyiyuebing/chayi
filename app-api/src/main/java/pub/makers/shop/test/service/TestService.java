package pub.makers.shop.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;

import pub.makers.shop.cargo.service.CargoImageBizService;

@Service
public class TestService {

	@Reference(version="1.0.0")
	private CargoImageBizService imageService;
	
	public void test1(){
		
		List<String> imgList = Lists.newArrayList(new String[]{"http://o7o0uv2j1.bkt.clouddn.com/1498806596328c65c650.jpg", "http://o7o0uv2j1.bkt.clouddn.com/149880659631128c65c650.jpg"});
		imageService.saveGroupImage(imgList, "123");
	}
}
