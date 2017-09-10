package vn.puresolutions.livestarv3.activity.userprofile.model;

import java.util.ArrayList;

/**
 * File created on 8/10/2017.
 *
 * @author Anhdv
 */

public class SectionVideo {
    private String date;
    private ArrayList<SingleVideo> lstSingleVideo;

    public SectionVideo() {
    }

    public SectionVideo(String date, ArrayList<SingleVideo> lstSingleVideo) {
        this.date = date;
        this.lstSingleVideo = lstSingleVideo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<SingleVideo> getLstSingleVideo() {
        return lstSingleVideo;
    }

    public void setLstVideo(ArrayList<SingleVideo> lstVideo) {
        this.lstSingleVideo = lstVideo;
    }
}
