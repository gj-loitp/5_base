package vn.puresolutions.livestar.core.api.model;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class Schedule extends BaseModel {
    private String date;
    private String start;
    private String end;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
