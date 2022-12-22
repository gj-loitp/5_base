package vn.loitp.app.a.customviews.layout.draggablePanel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.frm_test_bottom.*
import vn.loitp.R

class FrmTestBottom : Fragment() {

    companion object {
        fun newInstance(): FrmTestBottom {
            return FrmTestBottom()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frm_test_bottom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView.setImageResource(R.drawable.iv)
    }
}
