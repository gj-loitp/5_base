package vn.loitp.up.a.cv.vp.auto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import vn.loitp.databinding.FIvBinding

class FrmIv : Fragment() {

    companion object {
        fun newInstance(): FrmIv {
            return FrmIv()
        }
    }

    private lateinit var binding: FIvBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FIvBinding.inflate(inflater, container, false)
        return binding.root
    }
}
