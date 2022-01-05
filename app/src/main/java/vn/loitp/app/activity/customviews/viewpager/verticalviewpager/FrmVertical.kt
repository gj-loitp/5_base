package vn.loitp.app.activity.customviews.viewpager.verticalviewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_view_pager_vertical.*
import vn.loitp.app.R

class FrmVertical : Fragment() {

    companion object {

        private const val TEXT = "text"

        fun newInstance(data: String): FrmVertical {
            val fragment = FrmVertical()
            val bundle = Bundle(1)
            bundle.putString(TEXT, data)
            fragment.arguments = bundle
            return fragment
        }
    }

    var text: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        text = arguments?.getString(TEXT) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_pager_vertical, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv.text = text
    }
}
