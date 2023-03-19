package vn.loitp.up.a.cv.cornerSheet.sp.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.github.heyalex.CornerDrawer
import com.github.heyalex.cornersheet.behavior.CornerSheetBehavior
import com.github.heyalex.cornersheet.behavior.CornerSheetHeaderBehavior
import com.loitp.core.base.BaseActivity
import vn.loitp.databinding.FDetailShopItemBinding
import vn.loitp.up.a.cv.cornerSheet.sp.ShopActivity

class DetailShopFragment : Fragment() {

    private lateinit var behavior: CornerSheetHeaderBehavior<CornerDrawer>
    private lateinit var binding: FDetailShopItemBinding

    override fun onDetach() {
        super.onDetach()
        behavior.horizontalState = CornerSheetBehavior.STATE_COLLAPSED
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        behavior =
            (activity as ShopActivity).supportFragment.behavior as CornerSheetHeaderBehavior<CornerDrawer>

        behavior.horizontalState = CornerSheetBehavior.STATE_EXPANDED

        binding = FDetailShopItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = getShopItem(requireArguments().getLong(shopId))

        binding.detailShopImage.load(item.url)
        binding.name.text = item.name
        binding.desc.text = item.description
        binding.toolbar.setOnApplyWindowInsetsListener { v, insets ->
            binding.toolbar.updatePadding(top = insets.systemWindowInsetTop)
            insets
        }
        binding.toolbar.setNavigationOnClickListener {
            (requireActivity() as? BaseActivity)?.onBaseBackPressed()
        }
    }

    companion object {
        const val shopId = "shop_id"
        fun newInstance(id: Long): DetailShopFragment {
            val args = Bundle()
            args.putLong(shopId, id)
            val fragment = DetailShopFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
