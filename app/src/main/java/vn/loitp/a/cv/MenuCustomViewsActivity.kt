package vn.loitp.a.cv

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_menu_custom_view.*
import vn.loitp.R
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
import vn.loitp.a.cv.fbCmt.FacebookCommentActivity
import vn.loitp.a.cv.fingerPaintView.FingerPaintActivity
import vn.loitp.a.cv.graph.MenuGraphViewActivity
import vn.loitp.a.cv.indicator.ex.MenuMagicIndicatorActivity
import vn.loitp.a.cv.lCv.LCardViewActivity
import vn.loitp.a.cv.lDebug.LDebugViewActivity
import vn.loitp.a.cv.layout.MenuLayoutActivity
import vn.loitp.a.cv.luckyWheel.LuckyWheelActivity
import vn.loitp.a.cv.navi.MenuNavigationActivity
import vn.loitp.a.cv.popupMenu.PopupMenuActivity
import vn.loitp.a.cv.progress.MenuProgressActivity
import vn.loitp.a.cv.spotlight.SpotlightActivity
import vn.loitp.a.cv.menu.MenuMenuActivity
import vn.loitp.app.a.cv.rv.MenuRecyclerViewActivity
import vn.loitp.app.a.cv.sb.MenuSeekbarActivity
import vn.loitp.a.cv.scratchView.MenuScratchViewActivity
import vn.loitp.a.cv.scrollablePanel.ScrollablePanelActivity
import vn.loitp.app.a.cv.simpleRatingBar.SimpleRatingBarActivity
import vn.loitp.app.a.cv.stackExpandableView.StackExpandableViewActivity
import vn.loitp.app.a.cv.sticker.StickerActivity
import vn.loitp.app.a.cv.sw.MenuSwitchToggleActivity
import vn.loitp.app.a.cv.treeView.TreeViewActivity
import vn.loitp.app.a.cv.tv.MenuTextViewActivity
import vn.loitp.app.a.cv.video.MenuVideoViewActivity
import vn.loitp.app.a.cv.vp.MenuViewPagerActivity
import vn.loitp.app.a.cv.wheelSpiner.WheelSpinnerActivity
import vn.loitp.app.a.cv.wheelView.WheelViewActivity
import vn.loitp.app.a.cv.wv.MenuWebViewActivity
import vn.loitp.app.a.cv.wwlMusic.WWLActivityMusic
import vn.loitp.app.a.cv.wwlVideo.WWLVideoActivity

@LogTag("MenuCustomViewsActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuCustomViewsActivity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_custom_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
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
    }

    override fun onClick(v: View) {
        when (v) {
            btViewPager -> launchActivity(MenuViewPagerActivity::class.java)
            btButton -> launchActivity(MenuButtonActivity::class.java)
            btProgressLoading -> launchActivity(MenuProgressActivity::class.java)
            btSwitch -> launchActivity(MenuSwitchToggleActivity::class.java)
            btActionBar -> launchActivity(MenuActionBarActivity::class.java)
            btImageView -> launchActivity(vn.loitp.a.cv.iv.MenuImageViewActivity::class.java)
            btTextView -> launchActivity(MenuTextViewActivity::class.java)
            btBottomBarBlur -> launchActivity(MenuBottomNavigationActivity::class.java)
            btSticker -> launchActivity(StickerActivity::class.java)
            btEditText -> launchActivity(MenuEditTextActivity::class.java)
            btLayout -> launchActivity(MenuLayoutActivity::class.java)
            btVideoView -> launchActivity(MenuVideoViewActivity::class.java)
            btSeekBar -> launchActivity(MenuSeekbarActivity::class.java)
            btRecyclerView -> launchActivity(MenuRecyclerViewActivity::class.java)
            btDialog -> launchActivity(MenuDialogActivity::class.java)
            btPopupMenu -> launchActivity(PopupMenuActivity::class.java)
            btScratchView -> launchActivity(MenuScratchViewActivity::class.java)
            btNavigation -> launchActivity(MenuNavigationActivity::class.java)
            btTreeView -> launchActivity(TreeViewActivity::class.java)
            btDraggableFlipView -> launchActivity(DraggableFlipViewActivity::class.java)
            btDragView -> launchActivity(MenuDragViewActivity::class.java)
            btAnswerView -> launchActivity(AnswerViewActivity::class.java)
            btBottomSheet -> launchActivity(MenuBottomSheetActivity::class.java)
            btScrollablePanel -> launchActivity(ScrollablePanelActivity::class.java)
            btFbCmt -> launchActivity(FacebookCommentActivity::class.java)
            btWwlMusic -> launchActivity(WWLActivityMusic::class.java)
            btWwlVideo -> launchActivity(WWLVideoActivity::class.java)
            btLDebugView -> launchActivity(LDebugViewActivity::class.java)
            btMenu -> launchActivity(MenuMenuActivity::class.java)
            btLCardView -> launchActivity(LCardViewActivity::class.java)
            btCalendar -> launchActivity(MenuCalendarActivity::class.java)
            btWebView -> launchActivity(MenuWebViewActivity::class.java)
            btIndicator -> launchActivity(MenuMagicIndicatorActivity::class.java)
            btWheelSpinner -> launchActivity(WheelSpinnerActivity::class.java)
            btFingerPaintView -> launchActivity(FingerPaintActivity::class.java)
            btStackExpandableViewActivity -> launchActivity(StackExpandableViewActivity::class.java)
            btMenuGraphViewActivity -> launchActivity(MenuGraphViewActivity::class.java)
            btSimpleRatingBar -> launchActivity(SimpleRatingBarActivity::class.java)
            btCodeView -> launchActivity(CodeViewActivity::class.java)
            btWheelView -> launchActivity(WheelViewActivity::class.java)
            btLuckyWheelActivity -> launchActivity(LuckyWheelActivity::class.java)
            btCornerSheet -> launchActivity(CornetSheetExampleActivity::class.java)
            btCardView -> launchActivity(CardViewActivity::class.java)
            btAndroidRibbon -> launchActivity(RibbonActivity::class.java)
            btSpotlight -> launchActivity(SpotlightActivity::class.java)
        }

    }
}
