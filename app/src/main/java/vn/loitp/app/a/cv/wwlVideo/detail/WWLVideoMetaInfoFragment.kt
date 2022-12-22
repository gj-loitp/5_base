package vn.loitp.app.a.cv.wwlVideo.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.wwl_video_meta_info_fragment.*
import vn.loitp.R
import vn.loitp.app.a.cv.wwlVideo.utils.WWLVideoDataset

class WWLVideoMetaInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.wwl_video_meta_info_fragment, container, false)
    }

    fun updateItem(item: WWLVideoDataset.DatasetItem) {
        liTitle.text = item.title
        liSubtitle.text = item.subtitle
    }
}
