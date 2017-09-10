package vn.puresolutions.livestar.corev3.api.model.old;

import java.util.List;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class Broadcasters extends BaseModel {
    private int totalPage;

    private List<Broadcaster> broadcasters;

    public List<Broadcaster> getBroadcasters() {
        return broadcasters;
    }

    public void setBroadcasters(List<Broadcaster> broadcasters) {
        this.broadcasters = broadcasters;
    }
    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
