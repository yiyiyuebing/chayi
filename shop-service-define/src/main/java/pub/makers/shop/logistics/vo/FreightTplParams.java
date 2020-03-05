package pub.makers.shop.logistics.vo;

import java.io.Serializable;

/**
 * Created by dy on 2017/4/17.
 */
public class FreightTplParams implements Serializable {

    private String name;

    private String freightMethodId;

    private String relType;

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFreightMethodId() {
        return freightMethodId;
    }

    public void setFreightMethodId(String freightMethodId) {
        this.freightMethodId = freightMethodId;
    }
}
