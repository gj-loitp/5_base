package vn.loitp.up.a.cv.iv.pinchToZoom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.loitp.core.ext.loadGlide
import vn.loitp.databinding.FIvPinchToZoomBinding

class FrmIvPinchToZoom : Fragment() {

    companion object {
        const val KEY_URL = "KEY_URL"
    }

    private lateinit var binding: FIvPinchToZoomBinding
    private var urlIv: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FIvPinchToZoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        urlIv = arguments?.getString(KEY_URL)
        urlIv?.let { u ->
            binding.imageView.loadGlide(any = u)
        }
    }
}
