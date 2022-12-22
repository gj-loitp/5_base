package vn.loitp.app.a.cv.indicator.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.frm_test_magic_indicator.*
import vn.loitp.R

class TestFragment : Fragment() {
    companion object {
        const val EXTRA_TEXT = "extra_text"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frm_test_magic_indicator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            textView.text = it.getString(EXTRA_TEXT)
        }
    }
}
