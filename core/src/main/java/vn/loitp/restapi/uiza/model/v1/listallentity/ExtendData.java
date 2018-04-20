
package vn.loitp.restapi.uiza.model.v1.listallentity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExtendData {

    @SerializedName("published-date")
    @Expose
    private String publishedDate;
    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("artist")
    @Expose
    private String artist;

    @SerializedName("director")
    @Expose
    private String director;

    @SerializedName("text")
    @Expose
    private String text;

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
