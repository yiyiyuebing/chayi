package pub.makers.shop.store.enums;

public enum StoreJoinStatus {

	cghz("成功合作"), qtz("洽谈中"), wqt("未洽谈");
	
	private String displayName;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	private StoreJoinStatus(String displayName){
		this.displayName = displayName;
	}
	
}
