package vn.puresolutions.livestar.core.api.model.param;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/29/2015
 */
public class SignInLSParam extends SignInParam {
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
