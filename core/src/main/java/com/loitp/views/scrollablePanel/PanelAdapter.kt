package com.loitp.views.scrollablePanel

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Base class for an Adapter
 *
 *
 *
 * Adapters provide a binding from an app-specific data set to views that are displayed
 * within a 2-dimensional [RecyclerView].
 */
abstract class PanelAdapter {
    /**
     * Returns the total number of items every row in the data set hold by the adapter.
     *
     * @return The total number of items every row in this adapter.
     */
    abstract val rowCount: Int

    /**
     * Returns the total number of items every column in the data set hold by the adapter.
     *
     * @return The total number of items every column in this adapter.
     */
    abstract val columnCount: Int

    /**
     * Return the view type of the item at `row column` for the purposes
     * of view recycling.
     *
     *
     *
     * The default implementation of this method returns 0, making the assumption of
     * a single view type for the adapter. Unlike ListView adapters, types need not
     * be contiguous. Consider using id resources to uniquely identify item view types.
     *
     * @param row    row-position to query
     * @param column column-position to query
     * @return integer value identifying the type of the view needed to represent the item at
     * `row column`. Type codes need not be contiguous.
     */
    open fun getItemViewType(
        row: Int,
        column: Int
    ): Int {
        return 0
    }

    /**
     * see [RecyclerView.Adapter.onBindViewHolder]  }
     *
     * @param holder ViewHolder
     * @param row    row-position to query
     * @param column column-position to query
     */
    abstract fun onBindViewHolder(
        holder: RecyclerView.ViewHolder?,
        row: Int,
        column: Int
    )

    /**
     * see [RecyclerView.Adapter.onCreateViewHolder]  }
     */
    abstract fun onCreateViewHolder(
        parent: ViewGroup?,
        viewType: Int
    ): RecyclerView.ViewHolder?
}
