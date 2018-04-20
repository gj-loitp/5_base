
package vn.loitp.restapi.uiza.model.v2.listallentity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metadata {

    @SerializedName("total")
    @Expose
    private double total;
    @SerializedName("result")
    @Expose
    private double result;
    @SerializedName("page")
    @Expose
    private double page;
    @SerializedName("limit")
    @Expose
    private double limit;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public double getPage() {
        return page;
    }

    public void setPage(double page) {
        this.page = page;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

}
