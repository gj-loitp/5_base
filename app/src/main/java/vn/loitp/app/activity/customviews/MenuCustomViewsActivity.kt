package vn.loitp.app.activity.customviews

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.annotation.LayoutId
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
import vn.loitp.app.activity.customviews.compas.CompasActivity
import vn.loitp.app.activity.customviews.dialog.DialogMenuActivity
import vn.loitp.app.activity.customviews.draggableflipview.DraggableFlipViewActivity
import vn.loitp.app.activity.customviews.edittext.EditTextMenuActivity
import vn.loitp.app.activity.customviews.facebookcomment.FacebookCommentActivity
import vn.loitp.app.activity.customviews.imageview.ImageViewMenuActivity
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
import vn.loitp.app.activity.customviews.wwlmusic.WWLActivityMusic
import vn.loitp.app.activity.customviews.wwlvideo.WWLVideoActivity

@LayoutId(R.layout.activity_custom_view_menu)
@LogTag("MenuCustomViewsActivity")
class MenuCustomViewsActivity : BaseFontActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        btCompass.setOnClickListener(this)
        btScrollablePanel.setOnClickListener(this)
        btFbCmt.setOnClickListener(this)
        btWwlMusic.setOnClickListener(this)
        btWwlVideo.setOnClickListener(this)
        btLDebugView.setOnClickListener(this)
        btMenu.setOnClickListener(this)
        btLCardView.setOnClickListener(this)
        btCalendar.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btViewPager -> intent = Intent(activity, ViewPagerMenuActivity::class.java)
            btButton -> intent = Intent(activity, ButtonMenuActivity::class.java)
            btProgressLoading -> intent = Intent(activity, MenuProgressLoadingViewsActivity::class.java)
            btSwitch -> intent = Intent(activity, SwitchToggleMenuActivity::class.java)
            btActionBar -> intent = Intent(activity, ActionbarMenuActivity::class.java)
            btImageView -> intent = Intent(activity, ImageViewMenuActivity::class.java)
            btTextView -> intent = Intent(activity, TextViewMenuActivity::class.java)
            btBottomBarBlur -> intent = Intent(activity, BottomNavigationMenuActivity::class.java)
            btSticker -> intent = Intent(activity, StickerActivity::class.java)
            btEditText -> intent = Intent(activity, EditTextMenuActivity::class.java)
            btLayout -> intent = Intent(activity, LayoutMenuActivity::class.java)
            btVideoView -> intent = Intent(activity, VideoViewMenuActivity::class.java)
            btSeekBar -> intent = Intent(activity, SeekbarMenuActivity::class.java)
            btRecyclerView -> intent = Intent(activity, RecyclerViewMenuActivity::class.java)
            btDialog -> intent = Intent(activity, DialogMenuActivity::class.java)
            btPopupMenu -> intent = Intent(activity, PopupMenuActivity::class.java)
            btScratchView -> intent = Intent(activity, ScratchViewMenuActivity::class.java)
            btNavigation -> intent = Intent(activity, NavigationMenuActivity::class.java)
            btTreeView -> intent = Intent(activity, TreeViewActivity::class.java)
            btDraggableFlipView -> intent = Intent(activity, DraggableFlipViewActivity::class.java)
            btAnswerView -> intent = Intent(activity, AnswerViewActivity::class.java)
            btBottomSheet -> intent = Intent(activity, BottomSheetMenuActivity::class.java)
            btCompass -> intent = Intent(activity, CompasActivity::class.java)
            btScrollablePanel -> intent = Intent(activity, ScrollablePanelActivity::class.java)
            btFbCmt -> intent = Intent(activity, FacebookCommentActivity::class.java)
            btWwlMusic -> intent = Intent(activity, WWLActivityMusic::class.java)
            btWwlVideo -> intent = Intent(activity, WWLVideoActivity::class.java)
            btLDebugView -> intent = Intent(activity, LDebugViewActivity::class.java)
            btMenu -> intent = Intent(activity, MenuMenuActivity::class.java)
            btLCardView -> intent = Intent(activity, LCardViewActivity::class.java)
            btCalendar -> intent = Intent(activity, CalendarMenuActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(activity)
        }
    }
}
