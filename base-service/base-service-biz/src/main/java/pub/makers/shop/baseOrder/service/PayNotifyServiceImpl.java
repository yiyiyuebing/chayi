package pub.makers.shop.baseOrder.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lantu.base.pay.PayNotifyService;

@Service
public class PayNotifyServiceImpl implements PayNotifyService{

	@Override
	public boolean doBusiness(String arg0, String arg1, Map<String, String> arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasBusinessDone(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void lockTrade(String arg0) {
		// TODO Auto-generated method stub
		
	}

}
