package pub.makers.shop.base.vo;

import java.io.Serializable;
import java.util.List;


public class ResultList<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 总页面
    private int totalPages;

    // 总记录数
    private int totalRecords;
	private List<T> resultList;

	public ResultList() {

	}

	public ResultList(int counts, List<T> resultList) {
		this.totalPages = counts;
		this.resultList = resultList;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	
	public void calcTotalPage(int pageNumber){
		
		if(totalRecords==0){
            setTotalPages(0);
        }else{
            setTotalPages((int) ((totalRecords - 1) / pageNumber) + 1);
        }
	}

	public static<T> ResultList<T> createSuccess(int counts, List<T> resultList){
		
		ResultList<T> result = new ResultList<T>(counts, resultList);
		
		return result;
	}
	
}
