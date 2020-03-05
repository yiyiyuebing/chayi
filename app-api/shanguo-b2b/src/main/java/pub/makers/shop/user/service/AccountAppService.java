package pub.makers.shop.user.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dev.base.exception.SessionTimeoutException;
import com.lantu.base.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pub.makers.shop.store.service.SubbranchLoginBizService;
import pub.makers.shop.store.vo.SubbranchVo;

import javax.servlet.http.HttpServletRequest;

@Service
public class AccountAppService {

	@Reference(version="1.0.0")
	private SubbranchLoginBizService loginService;
	
	public boolean hasLogin(HttpServletRequest request){
		return false;
	}
	
	/**
	 * 获取当前的登陆用户信息
	 * @param request
	 * @return
	 */
	public String getCurrShopId(HttpServletRequest request, boolean forceLogin){
		
		String token = HttpUtil.getCookieValue(request, "b2b_token");
		if (StringUtils.isBlank(token)){
			token = request.getHeader("token");
			System.out.println("get token from header, value: " + token);
		}

		if (StringUtils.isBlank(token) && forceLogin){
			throw new SessionTimeoutException();
		}
		
		if (token == null){ return token = ""; }

		String shopId = loginService.getShopIdByToken(token);
		if (StringUtils.isBlank(shopId) && forceLogin) {
			throw new SessionTimeoutException();
		}

		SubbranchVo subbranchVo = loginService.getShopInfo(shopId);

		if (subbranchVo == null) {
			return "";
		}
		
		return shopId;
	}

	public String getCurrStoreLevelId(HttpServletRequest request) {

		String token = HttpUtil.getCookieValue(request, "b2b_token");
		if (StringUtils.isBlank(token)){
			token = request.getHeader("token");
			System.out.println("get token from header, value: " + token);
		}

		if (token == null){ return token = null; }

		String shopId = loginService.getShopIdByToken(token);

		if (StringUtils.isBlank(shopId)) {
			return token = null;
		}
		SubbranchVo subbranchVo = loginService.getShopInfo(shopId);
		return subbranchVo != null ? subbranchVo.getLevelId() : null;
	}

	public String getCurrShopName(HttpServletRequest request) {
		String token = HttpUtil.getCookieValue(request, "b2b_token");

		if (token == null){ return token = null; }

		String shopId = loginService.getShopIdByToken(token);

		if (StringUtils.isBlank(shopId)) {
			return token = null;
		}
		SubbranchVo subbranchVo = loginService.getShopInfo(shopId);
		return subbranchVo != null ? subbranchVo.getName() : null;
	}
}
