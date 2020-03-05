package pub.makers.shop.promotion.enums;

/**
 * 资格类型
 * @author apple
 *
 */
public enum QualityType {
	
	all("无限制"), group("会员组");

	private String displayName;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	private QualityType(String displayName){
		this.displayName = displayName;
	}
	
}
