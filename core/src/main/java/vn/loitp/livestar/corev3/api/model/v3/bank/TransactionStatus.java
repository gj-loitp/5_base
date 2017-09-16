package vn.loitp.livestar.corev3.api.model.v3.bank;


import com.google.gson.annotations.SerializedName;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public enum TransactionStatus {
    @SerializedName("1")
    VERIFIED(1),
    @SerializedName("0")
    UNVERIFIED(0);

    private int value;

    TransactionStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TransactionStatus fromValue(int value) {
        for (TransactionStatus c : TransactionStatus.values()) {
            if (c.value == value) {
                return c;
            }
        }
        throw new IllegalArgumentException("Invalid TransactionStatus value: "
                + value);
    }
}
