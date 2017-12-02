package vn.loitp.app.activity.customviews.keyword_hottags._lib.keyword;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LabelValues implements Parcelable {

    private int key;
    private String value;

    public LabelValues(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.key);
        dest.writeString(this.value);
    }

    private LabelValues(Parcel in) {
        this.key = in.readInt();
        this.value = in.readString();
    }

    public static final Creator<LabelValues> CREATOR = new Creator<LabelValues>() {
        public LabelValues createFromParcel(Parcel source) {
            return new LabelValues(source);
        }

        public LabelValues[] newArray(int size) {
            return new LabelValues[size];
        }
    };
}
