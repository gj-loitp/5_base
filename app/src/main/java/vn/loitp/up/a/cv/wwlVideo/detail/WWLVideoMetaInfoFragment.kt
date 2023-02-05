package vn.loitp.up.a.cv.wwlVideo.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import vn.loitp.databinding.FWwlVideoMetaInfoBinding
import vn.loitp.up.a.cv.wwlVideo.utils.WWLVideoDataset

class WWLVideoMetaInfoFragment : Fragment() {
    private lateinit var binding: FWwlVideoMetaInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FWwlVideoMetaInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun updateItem(item: WWLVideoDataset.DatasetItem) {
        binding.liTitle.text = item.title
        binding.liSubtitle.text = item.subtitle
    }
}
