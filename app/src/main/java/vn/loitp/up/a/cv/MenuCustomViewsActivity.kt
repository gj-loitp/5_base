package vn.loitp.up.a.cv

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.a.cv.ab.MenuActionBarActivity
import vn.loitp.a.cv.androidRibbon.RibbonActivity
import vn.loitp.a.cv.answerView.AnswerViewActivity
import vn.loitp.a.cv.bb.MenuBottomNavigationActivity
import vn.loitp.a.cv.bs.MenuBottomSheetActivity
import vn.loitp.a.cv.bt.MenuButtonActivity
import vn.loitp.a.cv.cal.MenuCalendarActivity
import vn.loitp.a.cv.code.CodeViewActivity
import vn.loitp.a.cv.cornerSheet.CornetSheetExampleActivity
import vn.loitp.a.cv.cv.CardViewActivity
import vn.loitp.a.cv.dlg.MenuDialogActivity
import vn.loitp.a.cv.dragView.MenuDragViewActivity
import vn.loitp.a.cv.draggableFlipView.DraggableFlipViewActivity
import vn.loitp.a.cv.et.MenuEditTextActivity
import vn.loitp.a.cv.fancyShowcase.FancyShowcaseActivity
import vn.loitp.a.cv.fbCmt.FacebookCommentActivity
import vn.loitp.a.cv.fingerPaintView.FingerPaintActivity
import vn.loitp.a.cv.graph.MenuGraphViewActivity
import vn.loitp.a.cv.indicator.ex.MenuMagicIndicatorActivityFont
import vn.loitp.a.cv.lCv.LCardViewActivity
import vn.loitp.a.cv.lDebug.LDebugViewActivity
import vn.loitp.a.cv.layout.MenuLayoutActivity
import vn.loitp.a.cv.luckyWheel.LuckyWheelActivity
import vn.loitp.a.cv.menu.MenuMenuActivity
import vn.loitp.a.cv.navi.MenuNavigationActivity
import vn.loitp.a.cv.popupMenu.PopupMenuActivity
import vn.loitp.databinding.AMenuCustomViewBinding
import vn.loitp.up.a.cv.progress.MenuProgressActivity
import vn.loitp.up.a.cv.rv.MenuRecyclerViewActivity
import vn.loitp.up.a.cv.sb.MenuSeekbarActivity
import vn.loitp.up.a.cv.scratchView.MenuScratchViewActivity
import vn.loitp.up.a.cv.scrollablePanel.ScrollablePanelActivity
import vn.loitp.up.a.cv.simpleRatingBar.SimpleRatingBarActivity
import vn.loitp.up.a.cv.spotlight.SpotlightActivity
import vn.loitp.up.a.cv.stackExpandableView.StackExpandableViewActivity
import vn.loitp.up.a.cv.sticker.StickerActivity
import vn.loitp.up.a.cv.sw.MenuSwitchToggleActivity
import vn.loitp.up.a.cv.treeView.TreeViewActivity
import vn.loitp.up.a.cv.tv.MenuTextViewActivity
import vn.loitp.up.a.cv.video.MenuVideoViewActivity
import vn.loitp.up.a.cv.vp.MenuViewPagerActivity
import vn.loitp.up.a.cv.wheelSpiner.WheelSpinnerActivity
import vn.loitp.up.a.cv.wheelView.WheelViewActivity
import vn.loitp.up.a.cv.wv.MenuWebViewActivity
import vn.loitp.up.a.cv.wwlMusic.WWLActivityMusic
import vn.loitp.up.a.cv.wwlVideo.WWLVideoActivity

