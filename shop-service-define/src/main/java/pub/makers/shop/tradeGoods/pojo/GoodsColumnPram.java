package pub.makers.shop.tradeGoods.pojo;

import java.io.Serializable;

/**
 * Created by devpc on 2017/8/8.
 */
public class GoodsColumnPram implements Serializable{
    private String id;

    private String columnName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
