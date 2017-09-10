package vn.puresolutions.livestar.core.data.service;

import android.content.Context;

import vn.puresolutions.livestar.core.api.model.CoinTransaction;
import vn.puresolutions.livestar.core.api.model.def.TransactionStatus;
import vn.puresolutions.livestar.core.data.dao.CoinTransactionDao;

/**
 * Created by Phu Tran on 4/20/2016.
 */
public class CoinTransactionDBService {
    private CoinTransactionDao dao;

    public CoinTransactionDBService(Context context) {
        dao = new CoinTransactionDao(context);
    }

    public void insert(CoinTransaction coinTransaction) {
        dao.insert(coinTransaction);
    }

    public void updateStatus(String id, TransactionStatus status) {
        CoinTransaction coinTransaction = getById(id);
        if (coinTransaction != null) {
            coinTransaction.setStatus(status);
            dao.update(coinTransaction);
        }
    }

    public CoinTransaction getById(String id) {
        return dao.getById(id);
    }

    public void delete(CoinTransaction coinTransaction) {
        if (coinTransaction != null) {
            dao.deleteById(coinTransaction.getReceiptId());
        }
    }
}
