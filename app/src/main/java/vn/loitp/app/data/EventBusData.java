package vn.loitp.app.data;

import org.greenrobot.eventbus.EventBus;

import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie;

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

    public class ClickVideoEvent {
        private Movie movie;
        private int position;

        public Movie getMovie() {
            return movie;
        }

        public void setMovie(Movie movie) {
            this.movie = movie;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    public void sendClickVideoEvent(Movie movie, int position) {
        ClickVideoEvent clickVideoEvent = new ClickVideoEvent();
        clickVideoEvent.setMovie(movie);
        clickVideoEvent.setPosition(position);
        EventBus.getDefault().post(clickVideoEvent);
    }
}
