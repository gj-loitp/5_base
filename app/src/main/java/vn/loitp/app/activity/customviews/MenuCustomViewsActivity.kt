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
import kotlinx.android.synthetic.main.activity_custom_view_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.actionbar.ActionbarMenuActivity
import vn.loitp.app.activity.customviews.answerview.AnswerViewActivity
import vn.loitp.app.activity.customviews.bottomnavigationbar.BottomNavigationMenuActivity
import vn.loitp.app.activity.customviews.bottomsheet.BottomSheetMenuActivity
import vn.loitp.app.activity.customviews.button.ButtonMenuActivity
import vn.loitp.app.activity.customviews.calendar.CalendarMenuActivity
import vn.loitp.app.activity.customviews.dialog.DialogMenuActivity
import vn.loitp.app.activity.customviews.draggableflipview.DraggableFlipViewActivity
import vn.loitp.app.activity.customviews.dragview.MenuDragViewActivity
import vn.loitp.app.activity.customviews.edittext.EditTextMenuActivity
import vn.loitp.app.activity.customviews.facebookcomment.FacebookCommentActivity
import vn.loitp.app.activity.customviews.imageview.ImageViewMenuActivity
import vn.loitp.app.activity.customviews.indicator.example.MagicIndicatorMenuActivity
import vn.loitp.app.activity.customviews.layout.MenuLayoutActivity
import vn.loitp.app.activity.customviews.lcardview.LCardViewActivity
import vn.loitp.app.activity.customviews.ldebugview.LDebugViewActivity
import vn.loitp.app.activity.customviews.menu.MenuMenuActivity
import vn.loitp.app.activity.customviews.navigation.MenuNavigationActivity
import vn.loitp.app.activity.customviews.popupmenu.PopupMenuActivity
import vn.loitp.app.activity.customviews.progressloadingview.MenuProgressLoadingViewsActivity
import vn.loitp.app.activity.customviews.recyclerview.RecyclerViewMenuActivity
import vn.loitp.app.activity.customviews.scratchview.ScratchViewMenuActivity
import vn.loitp.app.activity.customviews.scrollablepanel.ScrollablePanelActivity
import vn.loitp.app.activity.customviews.seekbar.SeekbarMenuActivity
import vn.loitp.app.activity.customviews.sticker.StickerActivity
import vn.loitp.app.activity.customviews.switchtoggle.SwitchToggleMenuActivity
import vn.loitp.app.activity.customviews.textview.TextViewMenuActivity
import vn.loitp.app.activity.customviews.treeview.TreeViewActivity
import vn.loitp.app.activity.customviews.videoview.VideoViewMenuActivity
import vn.loitp.app.activity.customviews.viewpager.ViewPagerMenuActivity
import vn.loitp.app.activity.customviews.webview.LWebViewActivity
import vn.loitp.app.activity.customviews.wheelspiner.WheelSpinnerActivity
import vn.loitp.app.activity.customviews.wwlmusic.WWLActivityMusic
import vn.loitp.app.activity.customviews.wwlvideo.WWLVideoActivity

@LogTag("MenuCustomViewsActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuCustomViewsActivity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_custom_view_menu
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
    }

    override fun onClick(v: View) {
        val intent: Intent? = when (v) {
            btViewPager -> Intent(this, ViewPagerMenuActivity::class.java)
            btButton -> Intent(this, ButtonMenuActivity::class.java)
            btProgressLoading -> Intent(this, MenuProgressLoadingViewsActivity::class.java)
            btSwitch -> Intent(this, SwitchToggleMenuActivity::class.java)
            btActionBar -> Intent(this, ActionbarMenuActivity::class.java)
            btImageView -> Intent(this, ImageViewMenuActivity::class.java)
            btTextView -> Intent(this, TextViewMenuActivity::class.java)
            btBottomBarBlur -> Intent(this, BottomNavigationMenuActivity::class.java)
            btSticker -> Intent(this, StickerActivity::class.java)
            btEditText -> Intent(this, EditTextMenuActivity::class.java)
            btLayout -> Intent(this, MenuLayoutActivity::class.java)
            btVideoView -> Intent(this, VideoViewMenuActivity::class.java)
            btSeekBar -> Intent(this, SeekbarMenuActivity::class.java)
            btRecyclerView -> Intent(this, RecyclerViewMenuActivity::class.java)
            btDialog -> Intent(this, DialogMenuActivity::class.java)
            btPopupMenu -> Intent(this, PopupMenuActivity::class.java)
            btScratchView -> Intent(this, ScratchViewMenuActivity::class.java)
            btNavigation -> Intent(this, MenuNavigationActivity::class.java)
            btTreeView -> Intent(this, TreeViewActivity::class.java)
            btDraggableFlipView -> Intent(this, DraggableFlipViewActivity::class.java)
            btDragView -> Intent(this, MenuDragViewActivity::class.java)
            btAnswerView -> Intent(this, AnswerViewActivity::class.java)
            btBottomSheet -> Intent(this, BottomSheetMenuActivity::class.java)
            btScrollablePanel -> Intent(this, ScrollablePanelActivity::class.java)
            btFbCmt -> Intent(this, FacebookCommentActivity::class.java)
            btWwlMusic -> Intent(this, WWLActivityMusic::class.java)
            btWwlVideo -> Intent(this, WWLVideoActivity::class.java)
            btLDebugView -> Intent(this, LDebugViewActivity::class.java)
            btMenu -> Intent(this, MenuMenuActivity::class.java)
            btLCardView -> Intent(this, LCardViewActivity::class.java)
            btCalendar -> Intent(this, CalendarMenuActivity::class.java)
            btWebView -> Intent(this, LWebViewActivity::class.java)
            btIndicator -> Intent(this, MagicIndicatorMenuActivity::class.java)
            btWheelSpinner -> Intent(this, WheelSpinnerActivity::class.java)
            else -> null
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
