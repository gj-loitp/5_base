package vn.loitp.app.activity.customviews.viewPager.vertical

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.frm_view_pager_vertical.*
import vn.loitp.R

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
        return inflater.inflate(R.layout.frm_view_pager_vertical, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        tv.text = text
    }
}
