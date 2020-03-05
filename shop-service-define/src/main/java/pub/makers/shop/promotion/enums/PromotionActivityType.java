package pub.makers.shop.promotion.enums;

/**
 * 促销活动类型
 * @author apple
 *
 */
public enum PromotionActivityType {
	
	sale("限时打折", 8), tuan("团购", 9), zengpin("赠品", 0), presell("预售", 10), manjian("满减", 1), manzeng("满赠", 1);

	private String displayName;

	private Integer level;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	PromotionActivityType(String displayName, Integer level) {
		this.displayName = displayName;
		this.level = level;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
}
