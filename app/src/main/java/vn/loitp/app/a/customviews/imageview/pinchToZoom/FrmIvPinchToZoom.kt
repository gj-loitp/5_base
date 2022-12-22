package vn.loitp.app.a.customviews.imageview.pinchToZoom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.loitp.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.frm_iv_pinch_to_zoom.*
import vn.loitp.R

class FrmIvPinchToZoom : Fragment() {

    companion object {
        const val KEY_URL = "KEY_URL"
    }

    private var urlIv: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frm_iv_pinch_to_zoom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        urlIv = arguments?.getString(KEY_URL)
        urlIv?.let { u ->
            LImageUtil.load(context = view.context, any = u, imageView = imageView)
        }
    }
}
