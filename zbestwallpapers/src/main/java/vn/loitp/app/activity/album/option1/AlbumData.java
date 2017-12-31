package vn.loitp.app.activity.album.option1;

import java.util.List;

import vn.loitp.restapi.flickr.model.photosetgetlist.Photoset;

/**
 * Created by www.muathu@gmail.com on 12/31/2017.
 */

public class AlbumData {
    private static final AlbumData ourInstance = new AlbumData();

    public static AlbumData getInstance() {
        return ourInstance;
    }

    private AlbumData() {
    }

    private List<Photoset> photosetListCategory;

    public List<Photoset> getPhotosetListCategory() {
        return photosetListCategory;
    }

    public void setPhotosetListCategory(List<Photoset> photosetListCategory) {
        this.photosetListCategory = photosetListCategory;
    }

    private List<Photoset> photosetListVietnamese;

    public List<Photoset> getPhotosetListVietnamese() {
        return photosetListVietnamese;
    }

    public void setPhotosetListVietnamese(List<Photoset> photosetListVietnamese) {
        this.photosetListVietnamese = photosetListVietnamese;
    }
}
