package vn.loitp.app.activity.customviews.bottomBar.expandableBottomBar.screens;

import android.graphics.Color;
import android.os.Bundle;

import com.loitpcore.annotation.IsAutoAnimation;
import com.loitpcore.annotation.IsFullScreen;
import com.loitpcore.annotation.LogTag;
import com.loitpcore.core.base.BaseFontActivity;

import github.com.st235.lib_expandablebottombar.ExpandableBottomBar;
import github.com.st235.lib_expandablebottombar.Menu;
import github.com.st235.lib_expandablebottombar.MenuItemDescriptor;
import vn.loitp.app.R;

@LogTag("CoordinatorLayoutActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
public class JavaActivity extends BaseFontActivity {

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_java;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExpandableBottomBar expandableBottomBar = findViewById(R.id.expandableBottomBar);
        Menu menu = expandableBottomBar.getMenu();

        menu.add(
                new MenuItemDescriptor.Builder(this, R.id.icon_home, R.drawable.ic_home, R.string.text, Color.GRAY).build()
        );

        menu.add(
                new MenuItemDescriptor.Builder(this, R.id.icon_likes, R.drawable.ic_likes, R.string.text2, 0xffff77a9).build()
        );

        menu.add(
                new MenuItemDescriptor.Builder(this, R.id.icon_bookmarks, R.drawable.ic_bookmarks, R.string.text3, 0xff58a5f0).build()
        );

        menu.add(
                new MenuItemDescriptor.Builder(this, R.id.icon_settings, R.drawable.ic_settings, R.string.text4, 0xffbe9c91).build()
        );

        expandableBottomBar.setOnItemSelectedListener((view, item, byUser) -> {
            return null;
        });

        expandableBottomBar.setOnItemReselectedListener((view, item, byUser) -> {
            return null;
        });
    }
}
