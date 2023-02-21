package vn.loitp.up.a.cv.layout.draggablePanel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import vn.loitp.databinding.FTestBottomBinding

class FrmTestTop : Fragment() {

    companion object {
        fun newInstance(): FrmTestTop {
            return FrmTestTop()
        }
    }

    private lateinit var binding: FTestBottomBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FTestBottomBinding.inflate(inflater, container, false)
        return binding.root
    }
}
