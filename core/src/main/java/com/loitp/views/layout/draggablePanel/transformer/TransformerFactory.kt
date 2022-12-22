package com.loitp.views.layout.draggablePanel.transformer

import android.view.View

/**
 * Factory created to provide Transformer implementations like ResizeTransformer o
 * ScaleTransformer.
 *
 * @author Pedro Vicente Gómez Sánchez
 */
class TransformerFactory {
    fun getTransformer(
        resize: Boolean,
        view: View,
        parent: View
    ): Transformer {
        return if (resize) {
            ResizeTransformer(view = view, parent = parent)
        } else {
            ScaleTransformer(view = view, parent = parent)
        }
    }
}
