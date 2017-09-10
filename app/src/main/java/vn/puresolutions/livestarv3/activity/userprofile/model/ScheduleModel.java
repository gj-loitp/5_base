package vn.puresolutions.livestarv3.activity.userprofile.model;

import vn.puresolutions.livestar.corev3.api.model.v3.BaseModel;

/**
 * File created on 8/24/2017.
 *
 * @author anhdv
 */

public class ScheduleModel extends BaseModel {
    private String id;
    private String date;
    private String time;

    public ScheduleModel(String id, String date, String time) {
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public ScheduleModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
