package vn.puresolutions.livestar.corev3.api.model.old;


import com.google.gson.annotations.SerializedName;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class ErrorResponse {
    @SerializedName("error")
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
