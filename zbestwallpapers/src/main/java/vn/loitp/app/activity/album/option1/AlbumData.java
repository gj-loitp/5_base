package vn.loitp.app.activity.album.option1;

import java.util.List;

import vn.loitp.restapi.flickr.model.photosetgetlist.Photoset;

/**
 * Created by www.muathu@gmail.com on 12/30/2017.
 */

public class AlbumData {
    private static final AlbumData ourInstance = new AlbumData();

    public static AlbumData getInstance() {
        return ourInstance;
    }

    private AlbumData() {
    }

    private List<Photoset> photosetList;

    public List<Photoset> getPhotosetList() {
        return photosetList;
    }

    public void setPhotosetList(List<Photoset> photosetList) {
        this.photosetList = photosetList;
    }
}
