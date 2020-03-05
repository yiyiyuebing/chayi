package pub.makers.shop.user.pojo;

/**
 * @Title: WeixinOauth2Token.java
 * @Package com.club.web.client.pojo
 * @Description: TODO(微信网页授权信息)
 * @author 柳伟军
 * @date 2016年4月16日 下午2:57:15
 * @version V1.0
 */
public class WeixinOauth2Token2Ch {
	// 网页授权接口调用凭证
	private String access_token;
	// 凭证有效时长
	private int expires_in;
	// 用于刷新凭证
	private String refresh_token;
	// 用户标识
	private String openid;
	// 用户授权作用
	private String scope;


	
	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}
