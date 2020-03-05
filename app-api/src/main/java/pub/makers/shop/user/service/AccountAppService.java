package pub.makers.shop.user.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pub.makers.base.exception.ValidateUtils;
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
	public String getCurrShopId(HttpServletRequest request){
		
		String token = request.getHeader("token");
		ValidateUtils.notNull(token, "00001", "参数错误，请重新登陆或升级APP");
		String shopId = loginService.getShopIdByToken(token);
		ValidateUtils.notNull(shopId, "00001", "参数错误，请重新登陆或升级APP");
		
		return shopId;
	}

	public String getCurrStoreLevelId(HttpServletRequest request) {

		String token = request.getHeader("token");
		ValidateUtils.notNull(token, "00001", "参数错误，请重新登陆或升级APP");
		String shopId = loginService.getShopIdByToken(token);
		ValidateUtils.notNull(shopId, "00001", "参数错误，请重新登陆或升级APP");

		if (StringUtils.isBlank(shopId)) {
			return null;
		}
		SubbranchVo subbranchVo = loginService.getShopInfo(shopId);
		return subbranchVo != null ? subbranchVo.getLevelId() : null;
	}
}
