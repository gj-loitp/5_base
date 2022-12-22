package vn.loitp.app.a.cv.layout.transformationLayout.single

import android.os.Bundle
import android.view.View
import com.loitp.core.base.BaseFragment
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.addTransformation
import com.skydoves.transformationlayout.onTransformationStartContainer
import kotlinx.android.synthetic.main.frm_transformation_home.*
import vn.loitp.R
import vn.loitp.app.a.cv.layout.transformationLayout.TransformationMockUtil.getMockPosters
import vn.loitp.app.a.cv.layout.transformationLayout.recycler.Poster
import vn.loitp.app.a.cv.layout.transformationLayout.recycler.PosterMenuAdapter
import vn.loitp.app.a.cv.layout.transformationLayout.recycler.PosterSingleAdapter

class TransformationSingleFragment : BaseFragment(), PosterSingleAdapter.PosterDelegate {

    companion object {
        const val TAG = "MainSingleFragment"
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_transformation_home
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // [Step1]: apply onTransformationStartContainer.
        onTransformationStartContainer()
    }

    /** This function will be called from the [PosterSingleAdapter.PosterDelegate]'s onBindViewHolder. */
    override fun onItemClick(poster: Poster, itemView: TransformationLayout) {
        val fragment = TransformationSingleDetailFragment()
        // [Step2]: getBundle from the TransformationLayout.
        val bundle = itemView.getBundle(TransformationSingleDetailFragment.paramsKey)
        bundle.putParcelable(TransformationSingleDetailFragment.posterKey, poster)
        fragment.arguments = bundle

        requireFragmentManager()
            .beginTransaction()
            // [Step3]: addTransformation using the TransformationLayout.
            .addTransformation(itemView)
            .replace(R.id.layoutContainer, fragment, TransformationSingleDetailFragment.TAG)
            .addToBackStack(TransformationSingleDetailFragment.TAG)
            .commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = PosterSingleAdapter(this).apply { addPosterList(getMockPosters()) }
        recyclerViewMenu.adapter = PosterMenuAdapter().apply { addPosterList(getMockPosters()) }

        fab.setOnClickListener {
            if (!transformationLayout.isTransforming) {
                backgroundView.visibility = View.VISIBLE
            }
            transformationLayout.startTransform()
        }

        menuHome.setOnClickListener {
            if (!transformationLayout.isTransforming) {
                backgroundView.visibility = View.GONE
            }
            transformationLayout.finishTransform()
        }
    }
}
