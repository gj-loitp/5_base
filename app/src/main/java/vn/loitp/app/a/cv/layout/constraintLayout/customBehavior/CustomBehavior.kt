package vn.loitp.app.a.cv.layout.constraintLayout.customBehavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import de.hdodenhof.circleimageview.CircleImageView

class CustomBehavior(
    context: Context?,
    attrs: AttributeSet?
) : CoordinatorLayout.Behavior<CircleImageView>(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: CircleImageView,
        dependency: View
    ): Boolean {
        return dependency is Toolbar
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: CircleImageView,
        dependency: View
    ): Boolean {
        val dependencyLocation = IntArray(2)
        val childLocation = IntArray(2)
        dependency.getLocationInWindow(dependencyLocation)
        child.getLocationInWindow(childLocation)
        val diff = childLocation[1] - dependencyLocation[1].toFloat()
        if (diff > 0) {
            val scale = diff / childLocation[1].toFloat()
            child.scaleX = 1 + scale
            child.scaleY = 1 + scale
        }
        return false
    }
}
