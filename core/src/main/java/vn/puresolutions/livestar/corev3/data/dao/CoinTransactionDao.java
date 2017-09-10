package vn.puresolutions.livestar.corev3.data.dao;

import android.content.Context;

import vn.puresolutions.livestar.corev3.api.model.v3.bank.CoinTransaction;


/**
 * Created by Phu Tran on 4/20/2016.
 */
public class CoinTransactionDao extends DataDao<CoinTransaction, String> {
    public CoinTransactionDao(Context context) {
        super(context, CoinTransaction.class);
    }
}
