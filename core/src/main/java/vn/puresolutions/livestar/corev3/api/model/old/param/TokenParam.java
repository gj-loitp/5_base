package vn.puresolutions.livestar.corev3.api.model.old.param;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class TokenParam {
    private String email;
    private String token;

    public TokenParam(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
