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

package com.function.youtubeparser.models.videos;

/**
 * Created by marco on 5/7/16.
 */
public class Video {

    private String title;
    private String videoId;
    private String coverLink;
    private String date;
    private String linkYoutube;

    public Video(String title, String videoId, String coverLink, String date) {
        this.title = title;
        this.videoId = videoId;
        this.coverLink = coverLink;
        this.date = date;
        this.linkYoutube = "https://www.youtube.com/watch?v=" + videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getCoverLink() {
        return coverLink;
    }

    public void setCoverLink(String coverLink) {
        this.coverLink = coverLink;
    }

    public String getLinkYoutube(){
        return linkYoutube;
    }

    @Override
    public String toString() {
        return "Video { " +
                "title= '" + title + '\'' +
                ", videoId= '" + videoId + '\'' +
                ", coverLink= '" + coverLink + '\'' +
                ", date= '" + date + '\'' +
                '}';
    }
}
