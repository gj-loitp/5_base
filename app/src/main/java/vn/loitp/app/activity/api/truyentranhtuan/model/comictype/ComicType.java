package vn.loitp.app.activity.api.truyentranhtuan.model.comictype;

/**
 * Created by HOME on 4/13/2017.
 */

public class ComicType {
    public final static String TAT_CA = "Tất cả";
    public final static String TOP_50 = "Top 50";
    public final static String DANG_TIEN_HANH = "Đang tiến hành";
    public final static String TAM_NGUNG = "Tạm ngừng";
    public final static String HOAN_THANH = "Hoàn thành";
    public final static String ACTION = "Action";
    public final static String ADVENTURE = "Adventure";
    public final static String ANIME = "Anime";
    public final static String COMEDY = "Comedy";
    public final static String COMIC = "Comic";
    public final static String DRAMA = "Drama";
    public final static String FANTASY = "Fantasy";
    public final static String GENDER_BENDER = "Gender bender";
    public final static String HISTORICAL = "Historical";
    public final static String HORROR = "Horror";
    public final static String JOSEI = "Josei";
    public final static String LIVE_ACTION = "Live action";
    public final static String MANHUA = "Manhua";
    public final static String MANHWA = "Manhwa";
    public final static String MARTIAL_ARTS = "Martial arts";
    public final static String MECHA = "Mecha";
    public final static String MYSTERY = "Mystery";
    public final static String ONESHOT = "Oneshot";
    public final static String PSYCHOLOGICAL = "Psychological";
    public final static String ROMANCE = "Romance";
    public final static String SHCOOL_LIFE = "School life";
    public final static String SCIFI = "Scifi";
    public final static String SHOUJO = "Shoujo";
    public final static String SHOUNEN = "Shounen";
    public final static String SLICE_OF_LIFE = "Slice oflife";
    public final static String SMUT = "Smut";
    public final static String SPORTS = "Sports";
    public final static String SUPERNATURAL = "Supernatural";
    public final static String TRAGEDY = "Tragedy";
    public final static String TRUYEN_SCAN = "Truyện scan";
    public final static String TRUYEN_TRANH_VIET_NAM = "Truyện tranh Việt Nam";
    public final static String WEBTOON = "Webtoon";

    private String url;
    private String type;

    public ComicType(String type, String url) {
        this.type = type;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
