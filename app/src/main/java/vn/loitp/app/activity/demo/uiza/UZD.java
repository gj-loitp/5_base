package vn.loitp.app.activity.demo.uiza;

import android.view.MenuItem;

public class UZD {
    private static final UZD ourInstance = new UZD();

    public static UZD getInstance() {
        return ourInstance;
    }

    private UZD() {
    }

    private MenuItem menuItem;

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }
}
