package vn.loitp.app.data;

import org.greenrobot.eventbus.EventBus;

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

    /*public static class ComicChangeEvent {
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
    }*/
}
