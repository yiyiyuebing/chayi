package pub.makers.shop.user.utils;

import com.dev.base.json.JsonUtils;
import com.dev.base.utils.SpringContextUtils;
import com.dev.base.utils.UUIDUtils;
import com.lantu.base.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.jedis.JedisCallback;
import pub.makers.jedis.JedisTemplate;
import pub.makers.shop.user.entity.WeixinUserInfo;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

public class WxUserUtils {

	private static JedisTemplate jedisTemplate;
	private static final String USER_TOKEN_NAME = "b2c_token";
	
	public static String getUserId(){
		
		WeixinUserInfo user = getCurrUser();
		
		return user == null ? null : user.getID() + "";
	}
	
	public static WeixinUserInfo getCurrUser(){
		
		WeixinUserInfo userInfo = null;
		final String token = HttpUtil.getCookieValue(getRequest(), USER_TOKEN_NAME);
		if (StringUtils.isNotBlank(token)){
			userInfo = getJedis().execute(new JedisCallback<WeixinUserInfo>() {

				@Override
				public WeixinUserInfo doInJedis(Jedis jedis) {
					
					String userJson = jedis.get(String.format("b2c_token_%s", token));
					if (StringUtils.isNotBlank(userJson)){
						return JsonUtils.toObject(userJson, WeixinUserInfo.class);
					}
					return null;
				}
			});
		}
		
		return userInfo;
	}
	
	public static String setCurrUser(final WeixinUserInfo userInfo){
		
		final String token = UUIDUtils.uuid3();
		getJedis().execute(new JedisCallback<Object>() {

			@Override
			public Object doInJedis(Jedis jedis) {
				
				jedis.set(String.format("b2c_token_%s", token), JsonUtils.toJson(userInfo));
				return null;
			}
		});
		
		return token;
	}
	
	
	private static HttpServletRequest getRequest(){
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		ValidateUtils.notNull(requestAttributes, "无法获取请求参数");

		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		return request;
	}
	
	private synchronized static JedisTemplate getJedis(){
		if (jedisTemplate == null){
			jedisTemplate = SpringContextUtils.getBean(JedisTemplate.class);
		}
		
		return jedisTemplate;
	}
}
