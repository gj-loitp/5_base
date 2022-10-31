package com.loitpcore.views.layout.swipeReveal

import android.os.Bundle
import java.util.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ViewBinderHelper {

    companion object {
        private const val BUNDLE_MAP_KEY = "ViewBinderHelper_Bundle_Map_Key"
    }

    private var mapStates = Collections.synchronizedMap(HashMap<String, Int>())
    private val mapLayouts = Collections.synchronizedMap(HashMap<String, LSwipeRevealLayout>())
    private val lockedSwipeSet = Collections.synchronizedSet(HashSet<String>())

    @Volatile
    private var openOnlyOne = false
    private val stateChangeLock = Any()

    /**
     * Help to save and restore open/close state of the swipeLayout. Call this method
     * when you bind your view holder with the data object.
     *
     * @param swipeLayout swipeLayout of the current view.
     * @param id          a string that uniquely defines the data object of the current view.
     */
    fun bind(swipeLayout: LSwipeRevealLayout, id: String) {
        if (swipeLayout.shouldRequestLayout()) {
            swipeLayout.requestLayout()
        }
        mapLayouts.values.remove(swipeLayout)
        mapLayouts[id] = swipeLayout
        swipeLayout.abort()
        swipeLayout.setDragStateChangeListener { state: Int ->
            mapStates[id] = state
            if (openOnlyOne) {
                closeOthers(id, swipeLayout)
            }
        }

        // first time binding.
        if (!mapStates.containsKey(id)) {
            mapStates[id] = LSwipeRevealLayout.STATE_CLOSE
            swipeLayout.close(false)
        } else {
            val state = mapStates[id]
            if (state == LSwipeRevealLayout.STATE_CLOSE || state == LSwipeRevealLayout.STATE_CLOSING || state == LSwipeRevealLayout.STATE_DRAGGING) {
                swipeLayout.close(false)
            } else {
                swipeLayout.open(false)
            }
        }

        // set lock swipe
        swipeLayout.setLockDrag(lockedSwipeSet.contains(id))
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     */
    fun saveStates(outState: Bundle?) {
        if (outState == null) {
            return
        }
        val statesBundle = Bundle()
        for ((key, value) in mapStates) {
            statesBundle.putInt(key, value)
        }
        outState.putBundle(BUNDLE_MAP_KEY, statesBundle)
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     */
    fun restoreStates(inState: Bundle?) {
        if (inState == null) {
            return
        }
        if (inState.containsKey(BUNDLE_MAP_KEY)) {
            val restoredMap = HashMap<String, Int>()
            val statesBundle = inState.getBundle(BUNDLE_MAP_KEY)
            val keySet = statesBundle?.keySet()
            if (keySet != null) {
                for (key in keySet) {
                    restoredMap[key] = statesBundle.getInt(key)
                }
            }
            mapStates = restoredMap
        }
    }

    /**
     * Lock swipe for some layouts.
     *
     * @param id a string that uniquely defines the data object.
     */
    @Suppress("unused")
    fun lockSwipe(vararg id: String) {
        setLockSwipe(true, *id)
    }

    /**
     * Unlock swipe for some layouts.
     *
     * @param id a string that uniquely defines the data object.
     */
    @Suppress("unused")
    fun unlockSwipe(vararg id: String) {
        setLockSwipe(false, *id)
    }

    /**
     * @param openOnlyOne If set to true, then only one row can be opened at a time.
     */
    fun setOpenOnlyOne(openOnlyOne: Boolean) {
        this.openOnlyOne = openOnlyOne
    }

    /**
     * Open a specific layout.
     *
     * @param id unique id which identifies the data object which is bind to the layout.
     */
    @Suppress("unused")
    fun openLayout(id: String) {
        synchronized(stateChangeLock) {
            mapStates[id] = LSwipeRevealLayout.STATE_OPEN
            when {
                mapLayouts.containsKey(id) -> {
                    val layout = mapLayouts[id]
                    layout?.open(true)
                }
                openOnlyOne -> {
                    closeOthers(id, mapLayouts[id])
                }
                else -> {
                }
            }
        }
    }

    /**
     * Close a specific layout.
     *
     * @param id unique id which identifies the data object which is bind to the layout.
     */
    @Suppress("unused")
    fun closeLayout(id: String) {
        synchronized(stateChangeLock) {
            mapStates[id] = LSwipeRevealLayout.STATE_CLOSE
            if (mapLayouts.containsKey(id)) {
                val layout = mapLayouts[id]
                layout?.close(true)
            }
        }
    }

    /**
     * Close others swipe layout.
     *
     * @param id          layout which bind with this data object id will be excluded.
     * @param swipeLayout will be excluded.
     */
    private fun closeOthers(id: String, swipeLayout: LSwipeRevealLayout?) {
        synchronized(stateChangeLock) {
            // close other rows if openOnlyOne is true.
            if (openCount > 1) {
                for (entry in mapStates.entries) {
                    if (entry.key != id) {
                        entry.setValue(LSwipeRevealLayout.STATE_CLOSE)
                    }
                }
                for (layout in mapLayouts.values) {
                    if (layout !== swipeLayout) {
                        layout.close(true)
                    }
                }
            }
        }
    }

    private fun setLockSwipe(lock: Boolean, vararg id: String) {
        if (id.isEmpty()) {
            return
        }
        if (lock) {
            lockedSwipeSet.addAll(mutableListOf(*id))
        } else {
            lockedSwipeSet.removeAll(mutableListOf(*id))
        }
        for (s in id) {
            val layout = mapLayouts[s]
            layout?.setLockDrag(lock)
        }
    }

    private val openCount: Int
        get() {
            var total = 0
            for (state in mapStates.values) {
                if (state == LSwipeRevealLayout.STATE_OPEN || state == LSwipeRevealLayout.STATE_OPENING) {
                    total++
                }
            }
            return total
        }
}
