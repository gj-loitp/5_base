package vn.puresolutions.livestar.core.api.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

import vn.puresolutions.livestar.core.api.model.def.TransactionStatus;

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
