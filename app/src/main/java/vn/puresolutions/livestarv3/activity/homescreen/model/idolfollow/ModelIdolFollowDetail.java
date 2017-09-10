package vn.puresolutions.livestarv3.activity.homescreen.model.idolfollow;

/**
 * Created by pratap.kesaboyina on 01-12-2015.
 */
public class ModelIdolFollowDetail {
    private String name;
    private String url;
    private String description;

    public ModelIdolFollowDetail() {
    }

    public ModelIdolFollowDetail(String name, String url) {
        this.name = name;
        this.url = url;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
