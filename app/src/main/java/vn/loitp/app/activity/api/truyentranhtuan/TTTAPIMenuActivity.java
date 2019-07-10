package vn.loitp.app.activity.api.truyentranhtuan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.core.base.BaseFontActivity;
import com.core.utilities.LActivityUtil;

import loitp.basemaster.R;

public class TTTAPIMenuActivity extends BaseFontActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.bt_comic_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TTTAPIComicListActivity.class);
                startActivity(intent);
                LActivityUtil.INSTANCE.tranIn(activity);
            }
        });
        findViewById(R.id.bt_chap_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TTTAPIChapListActivity.class);
                startActivity(intent);
                LActivityUtil.INSTANCE.tranIn(activity);
            }
        });
        findViewById(R.id.bt_page_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TTTAPIPageListActivity.class);
                startActivity(intent);
                LActivityUtil.INSTANCE.tranIn(activity);
            }
        });
        findViewById(R.id.bt_fav_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TTTAPIFavListActivity.class);
                startActivity(intent);
                LActivityUtil.INSTANCE.tranIn(activity);
            }
        });
        findViewById(R.id.bt_add_to_fav_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TTTAPIAddFavListActivity.class);
                startActivity(intent);
                LActivityUtil.INSTANCE.tranIn(activity);
            }
        });
        findViewById(R.id.bt_remove_to_fav_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TTTAPIRemoveFavListActivity.class);
                startActivity(intent);
                LActivityUtil.INSTANCE.tranIn(activity);
            }
        });
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
        return R.layout.activity_api_ttt_menu;
    }

}
