package vn.loitp.a.cv

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_menu_custom_view.*
import vn.loitp.R
import vn.loitp.a.cv.ab.MenuActionBarActivityFont
import vn.loitp.a.cv.androidRibbon.RibbonActivityFont
import vn.loitp.a.cv.answerView.AnswerViewActivityFont
import vn.loitp.a.cv.bb.MenuBottomNavigationActivityFont
import vn.loitp.a.cv.bs.MenuBottomSheetActivityFont
import vn.loitp.a.cv.bt.MenuButtonActivityFont
import vn.loitp.a.cv.cal.MenuCalendarActivityFont
import vn.loitp.a.cv.code.CodeViewActivityFont
import vn.loitp.a.cv.cornerSheet.CornetSheetExampleActivityFont
import vn.loitp.a.cv.cv.CardViewActivityFont
import vn.loitp.a.cv.dlg.MenuDialogActivityFont
import vn.loitp.a.cv.dragView.MenuDragViewActivityFont
import vn.loitp.a.cv.draggableFlipView.DraggableFlipViewActivityFont
import vn.loitp.a.cv.et.MenuEditTextActivityFont
import vn.loitp.a.cv.fancyShowcase.FancyShowcaseActivity
import vn.loitp.a.cv.fbCmt.FacebookCommentActivityFont
import vn.loitp.a.cv.fingerPaintView.FingerPaintActivityFont
import vn.loitp.a.cv.graph.MenuGraphViewActivityFont
import vn.loitp.a.cv.indicator.ex.MenuMagicIndicatorActivityFont
import vn.loitp.a.cv.lCv.LCardViewActivityFont
import vn.loitp.a.cv.lDebug.LDebugViewActivity
import vn.loitp.a.cv.layout.MenuLayoutActivityFont
import vn.loitp.a.cv.luckyWheel.LuckyWheelActivityFont
import vn.loitp.a.cv.menu.MenuMenuActivityFont
import vn.loitp.a.cv.navi.MenuNavigationActivityFont
import vn.loitp.a.cv.popupMenu.PopupMenuActivityFont
import vn.loitp.a.cv.progress.MenuProgressActivityFont
import vn.loitp.a.cv.rv.MenuRecyclerViewActivity
import vn.loitp.a.cv.sb.MenuSeekbarActivityFont
import vn.loitp.a.cv.scratchView.MenuScratchViewActivityFont
import vn.loitp.a.cv.scrollablePanel.ScrollablePanelActivityFont
import vn.loitp.a.cv.simpleRatingBar.SimpleRatingBarActivityFont
import vn.loitp.a.cv.spotlight.SpotlightActivityFont
import vn.loitp.a.cv.stackExpandableView.StackExpandableViewActivityFont
import vn.loitp.a.cv.sticker.StickerActivityFont
import vn.loitp.a.cv.sw.MenuSwitchToggleActivityFont
import vn.loitp.a.cv.treeView.TreeViewActivityFont
import vn.loitp.a.cv.video.MenuVideoViewActivityFont
import vn.loitp.a.cv.wheelSpiner.WheelSpinnerActivityFont
import vn.loitp.a.cv.wheelView.WheelViewActivityFont
import vn.loitp.a.cv.wv.MenuWebViewActivityFont
import vn.loitp.a.cv.wwlMusic.WWLActivityMusicFont
import vn.loitp.a.cv.wwlVideo.WWLVideoActivity
import vn.loitp.app.a.cv.tv.MenuTextViewActivityFont
import vn.loitp.app.a.cv.vp.MenuViewPagerActivityFont

