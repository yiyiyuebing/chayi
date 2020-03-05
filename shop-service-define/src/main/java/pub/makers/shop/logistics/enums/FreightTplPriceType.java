package pub.makers.shop.logistics.enums;

public enum FreightTplPriceType {

	piece("记件"), amount("金额");
	
	private String name;
    
    
    private FreightTplPriceType(String name) {
        this.name = name;
    }
    
    public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
}
