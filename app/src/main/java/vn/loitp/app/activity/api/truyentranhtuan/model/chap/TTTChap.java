
package vn.loitp.app.activity.api.truyentranhtuan.model.chap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TTTChap {

  @SerializedName("chaps")
  @Expose
  private Chaps chaps;
  @SerializedName("info")
  @Expose
  private Info info;

  /**
   * @return The chaps
   */
  public Chaps getChaps() {
    return chaps;
  }

  /**
   * @param chaps The chaps
   */
  public void setChaps(Chaps chaps) {
    this.chaps = chaps;
  }

  /**
   * @return The info
   */
  public Info getInfo() {
    return info;
  }

  /**
   * @param info The info
   */
  public void setInfo(Info info) {
    this.info = info;
  }
}
