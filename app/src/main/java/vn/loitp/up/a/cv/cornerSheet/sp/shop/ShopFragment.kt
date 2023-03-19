package vn.loitp.up.a.cv.cornerSheet.sp.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.github.heyalex.CornerDrawer
import com.github.heyalex.cornersheet.behavior.CornerSheetHeaderBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.f_shop.*
import vn.loitp.R
import vn.loitp.up.a.cv.cornerSheet.sp.ShopActivity

class ShopFragment : Fragment() {

    private lateinit var behavior: CornerSheetHeaderBehavior<CornerDrawer>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.f_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        behavior =
            (activity as ShopActivity).supportFragment.behavior as CornerSheetHeaderBehavior<CornerDrawer>

        toolbar.setOnApplyWindowInsetsListener { v, insets ->
            v.updatePadding(top = insets.systemWindowInsetTop)
            insets
        }

        toolbar.setNavigationOnClickListener {
            requireActivity().finish()
        }

        view.findViewById<RecyclerView>(R.id.shop_recyclerview).apply {
            adapter = ShopAdapter(object : ShopItemClickListener {
                override fun onClick(image: View, text: View, shopItemId: Long) {
                    if (behavior.state != BottomSheetBehavior.STATE_COLLAPSED) return

                    val manager: FragmentManager = requireActivity().supportFragmentManager
                    val currentFragment: Fragment = manager.findFragmentByTag("shop")!!
                    val intoContainerId = currentFragment.id

                    manager.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack("shared")
                        .add(
                            intoContainerId,
                            DetailShopFragment.newInstance(shopItemId),
                            "detail"
                        )
                        .commit()
                }
            }).also {
                it.submitList(items)
            }
            clipToPadding = false

            setPadding(
                /* left = */ 0,
                /* top = */ 0,
                /* right = */ 0,
                /* bottom = */ behavior.peekHeight
            )

            behavior.peekHeightListener = object : CornerSheetHeaderBehavior.OnPeekHeightListener {
                override fun onPeekHeightChanged(newPeekHeight: Int) {
                    setPadding(
                        /* left = */ 0,
                        /* top = */ 0,
                        /* right = */ 0,
                        /* bottom = */ newPeekHeight
                    )
                }
            }
        }
    }

}
