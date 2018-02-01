package vn.loitp.app.data;

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

    private List<Photoset> photosetListManga;

    public List<Photoset> getPhotosetListManga() {
        return photosetListManga;
    }

    public void setPhotosetListManga(List<Photoset> photosetListManga) {
        this.photosetListManga = photosetListManga;
    }

    /*private boolean isUseStrechImageView;

    public boolean isUseStrechImageView() {
        return isUseStrechImageView;
    }

    public void setUseStrechImageView(boolean useStrechImageView) {
        isUseStrechImageView = useStrechImageView;
    }*/

    public void clearAll() {
        photosetListCategory = null;
        photosetListManga = null;
    }
}
