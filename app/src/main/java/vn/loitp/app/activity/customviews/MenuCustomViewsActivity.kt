package vn.loitp.app.activity.customviews

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
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
import vn.loitp.app.activity.customviews.edittext.EditTextMenuActivity
import vn.loitp.app.activity.customviews.facebookcomment.FacebookCommentActivity
import vn.loitp.app.activity.customviews.imageview.ImageViewMenuActivity
import vn.loitp.app.activity.customviews.indicator.example.ExampleMainActivity
import vn.loitp.app.activity.customviews.layout.LayoutMenuActivity
import vn.loitp.app.activity.customviews.lcardview.LCardViewActivity
import vn.loitp.app.activity.customviews.ldebugview.LDebugViewActivity
import vn.loitp.app.activity.customviews.menu.MenuMenuActivity
import vn.loitp.app.activity.customviews.navigation.NavigationMenuActivity
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
import vn.loitp.app.activity.customviews.wwlmusic.WWLActivityMusic
import vn.loitp.app.activity.customviews.wwlvideo.WWLVideoActivity

@LogTag("MenuCustomViewsActivity")
@IsFullScreen(false)
class MenuCustomViewsActivity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_custom_view_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
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
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btViewPager -> intent = Intent(this, ViewPagerMenuActivity::class.java)
            btButton -> intent = Intent(this, ButtonMenuActivity::class.java)
            btProgressLoading -> intent = Intent(this, MenuProgressLoadingViewsActivity::class.java)
            btSwitch -> intent = Intent(this, SwitchToggleMenuActivity::class.java)
            btActionBar -> intent = Intent(this, ActionbarMenuActivity::class.java)
            btImageView -> intent = Intent(this, ImageViewMenuActivity::class.java)
            btTextView -> intent = Intent(this, TextViewMenuActivity::class.java)
            btBottomBarBlur -> intent = Intent(this, BottomNavigationMenuActivity::class.java)
            btSticker -> intent = Intent(this, StickerActivity::class.java)
            btEditText -> intent = Intent(this, EditTextMenuActivity::class.java)
            btLayout -> intent = Intent(this, LayoutMenuActivity::class.java)
            btVideoView -> intent = Intent(this, VideoViewMenuActivity::class.java)
            btSeekBar -> intent = Intent(this, SeekbarMenuActivity::class.java)
            btRecyclerView -> intent = Intent(this, RecyclerViewMenuActivity::class.java)
            btDialog -> intent = Intent(this, DialogMenuActivity::class.java)
            btPopupMenu -> intent = Intent(this, PopupMenuActivity::class.java)
            btScratchView -> intent = Intent(this, ScratchViewMenuActivity::class.java)
            btNavigation -> intent = Intent(this, NavigationMenuActivity::class.java)
            btTreeView -> intent = Intent(this, TreeViewActivity::class.java)
            btDraggableFlipView -> intent = Intent(this, DraggableFlipViewActivity::class.java)
            btAnswerView -> intent = Intent(this, AnswerViewActivity::class.java)
            btBottomSheet -> intent = Intent(this, BottomSheetMenuActivity::class.java)
            btScrollablePanel -> intent = Intent(this, ScrollablePanelActivity::class.java)
            btFbCmt -> intent = Intent(this, FacebookCommentActivity::class.java)
            btWwlMusic -> intent = Intent(this, WWLActivityMusic::class.java)
            btWwlVideo -> intent = Intent(this, WWLVideoActivity::class.java)
            btLDebugView -> intent = Intent(this, LDebugViewActivity::class.java)
            btMenu -> intent = Intent(this, MenuMenuActivity::class.java)
            btLCardView -> intent = Intent(this, LCardViewActivity::class.java)
            btCalendar -> intent = Intent(this, CalendarMenuActivity::class.java)
            btWebView -> intent = Intent(this, LWebViewActivity::class.java)
            btIndicator -> intent = Intent(this, ExampleMainActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
