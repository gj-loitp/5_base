
package vn.loitp.restapi.livestar.corev3.api.model.v3.search;

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
    @SerializedName("keyword")
    @Expose
    private String keyword;

    @SerializedName("total_item")
    @Expose
    private int total_item;

    public int getTotal_item() {
        return total_item;
    }

    public void setTotal_item(int total_item) {
        this.total_item = total_item;
    }

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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
