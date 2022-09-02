package vn.loitp.app.activity.customviews

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_custom_view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.actionbar.MenuActionBarActivity
import vn.loitp.app.activity.customviews.answerView.AnswerViewActivity
import vn.loitp.app.activity.customviews.bottomNavigationBar.MenuBottomNavigationActivity
import vn.loitp.app.activity.customviews.bottomSheet.MenuBottomSheetActivity
import vn.loitp.app.activity.customviews.button.MenuButtonActivity
import vn.loitp.app.activity.customviews.calendar.MenuCalendarActivity
import vn.loitp.app.activity.customviews.dialog.MenuDialogActivity
import vn.loitp.app.activity.customviews.dragView.MenuDragViewActivity
import vn.loitp.app.activity.customviews.draggableFlipView.DraggableFlipViewActivity
import vn.loitp.app.activity.customviews.edittext.MenuEditTextActivity
import vn.loitp.app.activity.customviews.facebookComment.FacebookCommentActivity
import vn.loitp.app.activity.customviews.fingerPaintView.FingerPaintActivity
import vn.loitp.app.activity.customviews.graphView.MenuGraphViewActivity
import vn.loitp.app.activity.customviews.imageview.MenuImageViewActivity
import vn.loitp.app.activity.customviews.indicator.example.MenuMagicIndicatorActivity
import vn.loitp.app.activity.customviews.lCardView.LCardViewActivity
import vn.loitp.app.activity.customviews.lDebugView.LDebugViewActivity
import vn.loitp.app.activity.customviews.layout.MenuLayoutActivity
import vn.loitp.app.activity.customviews.menu.MenuMenuActivity
import vn.loitp.app.activity.customviews.navigation.MenuNavigationActivity
import vn.loitp.app.activity.customviews.popupMenu.PopupMenuActivity
import vn.loitp.app.activity.customviews.progressLoadingView.MenuProgressLoadingViewsActivity
import vn.loitp.app.activity.customviews.recyclerview.MenuRecyclerViewActivity
import vn.loitp.app.activity.customviews.scratchView.MenuScratchViewActivity
import vn.loitp.app.activity.customviews.scrollablePanel.ScrollablePanelActivity
import vn.loitp.app.activity.customviews.seekBar.MenuSeekbarActivity
import vn.loitp.app.activity.customviews.simpleRatingBar.EntryActivity
import vn.loitp.app.activity.customviews.stackExpandableView.StackExpandableViewActivity
import vn.loitp.app.activity.customviews.sticker.StickerActivity
import vn.loitp.app.activity.customviews.switchToggle.MenuSwitchToggleActivity
import vn.loitp.app.activity.customviews.textview.MenuTextViewActivity
import vn.loitp.app.activity.customviews.treeView.TreeViewActivity
import vn.loitp.app.activity.customviews.videoView.MenuVideoViewActivity
import vn.loitp.app.activity.customviews.viewPager.MenuViewPagerActivity
import vn.loitp.app.activity.customviews.webview.LWebViewActivity
import vn.loitp.app.activity.customviews.wheelSpiner.WheelSpinnerActivity
import vn.loitp.app.activity.customviews.wwlMusic.WWLActivityMusic
import vn.loitp.app.activity.customviews.wwlVideo.WWLVideoActivity

@LogTag("MenuCustomViewsActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
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
                    onBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
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
    }

    override fun onClick(v: View) {
        val intent: Intent? = when (v) {
            btViewPager -> Intent(this, MenuViewPagerActivity::class.java)
            btButton -> Intent(this, MenuButtonActivity::class.java)
            btProgressLoading -> Intent(this, MenuProgressLoadingViewsActivity::class.java)
            btSwitch -> Intent(this, MenuSwitchToggleActivity::class.java)
            btActionBar -> Intent(this, MenuActionBarActivity::class.java)
            btImageView -> Intent(this, MenuImageViewActivity::class.java)
            btTextView -> Intent(this, MenuTextViewActivity::class.java)
            btBottomBarBlur -> Intent(this, MenuBottomNavigationActivity::class.java)
            btSticker -> Intent(this, StickerActivity::class.java)
            btEditText -> Intent(this, MenuEditTextActivity::class.java)
            btLayout -> Intent(this, MenuLayoutActivity::class.java)
            btVideoView -> Intent(this, MenuVideoViewActivity::class.java)
            btSeekBar -> Intent(this, MenuSeekbarActivity::class.java)
            btRecyclerView -> Intent(this, MenuRecyclerViewActivity::class.java)
            btDialog -> Intent(this, MenuDialogActivity::class.java)
            btPopupMenu -> Intent(this, PopupMenuActivity::class.java)
            btScratchView -> Intent(this, MenuScratchViewActivity::class.java)
            btNavigation -> Intent(this, MenuNavigationActivity::class.java)
            btTreeView -> Intent(this, TreeViewActivity::class.java)
            btDraggableFlipView -> Intent(this, DraggableFlipViewActivity::class.java)
            btDragView -> Intent(this, MenuDragViewActivity::class.java)
            btAnswerView -> Intent(this, AnswerViewActivity::class.java)
            btBottomSheet -> Intent(this, MenuBottomSheetActivity::class.java)
            btScrollablePanel -> Intent(this, ScrollablePanelActivity::class.java)
            btFbCmt -> Intent(this, FacebookCommentActivity::class.java)
            btWwlMusic -> Intent(this, WWLActivityMusic::class.java)
            btWwlVideo -> Intent(this, WWLVideoActivity::class.java)
            btLDebugView -> Intent(this, LDebugViewActivity::class.java)
            btMenu -> Intent(this, MenuMenuActivity::class.java)
            btLCardView -> Intent(this, LCardViewActivity::class.java)
            btCalendar -> Intent(this, MenuCalendarActivity::class.java)
            btWebView -> Intent(this, LWebViewActivity::class.java)
            btIndicator -> Intent(this, MenuMagicIndicatorActivity::class.java)
            btWheelSpinner -> Intent(this, WheelSpinnerActivity::class.java)
            btFingerPaintView -> Intent(this, FingerPaintActivity::class.java)
            btStackExpandableViewActivity -> Intent(this, StackExpandableViewActivity::class.java)
            btMenuGraphViewActivity -> Intent(this, MenuGraphViewActivity::class.java)
            btSimpleRatingBar -> Intent(this, EntryActivity::class.java)
            else -> null
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
