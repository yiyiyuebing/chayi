package pub.makers.shop.baseGood.enums;


/**
 * 页面模板模块类型
 * @author apple
 *
 */
public enum PageTplModelType {

	ad("AD"), cc("橱窗");
	private String name;
    
    
    private PageTplModelType(String name) {
        this.name = name;
    }
    
    public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
}
