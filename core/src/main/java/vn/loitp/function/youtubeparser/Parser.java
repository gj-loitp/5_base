/*
 *   Copyright 2016 Marco Gomiero
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package vn.loitp.function.youtubeparser;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import vn.loitp.function.youtubeparser.models.videos.High;
import vn.loitp.function.youtubeparser.models.videos.Id;
import vn.loitp.function.youtubeparser.models.videos.Item;
import vn.loitp.function.youtubeparser.models.videos.Main;
import vn.loitp.function.youtubeparser.models.videos.Snippet;
import vn.loitp.function.youtubeparser.models.videos.Thumbnails;
import vn.loitp.function.youtubeparser.models.videos.Video;

/**
 * This class parses video data from Youtube
 * <p>
 * Created by Marco Gomiero on 6/9/16.
 */
public class Parser extends AsyncTask<String, Void, String> {

    private OnTaskCompleted onComplete;
    public static final int ORDER_DATE = 1;
    public static final int ORDER_VIEW_COUNT = 2;

    /**
     * This method generates the url that retrieves Youtube video data
     *
     * @param channelID The ID of the desired channel. Ex: https://www.youtube.com/channel/UCVHFbqXqoYvEWM1Ddxl0QDg
     *                  channel id = UCVHFbqXqoYvEWM1Ddxl0QDg
     * @param maxResult The number of video to get
     * @param key       Your Browser API key. Obtain one by visiting https://console.developers.google.com
     * @return The url required to get data
     * @deprecated This method is deprecated. Please use the new version that allows to choose the
     * type of order of the videos: {@link #generateRequest(String, int, int, String)}
     */
    @Deprecated
    public String generateRequest(String channelID, int maxResult, String key) {

        String urlString = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=";
        urlString = urlString + channelID + "&maxResults=" + maxResult + "&order=date&key=" + key;
        return urlString;
    }

    /**
     * This method generates the url to retrieve Youtube Video data
     *
     * @param channelID The ID of the desired channel. Ex: https://www.youtube.com/channel/UCVHFbqXqoYvEWM1Ddxl0QDg
     *                  channel id = UCVHFbqXqoYvEWM1Ddxl0QDg
     * @param maxResult The number of video to get. The maximum value is 50
     * @param orderType The type of ordering. You can choose an order by date: {@link #ORDER_DATE} and by view count {@link #ORDER_VIEW_COUNT}.
     * @param key       Your Browser API key. Obtain one by visiting https://console.developers.google.com
     * @return The url required to get data
     */
    public String generateRequest(String channelID, int maxResult, int orderType, String key) {

        String urlString = "https://www.googleapis.com/youtube/v3/search?&part=snippet&channelId=";
        String order = "";
        switch (orderType) {

            case ORDER_DATE:
                order = "date";
                break;

            case ORDER_VIEW_COUNT:
                order = "viewcount";
                break;

            default:
                break;
        }

        urlString = urlString + channelID + "&maxResults=" + maxResult + "&order=" + order + "&key=" + key;
        return urlString;
    }

    /**
     * This method generates the url to retrieve more video data. Remember that first you have to call
     * {@link #generateRequest(String, int, int, String)} to get the next page token
     *
     * @param channelID The ID of the desired channel. Ex: https://www.youtube.com/channel/UCVHFbqXqoYvEWM1Ddxl0QDg
     *                  channel id = UCVHFbqXqoYvEWM1Ddxl0QDg
     * @param maxResult The number of video to get. The maximum value is 50
     * @param orderType The type of ordering. You can choose an order by date: {@link #ORDER_DATE} and by view count {@link #ORDER_VIEW_COUNT}.
     * @param key       Your Browser API key. Obtain one by visiting https://console.developers.google.com
     * @param nextToken The token necessary to load more data
     * @return The url required to get more data
     */
    public String generateMoreDataRequest(String channelID, int maxResult, int orderType, String key, String nextToken) {

        String urlString = "https://www.googleapis.com/youtube/v3/search?pageToken=";
        String order = "";
        switch (orderType) {

            case ORDER_DATE:
                order = "date";
                break;

            case ORDER_VIEW_COUNT:
                order = "viewcount";
                break;

            default:
                break;
        }

        urlString = urlString + nextToken + "&part=snippet&channelId=" + channelID + "&maxResults="
                + maxResult + "&order=" + order + "&key=" + key;
        return urlString;
    }


    public void onFinish(OnTaskCompleted onComplete) {
        this.onComplete = onComplete;
    }

    @Override
    protected String doInBackground(String... ulr) {

        Response response;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ulr[0])
                .build();

        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful())
                return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            onComplete.onError();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {

        if (result != null) {
            try {

                ArrayList<Video> videos = new ArrayList<>();

                Gson gson = new GsonBuilder().create();
                Main data = gson.fromJson(result, Main.class);

                //Begin parsing Json data
                List<Item> itemList = data.getItems();
                String nextToken = data.getNextPageToken();

                for (int i = 0; i < itemList.size(); i++) {

                    Item item = itemList.get(i);
                    Snippet snippet = item.getSnippet();

                    Id id = item.getId();

                    Thumbnails image = snippet.getThumbnails();

                    High high = image.getHigh();

                    String title = snippet.getTitle();
                    String videoId = id.getVideoId();
                    String imageLink = high.getUrl();
                    String sDate = snippet.getPublishedAt();

                    Locale.setDefault(Locale.getDefault());
                    TimeZone tz = TimeZone.getDefault();
                    Calendar cal = Calendar.getInstance(tz);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    sdf.setCalendar(cal);
                    cal.setTime(sdf.parse(sDate));
                    Date date = cal.getTime();

                    SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMMM yyyy");
                    String pubDateString = sdf2.format(date);

                    Video tempVideo = new Video(title, videoId, imageLink, pubDateString);
                    videos.add(tempVideo);
                }

                Log.i("YoutubeParser", "Youtube data parsed correctly!");
                onComplete.onTaskCompleted(videos, nextToken);

            } catch (Exception e) {

                e.printStackTrace();
                onComplete.onError();
            }
        } else
            onComplete.onError();
    }


    public interface OnTaskCompleted {
        void onTaskCompleted(ArrayList<Video> list, String nextPageToken);

        void onError();
    }
}