package com.loitp.func.epub.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.loitpcore.R

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class PageFragment : Fragment() {

    companion object {
        private const val ARG_TAB_POSITION = "ARG_TAB_POSITION"

        fun newInstance(tabPosition: Int): PageFragment {
            val fragment = PageFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_TAB_POSITION, tabPosition)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var onFragmentReadyListener: OnFragmentReadyListener? = null

    interface OnFragmentReadyListener {
        fun onFragmentReady(position: Int): View?
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onFragmentReadyListener = context as OnFragmentReadyListener
    }

    override fun onDestroy() {
        super.onDestroy()
        onFragmentReadyListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.l_f_epub_reader, container, false)

        arguments?.let {
            val position = it.getInt(ARG_TAB_POSITION)
            val view = onFragmentReadyListener?.onFragmentReady(position)
            view?.let {
                val fragmentMainLayout =
                    rootView.findViewById<RelativeLayout>(R.id.fragmentMainLayout)
                fragmentMainLayout.addView(view)
            }
        }
        return rootView
    }
}
