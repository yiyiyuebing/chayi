package pub.makers.shop.base.entity;

import java.io.Serializable;
import java.util.Date;

public class Region implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;		
	
	/**  */
	private Integer pid;		
	
	/**  */
	private String name;		
	

	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getId(){
		return id;
	}
	
	public void setPid(Integer pid){
		this.pid = pid;
	}
	
	public Integer getPid(){
		return pid;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
}
