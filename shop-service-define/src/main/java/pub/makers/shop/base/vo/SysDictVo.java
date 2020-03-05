package pub.makers.shop.base.vo;

import java.io.Serializable;

/**
 * Created by devpc on 2017/7/24.
 *
 * 查询标签
 */
public class SysDictVo implements Serializable{
    /** 主键 */
    private String dictid;

    /** 查询的标签值 */
    private String valuelabel;
    /**
     * 这是标签
     */
    private String dictType;

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictid() {
        return dictid;
    }

    public void setDictid(String dictid) {
        this.dictid = dictid;
    }

    public String getValuelabel() {
        return valuelabel;
    }

    public void setValuelabel(String valuelabel) {
        this.valuelabel = valuelabel;
    }
}
