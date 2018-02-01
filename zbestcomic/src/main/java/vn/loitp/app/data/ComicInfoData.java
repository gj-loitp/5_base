package vn.loitp.app.data;

import vn.loitp.app.model.chap.TTTChap;
import vn.loitp.core.utilities.LLog;

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
        posCurrentChap = 0;
    }

    private int posCurrentChap = 0;

    public int getPosCurrentChap() {
        return posCurrentChap;
    }

    public void setPosCurrentChap(int posCurrentChap) {
        if (posCurrentChap < 0 || posCurrentChap >= tttChap.getChaps().getChap().size()) {
            return;
        }
        this.posCurrentChap = posCurrentChap;
    }

    public String getCurrentLinkChap() {
        try {
            return tttChap.getChaps().getChap().get(posCurrentChap).getUrl();
        } catch (NullPointerException e) {
            return null;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
