package com.loitp.views.layout.circularView

import android.database.DataSetObservable
import android.database.DataSetObserver

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
abstract class SimpleCircularViewAdapter : BaseCircularViewAdapter {
    private val mDataSetObservable = DataSetObservable()
    override fun registerDataSetObserver(observer: DataSetObserver?) {
        mDataSetObservable.registerObserver(observer)
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
        mDataSetObservable.unregisterObserver(observer)
    }

    /**
     * Notifies the attached observers that the underlying data has been changed
     * and any View reflecting the data set should refresh itself.
     */
    override fun notifyDataSetChanged() {
        mDataSetObservable.notifyChanged()
    }

    /**
     * Notifies the attached observers that the underlying data is no longer valid
     * or available. Once invoked this adapter is no longer valid and should
     * not report further data set changes.
     */
    override fun notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated()
    }
}
