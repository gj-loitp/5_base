/*
 * Copyright (C) 2014 Pedro Vicente Gómez Sánchez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.views.layout.draggablepanel

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
