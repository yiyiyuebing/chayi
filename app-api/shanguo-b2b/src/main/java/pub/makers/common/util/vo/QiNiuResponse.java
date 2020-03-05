package pub.makers.common.util.vo;

/**
 * Created by dy on 2017/6/14.
 */
public class QiNiuResponse {

    private String id;
    private String url;
    private String name;

    private boolean success;

    public QiNiuResponse() {
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
