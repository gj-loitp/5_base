package vn.puresolutions.livestar.core.api.model.param;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 12/16/2015
 */
public class FollowParam {
    private String status;

    public FollowParam(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
