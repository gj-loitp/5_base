package vn.loitp.up.a.cv.vp.vp2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import vn.loitp.R
import vn.loitp.databinding.FVp2Binding

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

    private lateinit var binding: FVp2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FVp2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        var count = -1
        if (arguments != null) {
            count = arguments?.getInt(ARGS_KEY, -1) ?: -1
        }
        if (count != -1) {
            binding.tv.text = getString(R.string.app_name)
            for (i in 0 until count) {
                val imageView = ImageView(activity)
                imageView.setImageResource(R.drawable.ic_account_circle_black_48dp)
                binding.ll.addView(imageView)
            }
        }
    }
}
