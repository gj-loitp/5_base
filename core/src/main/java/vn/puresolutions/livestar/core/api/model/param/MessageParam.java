package vn.puresolutions.livestar.core.api.model.param;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/25/2015
 */
public class MessageParam {
    private String message;

    public MessageParam(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
