package vn.loitp.app.a.picker.imagePicker

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitp.core.base.BaseFragment
import com.loitp.core.ext.setSafeOnClickListener
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.model.ImagePickerConfig
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker
import kotlinx.android.synthetic.main.fragment_main.*
import vn.loitp.R

class MainFragment : BaseFragment() {

    private var config: ImagePickerConfig? = null
    private var adapter: ImageAdapter? = null
    private var images = ArrayList<Image>()

    private val launcher = registerImagePicker {
        adapter?.setData(it)
    }

    companion object {
        const val EXTRA_CONFIG = "Config"
        fun newInstance(config: ImagePickerConfig?): MainFragment {
            val fragment = MainFragment()
            val args = Bundle()
            args.putParcelable(EXTRA_CONFIG, config)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        config = requireArguments().getParcelable(EXTRA_CONFIG)
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.fragment_main
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ImageAdapter(requireActivity())
        recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        pickImageButton.setSafeOnClickListener {
            config?.let {
                launcher.launch(it)
            }
        }
    }
}
