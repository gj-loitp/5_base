package vn.loitp.app.activity.customviews

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_menu_custom_view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.actionbar.ActionbarMenuActivity
import vn.loitp.app.activity.customviews.answerview.AnswerViewActivity
import vn.loitp.app.activity.customviews.ariana.ArianaMenuActivity
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

class MenuCustomViewsActivity : BaseFontActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_ariana).setOnClickListener(this)
        findViewById<View>(R.id.bt_view_pager).setOnClickListener(this)
        findViewById<View>(R.id.bt_button).setOnClickListener(this)
        findViewById<View>(R.id.bt_progress_loading).setOnClickListener(this)
        findViewById<View>(R.id.bt_switch).setOnClickListener(this)
        findViewById<View>(R.id.bt_action_bar).setOnClickListener(this)
        findViewById<View>(R.id.bt_imageview).setOnClickListener(this)
        findViewById<View>(R.id.bt_textview).setOnClickListener(this)
        findViewById<View>(R.id.bt_bottom_bar).setOnClickListener(this)
        findViewById<View>(R.id.bt_sticker).setOnClickListener(this)
        findViewById<View>(R.id.bt_layout).setOnClickListener(this)
        findViewById<View>(R.id.bt_edit_text).setOnClickListener(this)
        findViewById<View>(R.id.bt_videoview).setOnClickListener(this)
        findViewById<View>(R.id.bt_seekbar).setOnClickListener(this)
        findViewById<View>(R.id.bt_recyclerview).setOnClickListener(this)
        findViewById<View>(R.id.bt_dialog).setOnClickListener(this)
        findViewById<View>(R.id.bt_popup_menu).setOnClickListener(this)
        findViewById<View>(R.id.bt_scratchview).setOnClickListener(this)
        findViewById<View>(R.id.bt_navigation).setOnClickListener(this)
        findViewById<View>(R.id.bt_treeview).setOnClickListener(this)
        findViewById<View>(R.id.bt_draggable_flipview).setOnClickListener(this)
        findViewById<View>(R.id.bt_answer_view).setOnClickListener(this)
        findViewById<View>(R.id.bt_bottom_sheet).setOnClickListener(this)
        findViewById<View>(R.id.bt_compas).setOnClickListener(this)
        findViewById<View>(R.id.bt_scrollable_panel).setOnClickListener(this)
        findViewById<View>(R.id.bt_fb_cmt).setOnClickListener(this)
        findViewById<View>(R.id.bt_wwl_music).setOnClickListener(this)
        findViewById<View>(R.id.bt_wwl_video).setOnClickListener(this)
        findViewById<View>(R.id.bt_ldebugview).setOnClickListener(this)
        findViewById<View>(R.id.bt_menu).setOnClickListener(this)
        findViewById<View>(R.id.bt_l_card_view).setOnClickListener(this)
        btCalendar.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_custom_view
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_ariana -> intent = Intent(activity, ArianaMenuActivity::class.java)
            R.id.bt_view_pager -> intent = Intent(activity, ViewPagerMenuActivity::class.java)
            R.id.bt_button -> intent = Intent(activity, ButtonMenuActivity::class.java)
            R.id.bt_progress_loading -> intent = Intent(activity, MenuProgressLoadingViewsActivity::class.java)
            R.id.bt_switch -> intent = Intent(activity, SwitchToggleMenuActivity::class.java)
            R.id.bt_action_bar -> intent = Intent(activity, ActionbarMenuActivity::class.java)
            R.id.bt_imageview -> intent = Intent(activity, ImageViewMenuActivity::class.java)
            R.id.bt_textview -> intent = Intent(activity, TextViewMenuActivity::class.java)
            R.id.bt_bottom_bar -> intent = Intent(activity, BottomNavigationMenuActivity::class.java)
            R.id.bt_sticker -> intent = Intent(activity, StickerActivity::class.java)
            R.id.bt_edit_text -> intent = Intent(activity, EditTextMenuActivity::class.java)
            R.id.bt_layout -> intent = Intent(activity, LayoutMenuActivity::class.java)
            R.id.bt_videoview -> intent = Intent(activity, VideoViewMenuActivity::class.java)
            R.id.bt_seekbar -> intent = Intent(activity, SeekbarMenuActivity::class.java)
            R.id.bt_recyclerview -> intent = Intent(activity, RecyclerViewMenuActivity::class.java)
            R.id.bt_dialog -> intent = Intent(activity, DialogMenuActivity::class.java)
            R.id.bt_popup_menu -> intent = Intent(activity, PopupMenuActivity::class.java)
            R.id.bt_scratchview -> intent = Intent(activity, ScratchViewMenuActivity::class.java)
            R.id.bt_navigation -> intent = Intent(activity, NavigationMenuActivity::class.java)
            R.id.bt_treeview -> intent = Intent(activity, TreeViewActivity::class.java)
            R.id.bt_draggable_flipview -> intent = Intent(activity, DraggableFlipViewActivity::class.java)
            R.id.bt_answer_view -> intent = Intent(activity, AnswerViewActivity::class.java)
            R.id.bt_bottom_sheet -> intent = Intent(activity, BottomSheetMenuActivity::class.java)
            R.id.bt_compas -> intent = Intent(activity, CompasActivity::class.java)
            R.id.bt_scrollable_panel -> intent = Intent(activity, ScrollablePanelActivity::class.java)
            R.id.bt_fb_cmt -> intent = Intent(activity, FacebookCommentActivity::class.java)
            R.id.bt_wwl_music -> intent = Intent(activity, WWLActivityMusic::class.java)
            R.id.bt_wwl_video -> intent = Intent(activity, WWLVideoActivity::class.java)
            R.id.bt_ldebugview -> intent = Intent(activity, LDebugViewActivity::class.java)
            R.id.bt_menu -> intent = Intent(activity, MenuMenuActivity::class.java)
            R.id.bt_l_card_view -> intent = Intent(activity, LCardViewActivity::class.java)
            R.id.btCalendar -> intent = Intent(activity, CalendarMenuActivity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
