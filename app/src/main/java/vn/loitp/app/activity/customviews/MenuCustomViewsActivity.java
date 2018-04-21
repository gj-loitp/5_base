package vn.loitp.app.activity.customviews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.actionbar.ActionbarMenuActivity;
import vn.loitp.app.activity.customviews.answerview.AnswerViewActivity;
import vn.loitp.app.activity.customviews.ariana.ArianaMenuActivity;
import vn.loitp.app.activity.customviews.bottomnavigationbar.BottomNavigationMenuActivity;
import vn.loitp.app.activity.customviews.bottomsheet.BottomSheetMenuActivity;
import vn.loitp.app.activity.customviews.button.ButtonMenuActivity;
import vn.loitp.app.activity.customviews.dialog.DialogMenuActivity;
import vn.loitp.app.activity.customviews.draggableflipview.DraggableFlipViewActivity;
import vn.loitp.app.activity.customviews.edittext.EditTextMenuActivity;
import vn.loitp.app.activity.customviews.googleplusbutton.GooglePlusButtonActivity;
import vn.loitp.app.activity.customviews.imageview.ImageViewMenuActivity;
import vn.loitp.app.activity.customviews.keyword_hottags.KeywordHotagsActivity;
import vn.loitp.app.activity.customviews.layout.LayoutMenuActivity;
import vn.loitp.app.activity.customviews.navigation.NavigationMenuActivity;
import vn.loitp.app.activity.customviews.placeholderview.PlaceHolderViewMenuActivity;
import vn.loitp.app.activity.customviews.popupmenu.PopupMenuActivity;
import vn.loitp.app.activity.customviews.progressloadingview.MenuProgressLoadingViewsActivity;
import vn.loitp.app.activity.customviews.recyclerview.RecyclerViewMenuActivity;
import vn.loitp.app.activity.customviews.scratchview.ScratchViewMenuActivity;
import vn.loitp.app.activity.customviews.seekbar.SeekbarMenuActivity;
import vn.loitp.app.activity.customviews.sticker.StickerActivity;
import vn.loitp.app.activity.customviews.switchtoggle.SwitchToggleMenuActivity;
import vn.loitp.app.activity.customviews.textview.TextViewMenuActivity;
import vn.loitp.app.activity.customviews.treeview.TreeViewActivity;
import vn.loitp.app.activity.customviews.videoview.VideoViewMenuActivity;
import vn.loitp.app.activity.customviews.viewpager.ViewPagerMenuActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;

public class MenuCustomViewsActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_ariana).setOnClickListener(this);
        findViewById(R.id.bt_place_holder_view).setOnClickListener(this);
        findViewById(R.id.bt_view_pager).setOnClickListener(this);
        findViewById(R.id.bt_keyword_view).setOnClickListener(this);
        findViewById(R.id.bt_button).setOnClickListener(this);
        findViewById(R.id.bt_progress_loading).setOnClickListener(this);
        findViewById(R.id.bt_switch).setOnClickListener(this);
        findViewById(R.id.bt_action_bar).setOnClickListener(this);
        findViewById(R.id.bt_imageview).setOnClickListener(this);
        findViewById(R.id.bt_textview).setOnClickListener(this);
        findViewById(R.id.bt_bottom_bar).setOnClickListener(this);
        findViewById(R.id.bt_sticker).setOnClickListener(this);
        findViewById(R.id.bt_gg_plus_button).setOnClickListener(this);
        findViewById(R.id.bt_layout).setOnClickListener(this);
        findViewById(R.id.bt_edit_text).setOnClickListener(this);
        findViewById(R.id.bt_videoview).setOnClickListener(this);
        findViewById(R.id.bt_seekbar).setOnClickListener(this);
        findViewById(R.id.bt_recyclerview).setOnClickListener(this);
        findViewById(R.id.bt_dialog).setOnClickListener(this);
        findViewById(R.id.bt_popup_menu).setOnClickListener(this);
        findViewById(R.id.bt_scratchview).setOnClickListener(this);
        findViewById(R.id.bt_navigation).setOnClickListener(this);
        findViewById(R.id.bt_treeview).setOnClickListener(this);
        findViewById(R.id.bt_draggable_flipview).setOnClickListener(this);
        findViewById(R.id.bt_answer_view).setOnClickListener(this);
        findViewById(R.id.bt_bottom_sheet).setOnClickListener(this);
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_menu_custom_view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_ariana:
                intent = new Intent(activity, ArianaMenuActivity.class);
                break;
            case R.id.bt_place_holder_view:
                intent = new Intent(activity, PlaceHolderViewMenuActivity.class);
                break;
            case R.id.bt_view_pager:
                intent = new Intent(activity, ViewPagerMenuActivity.class);
                break;
            case R.id.bt_keyword_view:
                intent = new Intent(activity, KeywordHotagsActivity.class);
                break;
            case R.id.bt_button:
                intent = new Intent(activity, ButtonMenuActivity.class);
                break;
            case R.id.bt_progress_loading:
                intent = new Intent(activity, MenuProgressLoadingViewsActivity.class);
                break;
            case R.id.bt_switch:
                intent = new Intent(activity, SwitchToggleMenuActivity.class);
                break;
            case R.id.bt_action_bar:
                intent = new Intent(activity, ActionbarMenuActivity.class);
                break;
            case R.id.bt_imageview:
                intent = new Intent(activity, ImageViewMenuActivity.class);
                break;
            case R.id.bt_textview:
                intent = new Intent(activity, TextViewMenuActivity.class);
                break;
            case R.id.bt_bottom_bar:
                intent = new Intent(activity, BottomNavigationMenuActivity.class);
                break;
            case R.id.bt_sticker:
                intent = new Intent(activity, StickerActivity.class);
                break;
            case R.id.bt_gg_plus_button:
                intent = new Intent(activity, GooglePlusButtonActivity.class);
                break;
            case R.id.bt_edit_text:
                intent = new Intent(activity, EditTextMenuActivity.class);
                break;
            case R.id.bt_layout:
                intent = new Intent(activity, LayoutMenuActivity.class);
                break;
            case R.id.bt_videoview:
                intent = new Intent(activity, VideoViewMenuActivity.class);
                break;
            case R.id.bt_seekbar:
                intent = new Intent(activity, SeekbarMenuActivity.class);
                break;
            case R.id.bt_recyclerview:
                intent = new Intent(activity, RecyclerViewMenuActivity.class);
                break;
            case R.id.bt_dialog:
                intent = new Intent(activity, DialogMenuActivity.class);
                break;
            case R.id.bt_popup_menu:
                intent = new Intent(activity, PopupMenuActivity.class);
                break;
            case R.id.bt_scratchview:
                intent = new Intent(activity, ScratchViewMenuActivity.class);
                break;
            case R.id.bt_navigation:
                intent = new Intent(activity, NavigationMenuActivity.class);
                break;
            case R.id.bt_treeview:
                intent = new Intent(activity, TreeViewActivity.class);
                break;
            case R.id.bt_draggable_flipview:
                intent = new Intent(activity, DraggableFlipViewActivity.class);
                break;
            case R.id.bt_answer_view:
                intent = new Intent(activity, AnswerViewActivity.class);
                break;
            case R.id.bt_bottom_sheet:
                intent = new Intent(activity, BottomSheetMenuActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
