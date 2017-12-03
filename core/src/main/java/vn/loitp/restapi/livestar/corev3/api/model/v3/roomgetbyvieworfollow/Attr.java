
package vn.loitp.restapi.livestar.corev3.api.model.v3.roomgetbyvieworfollow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attr {

    @SerializedName("limit")
    @Expose
    private int limit;
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("total_page")
    @Expose
    private int totalPage;
    @SerializedName("offset")
    @Expose
    private int offset;
    @SerializedName("total_item")
    @Expose
    private int totalItem;
    @SerializedName("keyword")
    @Expose
    private String keyword;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
