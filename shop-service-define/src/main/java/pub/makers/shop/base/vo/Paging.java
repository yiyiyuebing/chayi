package pub.makers.shop.base.vo;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public class Paging implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int ps;
	private int pn;

	private int pageSize;
	private int pageNum;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPs() {
		return ps;
	}
	public void setPs(int ps) {
		this.ps = ps;
	}
	public int getPn() {
		return pn;
	}
	public void setPn(int pn) {
		this.pn = pn;
	}
	
	public static Paging build(HttpServletRequest request){
		
		Paging pg = new Paging();
		int pageStart = 0;
		try{
			pageStart = Integer.valueOf(request.getParameter("start"));
		}
		catch (Exception e){
			pageStart = 0;
		}
		try{
			pg.setPageSize(Integer.valueOf(request.getParameter("limit")));
		}
		catch (Exception e){
			pg.setPageSize(20);
		}
		pg.setPs(pageStart);
		pg.setPn(pg.getPageSize());
		return pg;
	}

	public static Paging build2(HttpServletRequest request){

		Paging pg = new Paging();

		try{
			pg.setPageSize(Integer.valueOf(request.getParameter("pageSize")));
		}
		catch (Exception e){
			pg.setPageSize(20);
		}

		try{
			pg.setPageNum(Integer.valueOf(request.getParameter("pageNum")));
		}
		catch (Exception e){
			pg.setPageNum(1);
		}

		pg.setPs((pg.getPageNum() - 1) * pg.getPageSize());
		pg.setPn(pg.getPageSize());
		return pg;
	}

	public static Paging build3(HttpServletRequest request){

		Paging pg = new Paging();
		try{
			pg.setPageSize(Integer.valueOf(request.getParameter("limit")));
		}
		catch (Exception e){
			pg.setPageSize(20);
		}

		try{
			pg.setPageNum(Integer.valueOf(request.getParameter("page")));
		}
		catch (Exception e){
			pg.setPageNum(1);
		}

		pg.setPs((pg.getPageNum() - 1) * pg.getPageSize());
		pg.setPn(pg.getPageSize());

		return pg;
	}
}
