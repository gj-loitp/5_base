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
import kotlinx.android.synthetic.main.f_detail_shop_item.*
import kotlinx.android.synthetic.main.f_detail_shop_item.view.*
import vn.loitp.R
import vn.loitp.up.a.cv.cornerSheet.sp.ShopActivityFont

class DetailShopFragment : Fragment() {

    private lateinit var behavior: CornerSheetHeaderBehavior<CornerDrawer>

    override fun onDetach() {
        super.onDetach()
        behavior.horizontalState = CornerSheetBehavior.STATE_COLLAPSED
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        behavior =
            (activity as ShopActivityFont).supportFragment.behavior as CornerSheetHeaderBehavior<CornerDrawer>

        behavior.horizontalState = CornerSheetBehavior.STATE_EXPANDED
        return inflater.inflate(R.layout.f_detail_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = getShopItem(requireArguments().getLong(shopId))
        view.detail_shop_image.load(item.url)
        view.name.text = item.name
        view.desc.text = item.description
        toolbar.setOnApplyWindowInsetsListener { v, insets ->
            toolbar.updatePadding(top = insets.systemWindowInsetTop)
            insets
        }
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
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
