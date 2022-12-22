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
import kotlinx.android.synthetic.main.activity_menu_custom_view.*
import vn.loitp.R
import vn.loitp.a.cv.ab.MenuActionBarActivity
import vn.loitp.a.cv.androidRibbon.RibbonActivity
import vn.loitp.a.cv.answerView.AnswerViewActivity
import vn.loitp.a.cv.bb.MenuBottomNavigationActivity
import vn.loitp.a.cv.bs.MenuBottomSheetActivity
import vn.loitp.a.cv.bt.MenuButtonActivity
import vn.loitp.app.a.customviews.calendar.MenuCalendarActivity
import vn.loitp.app.a.customviews.cardView.CardViewActivity
import vn.loitp.app.a.customviews.codeView.CodeViewActivity
import vn.loitp.app.a.customviews.cornerSheet.CornetSheetExampleActivity
import vn.loitp.app.a.customviews.dialog.MenuDialogActivity
import vn.loitp.app.a.customviews.dragView.MenuDragViewActivity
import vn.loitp.app.a.customviews.draggableFlipView.DraggableFlipViewActivity
import vn.loitp.app.a.customviews.edittext.MenuEditTextActivity
import vn.loitp.app.a.customviews.facebookComment.FacebookCommentActivity
import vn.loitp.app.a.customviews.fingerPaintView.FingerPaintActivity
import vn.loitp.app.a.customviews.graphView.MenuGraphViewActivity
import vn.loitp.app.a.customviews.imageview.MenuImageViewActivity
import vn.loitp.app.a.customviews.indicator.example.MenuMagicIndicatorActivity
import vn.loitp.app.a.customviews.lCardView.LCardViewActivity
import vn.loitp.app.a.customviews.lDebugView.LDebugViewActivity
import vn.loitp.app.a.customviews.layout.MenuLayoutActivity
import vn.loitp.app.a.customviews.luckyWheel.LuckyWheelActivity
import vn.loitp.app.a.customviews.menu.MenuMenuActivity
import vn.loitp.app.a.customviews.navigation.MenuNavigationActivity
import vn.loitp.app.a.customviews.popupMenu.PopupMenuActivity
import vn.loitp.app.a.customviews.progress.MenuProgressLoadingViewsActivity
import vn.loitp.app.a.customviews.recyclerview.MenuRecyclerViewActivity
import vn.loitp.app.a.customviews.scratchView.MenuScratchViewActivity
import vn.loitp.app.a.customviews.scrollablePanel.ScrollablePanelActivity
import vn.loitp.app.a.customviews.seekBar.MenuSeekbarActivity
import vn.loitp.app.a.customviews.simpleRatingBar.SimpleRatingBarActivity
import vn.loitp.app.a.customviews.stackExpandableView.StackExpandableViewActivity
import vn.loitp.app.a.customviews.sticker.StickerActivity
import vn.loitp.app.a.customviews.switchToggle.MenuSwitchToggleActivity
import vn.loitp.app.a.customviews.textview.MenuTextViewActivity
import vn.loitp.app.a.customviews.treeView.TreeViewActivity
import vn.loitp.app.a.customviews.videoView.MenuVideoViewActivity
import vn.loitp.app.a.customviews.viewPager.MenuViewPagerActivity
import vn.loitp.app.a.customviews.webview.MenuWebViewActivity
import vn.loitp.app.a.customviews.wheelSpiner.WheelSpinnerActivity
import vn.loitp.app.a.customviews.wheelView.WheelViewActivity
import vn.loitp.app.a.customviews.wwlMusic.WWLActivityMusic
import vn.loitp.app.a.customviews.wwlVideo.WWLVideoActivity

@LogTag("MenuCustomViewsActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class MenuCustomViewsActivity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_custom_view
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
    }

    override fun onClick(v: View) {
        when (v) {
            btViewPager -> launchActivity(MenuViewPagerActivity::class.java)
            btButton -> launchActivity(MenuButtonActivity::class.java)
            btProgressLoading -> launchActivity(MenuProgressLoadingViewsActivity::class.java)
            btSwitch -> launchActivity(MenuSwitchToggleActivity::class.java)
            btActionBar -> launchActivity(MenuActionBarActivity::class.java)
            btImageView -> launchActivity(MenuImageViewActivity::class.java)
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
        }

    }
}
