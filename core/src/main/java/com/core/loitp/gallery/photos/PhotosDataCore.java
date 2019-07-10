package com.core.loitp.gallery.photos;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;

/**
 * Created by www.muathu@gmail.com on 10/8/2017.
 */

public class PhotosDataCore {
    private final String TAG = PhotosDataCore.class.getSimpleName();
    private static final PhotosDataCore ourInstance = new PhotosDataCore();

    public static PhotosDataCore getInstance() {
        return ourInstance;
    }

    private PhotosDataCore() {
    }

    private List<Photo> photoList = new ArrayList<>();

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(final List<Photo> photoList) {
        this.photoList = photoList;
        //LLog.d(TAG, "size: " + this.photoList.size());
    }

    public void addPhoto(final List<Photo> photoList) {
        if (this.photoList.isEmpty()) {
            setPhotoList(photoList);
            return;
        }
        if (photoList.size() > 300) {
            this.photoList.clear();
        }
        this.photoList.addAll(photoList);
        //LLog.d(TAG, "size: " + this.photoList.size());
    }

    public void clearData() {
        this.photoList.clear();
        //LLog.d(TAG, "size: " + this.photoList.size());
    }

    public int getSize() {
        if (this.photoList == null || this.photoList.isEmpty()) {
            return 0;
        }
        return this.photoList.size();
    }

    public Photo getPhoto(final int position) {
        if (this.photoList == null || this.photoList.isEmpty() || position < 0 || position > this.photoList.size()) {
            return null;
        }
        return this.photoList.get(position);
    }

    public int getPosition(final String photoID) {
        for (int i = 0; i < this.photoList.size(); i++) {
            if (photoID.equals(this.photoList.get(i).getId())) {
                return i;
            }
        }
        return -1;
    }
}
