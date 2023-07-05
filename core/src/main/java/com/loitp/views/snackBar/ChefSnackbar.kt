package com.loitp.views.snackBar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.loitp.R

//TODO loitp refactor custom snackbar and move to code base
class ChefSnackbar(
    parent: ViewGroup,
    content: ChefSnackbarView
) : BaseTransientBottomBar<ChefSnackbar>(parent, content, content) {

    init {
        getView().setBackgroundColor(ContextCompat.getColor(view.context, android.R.color.transparent))
        getView().setPadding(0, 0, 0, 0)
    }

    companion object {

        fun make(view: View): ChefSnackbar {

            // First we find a suitable parent for our custom view
            val parent = view.findSuitableParent() ?: throw IllegalArgumentException(
                "No suitable parent found from the given view. Please provide a valid view."
            )

            // We inflate our custom view
            val customView = LayoutInflater.from(view.context).inflate(
                R.layout.layout_snackbar_chef,
                parent,
                false
            ) as ChefSnackbarView

            // We create and return our Snackbar
            return ChefSnackbar(
                parent,
                customView
            )
        }

    }

}