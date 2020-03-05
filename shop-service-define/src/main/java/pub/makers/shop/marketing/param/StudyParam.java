package pub.makers.shop.marketing.param;

import java.io.Serializable;

/**
 * Created by dy on 2017/9/30.
 */
public class StudyParam implements Serializable {

    private String studyType;
    private String studyChildType;
    private String conditions;
    private String type;
    private String createTimeOrder;
    private String readNumOrder;
    private Integer page;

    public String getStudyType() {
        return studyType;
    }

    public void setStudyType(String studyType) {
        this.studyType = studyType;
    }

    public String getStudyChildType() {
        return studyChildType;
    }

    public void setStudyChildType(String studyChildType) {
        this.studyChildType = studyChildType;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTimeOrder() {
        return createTimeOrder;
    }

    public void setCreateTimeOrder(String createTimeOrder) {
        this.createTimeOrder = createTimeOrder;
    }

    public String getReadNumOrder() {
        return readNumOrder;
    }

    public void setReadNumOrder(String readNumOrder) {
        this.readNumOrder = readNumOrder;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