@LogTag("MenuCustomViewsActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuCustomViewsActivity : BaseActivityFont(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_custom_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuCustomViewsActivity::class.java.simpleName
        }
        btViewPager.setOnClickListener(this)
        btButton.setOnClickListener(this)
        btProgressLoading.setOnClickListener(this)
        btSwitch.setOnClickListener(this)
        btActionBar.setOnClickListener(this)
        btImageView.setOnClickListener(this)
        btTextView.setOnClickListener(this)
        btBottomBarBlur.setOnClickListener(this)
        btSticker.setOnClickListener(this)
        btLayout.setOnClickListener(this)
        btEditText.setOnClickListener(this)
        btVideoView.setOnClickListener(this)
        btSeekBar.setOnClickListener(this)
        btRecyclerView.setOnClickListener(this)
        btDialog.setOnClickListener(this)
        btPopupMenu.setOnClickListener(this)
        btScratchView.setOnClickListener(this)
        btNavigation.setOnClickListener(this)
        btTreeView.setOnClickListener(this)
        btDraggableFlipView.setOnClickListener(this)
        btDragView.setOnClickListener(this)
        btAnswerView.setOnClickListener(this)
        btBottomSheet.setOnClickListener(this)
        btScrollablePanel.setOnClickListener(this)
        btFbCmt.setOnClickListener(this)
        btWwlMusic.setOnClickListener(this)
        btWwlVideo.setOnClickListener(this)
        btLDebugView.setOnClickListener(this)
        btMenu.setOnClickListener(this)
        btLCardView.setOnClickListener(this)
        btCalendar.setOnClickListener(this)
        btWebView.setOnClickListener(this)
        btIndicator.setOnClickListener(this)
        btWheelSpinner.setOnClickListener(this)
        btFingerPaintView.setOnClickListener(this)
        btStackExpandableViewActivity.setOnClickListener(this)
        btMenuGraphViewActivity.setOnClickListener(this)
        btSimpleRatingBar.setOnClickListener(this)
        btCodeView.setOnClickListener(this)
        btWheelView.setOnClickListener(this)
        btLuckyWheelActivity.setOnClickListener(this)
        btCornerSheet.setOnClickListener(this)
        btCardView.setOnClickListener(this)
        btAndroidRibbon.setOnClickListener(this)
        btSpotlight.setOnClickListener(this)
        btFancyShowcase.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btViewPager -> launchActivity(MenuViewPagerActivityFont::class.java)
            btButton -> launchActivity(MenuButtonActivityFont::class.java)
            btProgressLoading -> launchActivity(MenuProgressActivityFont::class.java)
            btSwitch -> launchActivity(MenuSwitchToggleActivityFont::class.java)
            btActionBar -> launchActivity(MenuActionBarActivityFont::class.java)
            btImageView -> launchActivity(vn.loitp.a.cv.iv.MenuImageViewActivityFont::class.java)
            btTextView -> launchActivity(MenuTextViewActivityFont::class.java)
            btBottomBarBlur -> launchActivity(MenuBottomNavigationActivityFont::class.java)
            btSticker -> launchActivity(StickerActivityFont::class.java)
            btEditText -> launchActivity(MenuEditTextActivityFont::class.java)
            btLayout -> launchActivity(MenuLayoutActivityFont::class.java)
            btVideoView -> launchActivity(MenuVideoViewActivityFont::class.java)
            btSeekBar -> launchActivity(MenuSeekbarActivityFont::class.java)
            btRecyclerView -> launchActivity(MenuRecyclerViewActivity::class.java)
            btDialog -> launchActivity(MenuDialogActivityFont::class.java)
            btPopupMenu -> launchActivity(PopupMenuActivityFont::class.java)
            btScratchView -> launchActivity(MenuScratchViewActivityFont::class.java)
            btNavigation -> launchActivity(MenuNavigationActivityFont::class.java)
            btTreeView -> launchActivity(TreeViewActivityFont::class.java)
            btDraggableFlipView -> launchActivity(DraggableFlipViewActivityFont::class.java)
            btDragView -> launchActivity(MenuDragViewActivityFont::class.java)
            btAnswerView -> launchActivity(AnswerViewActivityFont::class.java)
            btBottomSheet -> launchActivity(MenuBottomSheetActivityFont::class.java)
            btScrollablePanel -> launchActivity(ScrollablePanelActivityFont::class.java)
            btFbCmt -> launchActivity(FacebookCommentActivityFont::class.java)
            btWwlMusic -> launchActivity(WWLActivityMusicFont::class.java)
            btWwlVideo -> launchActivity(WWLVideoActivity::class.java)
            btLDebugView -> launchActivity(LDebugViewActivity::class.java)
            btMenu -> launchActivity(MenuMenuActivityFont::class.java)
            btLCardView -> launchActivity(LCardViewActivityFont::class.java)
            btCalendar -> launchActivity(MenuCalendarActivityFont::class.java)
            btWebView -> launchActivity(MenuWebViewActivityFont::class.java)
            btIndicator -> launchActivity(MenuMagicIndicatorActivityFont::class.java)
            btWheelSpinner -> launchActivity(WheelSpinnerActivityFont::class.java)
            btFingerPaintView -> launchActivity(FingerPaintActivityFont::class.java)
            btStackExpandableViewActivity -> launchActivity(StackExpandableViewActivityFont::class.java)
            btMenuGraphViewActivity -> launchActivity(MenuGraphViewActivityFont::class.java)
            btSimpleRatingBar -> launchActivity(SimpleRatingBarActivityFont::class.java)
            btCodeView -> launchActivity(CodeViewActivityFont::class.java)
            btWheelView -> launchActivity(WheelViewActivityFont::class.java)
            btLuckyWheelActivity -> launchActivity(LuckyWheelActivityFont::class.java)
            btCornerSheet -> launchActivity(CornetSheetExampleActivityFont::class.java)
            btCardView -> launchActivity(CardViewActivityFont::class.java)
            btAndroidRibbon -> launchActivity(RibbonActivityFont::class.java)
            btSpotlight -> launchActivity(SpotlightActivityFont::class.java)
            btFancyShowcase -> launchActivity(FancyShowcaseActivity::class.java)
        }

    }
}
