package vn.loitp.app.activity.api.truyentranhtuan.helper;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic;
import vn.loitp.app.activity.api.truyentranhtuan.model.comictype.ComicType;
import vn.loitp.app.common.Constants;

/**
 * Created by www.muathu@gmail.com on 11/2/2017.
 */

public class ComicUtils {
    public static List<ComicType> getComicTypeList() {
        List<ComicType> comicTypeList = new ArrayList<>();
        comicTypeList.add(new ComicType(ComicType.TAT_CA, Constants.MAIN_LINK_TRUYENTRANHTUAN));
        comicTypeList.add(new ComicType(ComicType.TOP_50, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/top/top-50"));
        comicTypeList.add(new ComicType(ComicType.DANG_TIEN_HANH, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/trang-thai/dang-tien-hanh"));
        comicTypeList.add(new ComicType(ComicType.TAM_NGUNG, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/trang-thai/tam-dung"));
        comicTypeList.add(new ComicType(ComicType.HOAN_THANH, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/trang-thai/hoan-thanh/"));
        comicTypeList.add(new ComicType(ComicType.ACTION, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/action"));
        comicTypeList.add(new ComicType(ComicType.ADVENTURE, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/adventure"));
        comicTypeList.add(new ComicType(ComicType.ANIME, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/anime"));
        comicTypeList.add(new ComicType(ComicType.COMEDY, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/comedy"));
        comicTypeList.add(new ComicType(ComicType.COMIC, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/comic"));
        comicTypeList.add(new ComicType(ComicType.DRAMA, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/drama"));
        comicTypeList.add(new ComicType(ComicType.FANTASY, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/fantasy"));
        comicTypeList.add(new ComicType(ComicType.GENDER_BENDER, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/gender-bender"));
        comicTypeList.add(new ComicType(ComicType.HISTORICAL, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/historical"));
        comicTypeList.add(new ComicType(ComicType.HORROR, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/horror"));
        comicTypeList.add(new ComicType(ComicType.JOSEI, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/josei"));
        comicTypeList.add(new ComicType(ComicType.LIVE_ACTION, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/live-action"));
        comicTypeList.add(new ComicType(ComicType.MANHUA, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/manhua"));
        comicTypeList.add(new ComicType(ComicType.MANHWA, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/manhwa"));
        comicTypeList.add(new ComicType(ComicType.MARTIAL_ARTS, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/martial-arts"));
        comicTypeList.add(new ComicType(ComicType.MECHA, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/mecha"));
        comicTypeList.add(new ComicType(ComicType.MYSTERY, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/mystery"));
        comicTypeList.add(new ComicType(ComicType.ONESHOT, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/one-shot"));
        comicTypeList.add(new ComicType(ComicType.PSYCHOLOGICAL, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/psychological"));
        comicTypeList.add(new ComicType(ComicType.ROMANCE, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/romance"));
        comicTypeList.add(new ComicType(ComicType.SHCOOL_LIFE, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/school-life"));
        comicTypeList.add(new ComicType(ComicType.SCIFI, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/sci-fi"));
        comicTypeList.add(new ComicType(ComicType.SHOUJO, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/shoujo"));
        comicTypeList.add(new ComicType(ComicType.SHOUNEN, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/shounen"));
        comicTypeList.add(new ComicType(ComicType.SLICE_OF_LIFE, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/slice-of-life"));
        comicTypeList.add(new ComicType(ComicType.SMUT, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/smut"));
        comicTypeList.add(new ComicType(ComicType.SPORTS, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/sports"));
        comicTypeList.add(new ComicType(ComicType.SUPERNATURAL, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/supernatural"));
        comicTypeList.add(new ComicType(ComicType.TRAGEDY, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/tragedy"));
        comicTypeList.add(new ComicType(ComicType.TRUYEN_SCAN, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/truyen-scan"));
        comicTypeList.add(new ComicType(ComicType.TRUYEN_TRANH_VIET_NAM, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/truyen-tranh-viet-nam"));
        comicTypeList.add(new ComicType(ComicType.WEBTOON, Constants.MAIN_LINK_TRUYENTRANHTUAN + "/the-loai/webtoon"));
        return comicTypeList;
    }

    public static void addComicToFavList(Comic comic) {

    }

    public static boolean isComicExistIn(Comic comic, List<Comic> comicList) {
        if (comic == null || comicList == null) {
            throw new NullPointerException("isComicExistIn comic == null || comicList == null");
        }
        for (int i = 0; i < comicList.size(); i++) {
            if (comic.getUrl().trim().equals(comicList.get(i).getUrl().trim())) {
                return true;
            }
        }
        return false;
    }

    public static int isComicExistAt(Comic comic, List<Comic> comicList) {
        if (comic == null || comicList == null) {
            throw new NullPointerException("isComicExistIn comic == null || comicList == null");
        }
        for (int i = 0; i < comicList.size(); i++) {
            if (comic.getUrl().trim().equals(comicList.get(i).getUrl().trim())) {
                return i;
            }
        }
        return Constants.NOT_FOUND;
    }
}
