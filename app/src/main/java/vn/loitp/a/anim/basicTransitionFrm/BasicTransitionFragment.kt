package vn.loitp.a.anim.basicTransitionFrm

import android.os.Bundle
import android.transition.Scene
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import vn.loitp.R

class BasicTransitionFragment : Fragment(), RadioGroup.OnCheckedChangeListener {

    companion object {

        fun newInstance(): BasicTransitionFragment {
            return BasicTransitionFragment()
        }
    }

    // We transition between these Scenes
    private var mScene1: Scene? = null
    private var mScene2: Scene? = null
    private var mScene3: Scene? = null

    /**
     * A custom TransitionManager
     */
    private var mTransitionManagerForScene3: TransitionManager? = null

    /**
     * Transitions take place in this ViewGroup. We retain this for the dynamic transition on scene 4.
     */
    private var mSceneRoot: ViewGroup? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_basic_transition, container, false)!!

        val radioGroup = view.findViewById<RadioGroup>(R.id.select_scene)
        radioGroup.setOnCheckedChangeListener(this)
        mSceneRoot = view.findViewById(R.id.scene_root)
        // BEGIN_INCLUDE(instantiation_from_view)
        // A Scene can be instantiated from a live view hierarchy.
        mScene1 = Scene(mSceneRoot, mSceneRoot?.findViewById(R.id.container))
        // END_INCLUDE(instantiation_from_view)

        // BEGIN_INCLUDE(instantiation_from_resource)
        // You can also inflate a generate a Scene from a layout resource file.
        mScene2 =
            Scene.getSceneForLayout(mSceneRoot, R.layout.frm_basic_transition_scene2, activity)
        // END_INCLUDE(instantiation_from_resource)

        // Another scene from a layout resource file.
        mScene3 =
            Scene.getSceneForLayout(mSceneRoot, R.layout.frm_basic_transition_scene3, activity)

        // BEGIN_INCLUDE(custom_transition_manager)
        // We create a custom TransitionManager for Scene 3, in which ChangeBounds and Fade
        // take place at the same time.
        mTransitionManagerForScene3 = TransitionInflater.from(activity)
            .inflateTransitionManager(R.transition.scene3_transition_manager, mSceneRoot)
        // END_INCLUDE(custom_transition_manager)

        return view
    }

    override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
        when (checkedId) {
            R.id.select_scene_1 -> {
                // BEGIN_INCLUDE(transition_simple)
                // You can start an automatic transition with TransitionManager.go().
                TransitionManager.go(mScene1)
            } // END_INCLUDE(transition_simple)
            R.id.select_scene_2 -> {
                TransitionManager.go(mScene2)
            }
            R.id.select_scene_3 -> {
                // BEGIN_INCLUDE(transition_custom)
                // You can also start a transition with a custom TransitionManager.
                mTransitionManagerForScene3?.transitionTo(mScene3)
            } // END_INCLUDE(transition_custom)
            R.id.select_scene_4 -> {
                // BEGIN_INCLUDE(transition_dynamic)
                // Alternatively, transition can be invoked dynamically without a Scene.
                // For this, we first call TransitionManager.beginDelayedTransition().
                TransitionManager.beginDelayedTransition(mSceneRoot)
                // Then, we can just change view properties as usual.
                val square = mSceneRoot?.findViewById<View>(R.id.transition_square)
                square?.let {
                    val params = it.layoutParams
                    val newSize = resources.getDimensionPixelSize(R.dimen.w_100)
                    params?.width = newSize
                    params?.height = newSize
                    it.layoutParams = params
                }
            } // END_INCLUDE(transition_dynamic)
        }
    }
}
