package pub.makers.shop.logistics.vo;

import java.io.Serializable;
import java.util.List;

import pub.makers.shop.tradeOrder.vo.IndentListVo;

public class FreightTplRelVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tplId;
	
	private List<IndentListVo> indentList;

	public String getTplId() {
		return tplId;
	}

	public void setTplId(String tplId) {
		this.tplId = tplId;
	}

	public List<IndentListVo> getIndentList() {
		return indentList;
	}

	public void setIndentList(List<IndentListVo> indentList) {
		this.indentList = indentList;
	}
	
}
