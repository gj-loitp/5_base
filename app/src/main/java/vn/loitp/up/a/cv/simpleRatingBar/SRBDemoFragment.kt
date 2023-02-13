package vn.loitp.up.a.cv.simpleRatingBar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.loitp.core.ext.d
import com.loitp.core.ext.setSafeOnClickListener
import com.willy.ratingbar.BaseRatingBar
import vn.loitp.databinding.FSrbDemoBinding

class SRBDemoFragment : Fragment() {

    companion object {
        const val TAG = "SimpleRatingBar"
    }

    private lateinit var binding: FSrbDemoBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FSrbDemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        binding.baseRatingBarMain.isClearRatingEnabled = false
        binding.baseRatingBarMain.setOnRatingChangeListener { _: BaseRatingBar?, rating: Float, _: Boolean ->
            d(TAG, "BaseRatingBar onRatingChange: $rating")
        }
        binding.scaleRatingBar.setOnRatingChangeListener { _: BaseRatingBar?, rating: Float, _: Boolean ->
            d(TAG, "ScaleRatingBar onRatingChange: $rating")
        }
        binding.rotationRatingBarMain.setOnRatingChangeListener { _: BaseRatingBar?, rating: Float, _: Boolean ->
            d(TAG, "RotationRatingBar onRatingChange: $rating")
        }
        binding.buttonMainAddRating.setSafeOnClickListener {
            var currentRating = binding.baseRatingBarMain.rating
            binding.baseRatingBarMain.rating = currentRating + 0.25f
            currentRating = binding.scaleRatingBar.rating
            binding.scaleRatingBar.rating = currentRating + 0.25f
            currentRating = binding.rotationRatingBarMain.rating
            binding.rotationRatingBarMain.rating = currentRating + 0.25f
        }
    }

}
