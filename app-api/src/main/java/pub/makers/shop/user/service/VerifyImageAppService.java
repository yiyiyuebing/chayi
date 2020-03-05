package pub.makers.shop.user.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.makers.jedis.JedisCallback;
import pub.makers.jedis.JedisTemplate;
import pub.makers.shop.user.utils.VerifyCodeUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 获取图形验证码
 * @author apple
 *
 */
@Service
public class VerifyImageAppService {

	@Autowired
	private JedisTemplate jedisTemplate;
	
	public void showVerifyImage(HttpServletRequest request, HttpServletResponse response, final String requestId) throws IOException{
    	
    	response.setHeader("Pragma", "No-cache"); 
        response.setHeader("Cache-Control", "no-cache"); 
        response.setDateHeader("Expires", 0); 
        response.setContentType("image/jpeg"); 
           
        //生成随机字串 
        final String verifyCode = VerifyCodeUtils.generateVerifyCode(4); 
        //存入会话session 
        final HttpSession session = request.getSession(true); 
        //删除以前的
        session.removeAttribute("verCode");
        session.setAttribute("verCode", verifyCode.toLowerCase()); 
        jedisTemplate.execute(new JedisCallback<Object>() {

			@Override
			public Object doInJedis(Jedis jedis) {
				String cacheKey = getRequestKey(requestId);
				jedis.set(cacheKey, verifyCode);
				// 有效期30分钟
				jedis.expire(cacheKey, 1800);
				return null;
			}
		});
        //生成图片 
        int w = 100, h = 30; 
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode); 
    }
	
	public boolean isCodeValid(String requestId, String iptVerifyCode){
		
		// 检查验证码是否正确
		boolean valid = true;
		String verCode = getYzm(requestId);
		if (StringUtils.isBlank(verCode) || StringUtils.isBlank(iptVerifyCode) || !verCode.toLowerCase().equals(iptVerifyCode.toLowerCase())){
			valid = false;
		}
		
		return valid;
	}
	
	private String getRequestKey(String requestId){
    	
    	return String.format("verify_img_%s", requestId);
    }
	
	private String getYzm(final String requestId){
		
		return jedisTemplate.execute(new JedisCallback<String>() {

			@Override
			public String doInJedis(Jedis jedis) {
				
				String key = getRequestKey(requestId);
				String code = jedis.get(key);
				return code;
			}
			
		});
	}
	
	public String delYzm(final String requestId){
		return jedisTemplate.execute(new JedisCallback<String>() {

			@Override
			public String doInJedis(Jedis jedis) {
				
				String key = getRequestKey(requestId);
				jedis.del(key);
				return null;
			}
			
		});
	}
}
