
package vn.loitp.app.activity.api.truyentranhtuan.model.chap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chap {

  @SerializedName("tit")
  @Expose
  private String tit;

  public boolean isReaded() {
    return isReaded;
  }

  public void setReaded(boolean readed) {
    isReaded = readed;
  }

  @SerializedName("url")
  @Expose

  private String url;

  @SerializedName("isReaded")
  @Expose
  private boolean isReaded;

  /**
   * @return The tit
   */
  public String getTit() {
    return tit;
  }

  /**
   * @param tit The tit
   */
  public void setTit(String tit) {
    this.tit = tit;
  }

  /**
   * @return The url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @param url The url
   */
  public void setUrl(String url) {
    this.url = url;
  }

}
