package vn.loitp.livestar.corev3.api.model.v3.bank;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

import vn.loitp.livestar.corev3.api.model.v3.BaseModel;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class CoinTransaction extends BaseModel {
    @DatabaseField(id = true)
    private transient String receiptId;

    @SerializedName("status_purchase")
    @DatabaseField
    private TransactionStatus status;

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
