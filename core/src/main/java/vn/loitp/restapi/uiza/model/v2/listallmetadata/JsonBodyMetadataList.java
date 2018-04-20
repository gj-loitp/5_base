package vn.loitp.restapi.uiza.model.v2.listallmetadata;

/**
 * Created by LENOVO on 3/23/2018.
 */

public class JsonBodyMetadataList {
    private int limit;
    private String orderBy;
    private String orderType;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
