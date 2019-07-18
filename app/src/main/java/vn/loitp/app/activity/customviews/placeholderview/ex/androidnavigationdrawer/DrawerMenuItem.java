package vn.loitp.app.activity.customviews.placeholderview.ex.androidnavigationdrawer;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.views.LToast;
import com.views.placeholderview.annotations.Click;
import com.views.placeholderview.annotations.Layout;
import com.views.placeholderview.annotations.Position;
import com.views.placeholderview.annotations.Resolve;
import com.views.placeholderview.annotations.View;

import loitp.basemaster.R;

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

    private void showShort(String msg) {
        LToast.showShort(mContext, msg, R.drawable.bkg_horizontal);
    }

    @Click(R.id.mainView)
    private void onMenuItemClick() {
        switch (mMenuPosition) {
            case DRAWER_MENU_ITEM_PROFILE:
                showShort("Profile");
                if (mCallBack != null) mCallBack.onProfileMenuSelected();
                break;
            case DRAWER_MENU_ITEM_REQUESTS:
                showShort("Requests");
                if (mCallBack != null) mCallBack.onRequestMenuSelected();
                break;
            case DRAWER_MENU_ITEM_GROUPS:
                showShort("Groups");
                if (mCallBack != null) mCallBack.onGroupsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_MESSAGE:
                showShort("Messages");
                if (mCallBack != null) mCallBack.onMessagesMenuSelected();
                break;
            case DRAWER_MENU_ITEM_NOTIFICATIONS:
                showShort("Notifications");
                if (mCallBack != null) mCallBack.onNotificationsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_SETTINGS:
                showShort("Settings");
                if (mCallBack != null) mCallBack.onSettingsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_TERMS:
                showShort("Terms");
                if (mCallBack != null) mCallBack.onTermsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_LOGOUT:
                showShort("Logout");
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