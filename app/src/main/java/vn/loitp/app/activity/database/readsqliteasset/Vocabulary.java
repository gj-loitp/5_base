package vn.loitp.app.activity.database.readsqliteasset;

/**
 * Created by Loitp on 5/2/2017.
 */

public class Vocabulary {
    private boolean isClose;
    private int id;
    private String sword, sphonetic, smeanings, ssummary;
    private int sisoxfordlist;

    public Vocabulary() {

    }

    public Vocabulary(int id, String sword, String sphonetic, String smeanings, String ssummary, int sisoxfordlist, boolean isClose) {
        this.id = id;
        this.sword = sword;
        this.sphonetic = sphonetic;
        this.smeanings = smeanings;
        this.ssummary = ssummary;
        this.sisoxfordlist = sisoxfordlist;
        this.isClose = isClose;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSword() {
        return sword;
    }

    public void setSword(String sword) {
        this.sword = sword;
    }

    public String getSphonetic() {
        return sphonetic;
    }

    public void setSphonetic(String sphonetic) {
        this.sphonetic = sphonetic;
    }

    public String getSmeanings() {
        return smeanings;
    }

    public void setSmeanings(String smeanings) {
        this.smeanings = smeanings;
    }

    public String getSsummary() {
        return ssummary;
    }

    public void setSsummary(String ssummary) {
        this.ssummary = ssummary;
    }

    public int getSisoxfordlist() {
        return sisoxfordlist;
    }

    public void setSisoxfordlist(int sisoxfordlist) {
        this.sisoxfordlist = sisoxfordlist;
    }

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean close) {
        isClose = close;
    }
}
