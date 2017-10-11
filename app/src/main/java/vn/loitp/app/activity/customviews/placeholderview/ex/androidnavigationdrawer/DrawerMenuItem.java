package vn.loitp.app.activity.customviews.placeholderview.ex.androidnavigationdrawer;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import loitp.utils.util.ToastUtils;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.Click;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.Layout;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.Position;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.Resolve;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.View;
import vn.loitp.livestar.R;

/**
 * Created by janisharali on 19/08/16.
 */
@Layout(R.layout.drawer_item)
public class DrawerMenuItem {
    public static final int DRAWER_MENU_ITEM_PROFILE = 1;
    public static final int DRAWER_MENU_ITEM_REQUESTS = 2;
    public static final int DRAWER_MENU_ITEM_GROUPS = 3;
    public static final int DRAWER_MENU_ITEM_MESSAGE = 4;
    public static final int DRAWER_MENU_ITEM_NOTIFICATIONS = 5;
    public static final int DRAWER_MENU_ITEM_SETTINGS = 6;
    public static final int DRAWER_MENU_ITEM_TERMS = 7;
    public static final int DRAWER_MENU_ITEM_LOGOUT = 8;

    @Position
    private int mMenuPosition;

    private Context mContext;
    private DrawerCallBack mCallBack;

    @View(R.id.itemNameTxt)
    private TextView itemNameTxt;

    @View(R.id.itemIcon)
    private ImageView itemIcon;

    public DrawerMenuItem(Context context) {
        mContext = context;
    }

    @Resolve
    private void onResolved() {
        switch (mMenuPosition) {
            case DRAWER_MENU_ITEM_PROFILE:
                itemNameTxt.setText("Profile");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_launcher));
                break;
            case DRAWER_MENU_ITEM_REQUESTS:
                itemNameTxt.setText("Requests");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_launcher));
                break;
            case DRAWER_MENU_ITEM_GROUPS:
                itemNameTxt.setText("Groups");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_launcher));
                break;
            case DRAWER_MENU_ITEM_MESSAGE:
                itemNameTxt.setText("Messages");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_launcher));
                break;
            case DRAWER_MENU_ITEM_NOTIFICATIONS:
                itemNameTxt.setText("Notifications");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_launcher));
                break;
            case DRAWER_MENU_ITEM_SETTINGS:
                itemNameTxt.setText("Settings");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_launcher));
                break;
            case DRAWER_MENU_ITEM_TERMS:
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_launcher));
                itemNameTxt.setText("Terms");
                break;
            case DRAWER_MENU_ITEM_LOGOUT:
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_launcher));
                itemNameTxt.setText("Logout");
                break;
        }
    }

    @Click(R.id.mainView)
    private void onMenuItemClick() {
        switch (mMenuPosition) {
            case DRAWER_MENU_ITEM_PROFILE:
                ToastUtils.showShort("Profile");
                if (mCallBack != null) mCallBack.onProfileMenuSelected();
                break;
            case DRAWER_MENU_ITEM_REQUESTS:
                ToastUtils.showShort("Requests");
                if (mCallBack != null) mCallBack.onRequestMenuSelected();
                break;
            case DRAWER_MENU_ITEM_GROUPS:
                ToastUtils.showShort("Groups");
                if (mCallBack != null) mCallBack.onGroupsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_MESSAGE:
                ToastUtils.showShort("Messages");
                if (mCallBack != null) mCallBack.onMessagesMenuSelected();
                break;
            case DRAWER_MENU_ITEM_NOTIFICATIONS:
                ToastUtils.showShort("Notifications");
                if (mCallBack != null) mCallBack.onNotificationsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_SETTINGS:
                ToastUtils.showShort("Settings");
                if (mCallBack != null) mCallBack.onSettingsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_TERMS:
                ToastUtils.showShort("Terms");
                if (mCallBack != null) mCallBack.onTermsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_LOGOUT:
                ToastUtils.showShort("Logout");
                if (mCallBack != null) mCallBack.onLogoutMenuSelected();
                break;
        }
    }

    public void setDrawerCallBack(DrawerCallBack callBack) {
        mCallBack = callBack;
    }

    public interface DrawerCallBack {
        void onProfileMenuSelected();

        void onRequestMenuSelected();

        void onGroupsMenuSelected();

        void onMessagesMenuSelected();

        void onNotificationsMenuSelected();

        void onSettingsMenuSelected();

        void onTermsMenuSelected();

        void onLogoutMenuSelected();
    }
}