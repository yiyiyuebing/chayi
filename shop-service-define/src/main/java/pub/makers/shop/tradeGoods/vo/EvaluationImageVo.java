/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package pub.makers.shop.tradeGoods.vo;

import java.io.Serializable;

/**
 *@Title: ImageVo
 *@Description:
 *@Author:Administrator
 *@Since:2016年3月25日
 *@Version:1.1.0
 */
public class EvaluationImageVo implements Serializable {

    private Long id;


    private String url;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}

}
