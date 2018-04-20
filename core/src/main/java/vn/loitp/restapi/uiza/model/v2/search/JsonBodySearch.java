package vn.loitp.restapi.uiza.model.v2.search;

/**
 * Created by LENOVO on 3/23/2018.
 */

public class JsonBodySearch {
    private String keyword;
    private int limit;
    private int page;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
}
