package com.loitp.views.layout.draggablePanel

/**
 * Listener created to be notified when some drag actions are performed over DraggablePanel or
 * DraggableView instances.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
interface DraggableListener {

    /**
     * Called when the view is maximized.
     */
    fun onMaximized()

    /**
     * Called when the view is minimized.
     */
    fun onMinimized()

    /**
     * Called when the view is closed to the left.
     */
    fun onClosedToLeft()

    /**
     * Called when the view is closed to the right.
     */
    fun onClosedToRight()
}
