package vn.loitp.app.a.customviews.viewPager.viewPager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.frm_view_pager_2.*
import vn.loitp.R

class FrmViewPager2 : Fragment() {

    companion object {
        const val ARGS_KEY = "count"

        @JvmStatic
        fun getInstance(count: Int): FrmViewPager2 {
            val args = Bundle()
            args.putInt(ARGS_KEY, count)

            val frmViewPager2 = FrmViewPager2()
            frmViewPager2.arguments = args
            return frmViewPager2
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frm_view_pager_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        var count = -1
        if (arguments != null) {
            count = arguments?.getInt(ARGS_KEY, -1) ?: -1
        }
        if (count != -1) {
            tv.text = getString(R.string.app_name)
            for (i in 0 until count) {
                val imageView = ImageView(activity)
                imageView.setImageResource(R.drawable.ic_account_circle_black_48dp)
                ll.addView(imageView)
            }
        }
    }
}
