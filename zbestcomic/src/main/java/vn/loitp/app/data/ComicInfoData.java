package vn.loitp.app.data;

import vn.loitp.app.model.chap.TTTChap;

/**
 * Created by www.muathu@gmail.com on 1/25/2018.
 */

public class ComicInfoData {
    private static final ComicInfoData ourInstance = new ComicInfoData();

    public static ComicInfoData getInstance() {
        return ourInstance;
    }

    private ComicInfoData() {
    }

    private TTTChap tttChap;

    public TTTChap getTttChap() {
        return tttChap;
    }

    public void setTttChap(TTTChap tttChap) {
        this.tttChap = tttChap;
    }

    public void clearAll() {
        tttChap = null;
    }
}