@LogTag("MenuCustomViewsActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuCustomViewsActivity : BaseActivityFont(), OnClickListener {
    private lateinit var binding: AMenuCustomViewBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuCustomViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuCustomViewsActivity::class.java.simpleName
        }
        binding.btViewPager.setOnClickListener(this)
        binding.btButton.setOnClickListener(this)
        binding.btProgressLoading.setOnClickListener(this)
        binding.btSwitch.setOnClickListener(this)
        binding.btActionBar.setOnClickListener(this)
        binding.btImageView.setOnClickListener(this)
        binding.btTextView.setOnClickListener(this)
        binding.btBottomBarBlur.setOnClickListener(this)
        binding.btSticker.setOnClickListener(this)
        binding.btLayout.setOnClickListener(this)
        binding.btEditText.setOnClickListener(this)
        binding.btVideoView.setOnClickListener(this)
        binding.btSeekBar.setOnClickListener(this)
        binding.btRecyclerView.setOnClickListener(this)
        binding.btDialog.setOnClickListener(this)
        binding.btPopupMenu.setOnClickListener(this)
        binding.btScratchView.setOnClickListener(this)
        binding.btNavigation.setOnClickListener(this)
        binding.btTreeView.setOnClickListener(this)
        binding.btDraggableFlipView.setOnClickListener(this)
        binding.btDragView.setOnClickListener(this)
        binding.btAnswerView.setOnClickListener(this)
        binding.btBottomSheet.setOnClickListener(this)
        binding.btScrollablePanel.setOnClickListener(this)
        binding.btFbCmt.setOnClickListener(this)
        binding.btWwlMusic.setOnClickListener(this)
        binding.btWwlVideo.setOnClickListener(this)
        binding.btLDebugView.setOnClickListener(this)
        binding.btMenu.setOnClickListener(this)
        binding.btLCardView.setOnClickListener(this)
        binding.btCalendar.setOnClickListener(this)
        binding.btWebView.setOnClickListener(this)
        binding.btIndicator.setOnClickListener(this)
        binding.btWheelSpinner.setOnClickListener(this)
        binding.btFingerPaintView.setOnClickListener(this)
        binding.btStackExpandableViewActivity.setOnClickListener(this)
        binding.btMenuGraphViewActivity.setOnClickListener(this)
        binding.btSimpleRatingBar.setOnClickListener(this)
        binding.btCodeView.setOnClickListener(this)
        binding.btWheelView.setOnClickListener(this)
        binding.btLuckyWheelActivity.setOnClickListener(this)
        binding.btCornerSheet.setOnClickListener(this)
        binding.btCardView.setOnClickListener(this)
        binding.btAndroidRibbon.setOnClickListener(this)
        binding.btSpotlight.setOnClickListener(this)
        binding.btFancyShowcase.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btViewPager -> launchActivity(MenuViewPagerActivity::class.java)
            binding.btButton -> launchActivity(MenuButtonActivity::class.java)
            binding.btProgressLoading -> launchActivity(MenuProgressActivity::class.java)
            binding.btSwitch -> launchActivity(MenuSwitchToggleActivity::class.java)
            binding.btActionBar -> launchActivity(MenuActionBarActivity::class.java)
            binding.btImageView -> launchActivity(vn.loitp.a.cv.iv.MenuImageViewActivity::class.java)
            binding.btTextView -> launchActivity(MenuTextViewActivity::class.java)
            binding.btBottomBarBlur -> launchActivity(MenuBottomNavigationActivity::class.java)
            binding.btSticker -> launchActivity(StickerActivity::class.java)
            binding.btEditText -> launchActivity(MenuEditTextActivity::class.java)
            binding.btLayout -> launchActivity(MenuLayoutActivity::class.java)
            binding.btVideoView -> launchActivity(MenuVideoViewActivity::class.java)
            binding.btSeekBar -> launchActivity(MenuSeekbarActivity::class.java)
            binding.btRecyclerView -> launchActivity(MenuRecyclerViewActivity::class.java)
            binding.btDialog -> launchActivity(MenuDialogActivity::class.java)
            binding.btPopupMenu -> launchActivity(PopupMenuActivity::class.java)
            binding.btScratchView -> launchActivity(MenuScratchViewActivity::class.java)
            binding.btNavigation -> launchActivity(MenuNavigationActivity::class.java)
            binding.btTreeView -> launchActivity(TreeViewActivity::class.java)
            binding.btDraggableFlipView -> launchActivity(DraggableFlipViewActivity::class.java)
            binding.btDragView -> launchActivity(MenuDragViewActivity::class.java)
            binding.btAnswerView -> launchActivity(AnswerViewActivity::class.java)
            binding.btBottomSheet -> launchActivity(MenuBottomSheetActivity::class.java)
            binding.btScrollablePanel -> launchActivity(ScrollablePanelActivity::class.java)
            binding.btFbCmt -> launchActivity(FacebookCommentActivity::class.java)
            binding.btWwlMusic -> launchActivity(WWLActivityMusic::class.java)
            binding.btWwlVideo -> launchActivity(WWLVideoActivity::class.java)
            binding.btLDebugView -> launchActivity(LDebugViewActivity::class.java)
            binding.btMenu -> launchActivity(MenuMenuActivity::class.java)
            binding.btLCardView -> launchActivity(LCardViewActivity::class.java)
            binding.btCalendar -> launchActivity(MenuCalendarActivity::class.java)
            binding.btWebView -> launchActivity(MenuWebViewActivity::class.java)
            binding.btIndicator -> launchActivity(MenuMagicIndicatorActivityFont::class.java)
            binding.btWheelSpinner -> launchActivity(WheelSpinnerActivity::class.java)
            binding.btFingerPaintView -> launchActivity(FingerPaintActivity::class.java)
            binding.btStackExpandableViewActivity -> launchActivity(StackExpandableViewActivity::class.java)
            binding.btMenuGraphViewActivity -> launchActivity(MenuGraphViewActivity::class.java)
            binding.btSimpleRatingBar -> launchActivity(SimpleRatingBarActivity::class.java)
            binding.btCodeView -> launchActivity(CodeViewActivity::class.java)
            binding.btWheelView -> launchActivity(WheelViewActivity::class.java)
            binding.btLuckyWheelActivity -> launchActivity(LuckyWheelActivity::class.java)
            binding.btCornerSheet -> launchActivity(CornetSheetExampleActivity::class.java)
            binding.btCardView -> launchActivity(CardViewActivity::class.java)
            binding.btAndroidRibbon -> launchActivity(RibbonActivity::class.java)
            binding.btSpotlight -> launchActivity(SpotlightActivity::class.java)
            binding.btFancyShowcase -> launchActivity(FancyShowcaseActivity::class.java)
        }

    }
}
