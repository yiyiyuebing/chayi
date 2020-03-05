package pub.makers.shop.baseGood.enums;


/**
 * 订单业务类型
 * @author apple
 *
 */
public enum PageTplClassify {

	pclist("PC列表"), pcdetail("PC详情"), mobileb2c("移动商城"), mobileb2b("移动采购"),mobileb2bbottom("H5首页模块管理底部商品推荐");
	private String name;
    
    
    private PageTplClassify(String name) {
        this.name = name;
    }
    
    public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
}
