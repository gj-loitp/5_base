package vn.puresolutions.livestarv3.activity.userprofile.model;

/**
 * File created on 8/10/2017.
 *
 * @author anhdv
 */

public class SingleVideo {
    private String urlAvatar;
    private String videoName;
    private int countView;
    private int countHeart;

    public SingleVideo(String urlAvatar, String videoName, int countView, int countHeart) {
        this.urlAvatar = urlAvatar;
        this.videoName = videoName;
        this.countView = countView;
        this.countHeart = countHeart;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public int getCountView() {
        return countView;
    }
    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }
    public void setCountView(int countView) {
        this.countView = countView;
    }

    public int getCountHeart() {
        return countHeart;
    }

    public void setCountHeart(int countHeart) {
        this.countHeart = countHeart;
    }
}
