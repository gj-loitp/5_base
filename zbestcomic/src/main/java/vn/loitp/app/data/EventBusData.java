package vn.loitp.app.data;

import org.greenrobot.eventbus.EventBus;

import vn.loitp.app.model.comic.Comic;

/**
 * Created by www.muathu@gmail.com on 10/21/2017.
 */

public class EventBusData {
    private static final EventBusData ourInstance = new EventBusData();

    public static EventBusData getInstance() {
        return ourInstance;
    }

    private EventBusData() {
    }

    public static class ComicChangeEvent {
        private int status;
        private Comic comic;
        private String tag;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Comic getComic() {
            return comic;
        }

        public void setComic(Comic comic) {
            this.comic = comic;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
    }

    public void sendComicChange(int status, Comic comic, String tag) {
        ComicChangeEvent comicChangeEvent = new ComicChangeEvent();
        comicChangeEvent.setStatus(status);
        comicChangeEvent.setComic(comic);
        comicChangeEvent.setTag(tag);
        EventBus.getDefault().post(comicChangeEvent);
    }

    public static class SearchEvent {
        private String TAG;
        private String keyword;

        public String getTAG() {
            return TAG;
        }

        public void setTAG(String TAG) {
            this.TAG = TAG;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }
    }

    public void sendSearchEvent(String keyword, String tag) {
        SearchEvent searchEvent = new SearchEvent();
        searchEvent.setKeyword(keyword);
        searchEvent.setTAG(tag);
        EventBus.getDefault().post(searchEvent);
    }

    /*public static class ConnectEvent {
        private boolean isConnected;
        private boolean isConnectedFast;
        private boolean isConnectedWifi;
        private boolean isConnectedMobile;

        public boolean isConnected() {
            return isConnected;
        }

        public void setConnected(boolean connected) {
            isConnected = connected;
        }

        public boolean isConnectedFast() {
            return isConnectedFast;
        }

        public void setConnectedFast(boolean connectedFast) {
            isConnectedFast = connectedFast;
        }

        public boolean isConnectedWifi() {
            return isConnectedWifi;
        }

        public void setConnectedWifi(boolean connectedWifi) {
            isConnectedWifi = connectedWifi;
        }

        public boolean isConnectedMobile() {
            return isConnectedMobile;
        }

        public void setConnectedMobile(boolean connectedMobile) {
            isConnectedMobile = connectedMobile;
        }
    }

    public void sendConnectChange(boolean isConnected, boolean isConnectedFast, boolean isConnectedWifi, boolean isConnectedMobile) {
        ConnectEvent connectEvent = new ConnectEvent();
        connectEvent.setConnected(isConnected);
        connectEvent.setConnectedFast(isConnectedFast);
        connectEvent.setConnectedWifi(isConnectedWifi);
        connectEvent.setConnectedMobile(isConnectedMobile);
        EventBus.getDefault().post(connectEvent);
    }*/
}
