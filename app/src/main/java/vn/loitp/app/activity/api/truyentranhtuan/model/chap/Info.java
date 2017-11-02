
package vn.loitp.app.activity.api.truyentranhtuan.model.chap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("cover")
    @Expose
    private String cover;
    @SerializedName("other_name")
    @Expose
    private String otherName;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("new_chap")
    @Expose
    private String newChap;
    @SerializedName("summary")
    @Expose
    private String summary;

    /**
     * 
     * @return
     *     The cover
     */
    public String getCover() {
        return cover;
    }

    /**
     * 
     * @param cover
     *     The cover
     */
    public void setCover(String cover) {
        this.cover = cover;
    }

    /**
     * 
     * @return
     *     The otherName
     */
    public String getOtherName() {
        return otherName;
    }

    /**
     * 
     * @param otherName
     *     The other_name
     */
    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    /**
     * 
     * @return
     *     The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The newChap
     */
    public String getNewChap() {
        return newChap;
    }

    /**
     * 
     * @param newChap
     *     The new_chap
     */
    public void setNewChap(String newChap) {
        this.newChap = newChap;
    }

    /**
     * 
     * @return
     *     The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 
     * @param summary
     *     The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

}
