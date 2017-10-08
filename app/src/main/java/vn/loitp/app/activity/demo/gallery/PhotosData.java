package vn.loitp.app.activity.demo.gallery;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.utilities.LLog;
import vn.loitp.flickr.model.photosetgetphotos.Photo;

/**
 * Created by www.muathu@gmail.com on 10/8/2017.
 */

public class PhotosData {
    private final String TAG = PhotosData.class.getSimpleName();
    private static final PhotosData ourInstance = new PhotosData();

    public static PhotosData getInstance() {
        return ourInstance;
    }

    private PhotosData() {
    }

    private List<Photo> photoList = new ArrayList<>();

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
        LLog.d(TAG, "size: " + this.photoList.size());
    }

    public void addPhoto(List<Photo> photoList) {
        if (this.photoList.isEmpty()) {
            setPhotoList(photoList);
            return;
        }
        this.photoList.addAll(photoList);
        LLog.d(TAG, "size: " + this.photoList.size());
    }

    public void clearData() {
        this.photoList.clear();
        LLog.d(TAG, "size: " + this.photoList.size());
    }

    public int getSize() {
        if (this.photoList == null || this.photoList.isEmpty()) {
            return 0;
        }
        return this.photoList.size();
    }

    public Photo getPhoto(int position) {
        if (this.photoList == null || this.photoList.isEmpty() || position < 0 || position > this.photoList.size()) {
            return null;
        }
        return this.photoList.get(position);
    }

    public int getPosition(String photoID) {
        for (int i = 0; i < this.photoList.size(); i++) {
            if (photoID.equals(this.photoList.get(i).getId())) {
                return i;
            }
        }
        return -1;
    }
}
