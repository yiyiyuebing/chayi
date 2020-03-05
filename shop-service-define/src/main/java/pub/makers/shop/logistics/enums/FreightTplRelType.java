package pub.makers.shop.logistics.enums;

public enum FreightTplRelType {

	category("类目");
	
	private String name;
    
    
    private FreightTplRelType(String name) {
        this.name = name;
    }
    
    public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
}
