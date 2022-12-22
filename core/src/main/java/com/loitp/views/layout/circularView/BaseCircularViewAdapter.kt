package com.loitp.views.layout.circularView

import android.database.DataSetObserver

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
interface BaseCircularViewAdapter {
    /**
     * Get the count of how many markers will show around the circular view. This number should be zero or positive.
     *
     * @return Number of markers to show.
     */
    val count: Int

    /**
     * Setup the marker that should show at a given position. The position will be between 0 and the value returned by [.getCount].
     *
     * @param position Position of the marker to show.
     * @param marker   The marker that will be used to display.
     */
    fun setupMarker(position: Int, marker: Marker?)

    /**
     * Register an observer on this adapter.
     *
     * @param observer observer to register.
     */
    fun registerDataSetObserver(observer: DataSetObserver?)

    /**
     * Unregister an observer on this adapter.
     *
     * @param observer observer to unregister.
     */
    fun unregisterDataSetObserver(observer: DataSetObserver?)

    /**
     * Notifies the attached observers that the underlying data has been changed
     * and any View reflecting the data set should refresh itself.
     */
    fun notifyDataSetChanged()

    /**
     * Notifies the attached observers that the underlying data is no longer valid
     * or available. Once invoked this adapter is no longer valid and should
     * not report further data set changes.
     */
    fun notifyDataSetInvalidated()
}
