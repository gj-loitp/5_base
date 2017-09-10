package vn.puresolutions.livestar.core.api.model.param;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class OTPParam {
    private String phone;
    private String otp;

    public OTPParam(String phone, String otp) {
        this.phone = phone;
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
