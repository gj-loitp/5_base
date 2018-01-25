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
        private boolean isRemoved;
        private Comic comic;
        private String tag;

        public boolean isRemoved() {
            return isRemoved;
        }

        public void setRemoved(boolean removed) {
            isRemoved = removed;
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

    public void sendComicChange(boolean isRemoved, Comic comic, String tag) {
        ComicChangeEvent comicChangeEvent = new ComicChangeEvent();
        comicChangeEvent.setRemoved(isRemoved);
        comicChangeEvent.setComic(comic);
        comicChangeEvent.setTag(tag);
        EventBus.getDefault().post(comicChangeEvent);
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
