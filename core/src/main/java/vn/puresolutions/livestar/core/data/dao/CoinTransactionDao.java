package vn.puresolutions.livestar.core.data.dao;

import android.content.Context;

import vn.puresolutions.livestar.core.api.model.CoinPackage;
import vn.puresolutions.livestar.core.api.model.CoinTransaction;

/**
 * Created by Phu Tran on 4/20/2016.
 */
public class CoinTransactionDao extends DataDao<CoinTransaction, String> {
    public CoinTransactionDao(Context context) {
        super(context, CoinTransaction.class);
    }
}
