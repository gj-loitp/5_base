package vn.puresolutions.livestarv3.activity.homescreen.model.idolposter;

import java.util.ArrayList;

import vn.puresolutions.livestar.corev3.api.model.v3.getposter.GetPoster;
import vn.puresolutions.livestarv3.activity.homescreen.model.ModelBase;

/**
 * Created by pratap.kesaboyina on 30-11-2015.
 */
public class ModelIdolPoster extends ModelBase {
    private ArrayList<GetPoster> getPosterArrayList;

    public ModelIdolPoster() {
    }

    public ArrayList<GetPoster> getGetPosterArrayList() {
        return getPosterArrayList;
    }

    public void setGetPosterArrayList(ArrayList<GetPoster> getPosterArrayList) {
        this.getPosterArrayList = getPosterArrayList;
    }
}
