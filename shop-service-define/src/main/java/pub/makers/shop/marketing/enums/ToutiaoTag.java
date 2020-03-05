package pub.makers.shop.marketing.enums;

public enum ToutiaoTag {

	gg("公告"), cx("促销");
	
	private String displayName;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	private ToutiaoTag(String displayName){
		this.displayName = displayName;
	}
	
}
