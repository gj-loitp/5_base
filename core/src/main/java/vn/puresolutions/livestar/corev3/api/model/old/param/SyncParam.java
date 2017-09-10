package vn.puresolutions.livestar.corev3.api.model.old.param;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class SyncParam {
    private String phone;
    private String otp;
    private String password;

    public SyncParam(String phone, String otp, String password) {
        this.phone = phone;
        this.otp = otp;
        this.password = password;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
