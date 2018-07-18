package vn.loitp.app.activity.customviews.videoview.exoplayer2cast;

/**
 * Created by loitp on 7/18/2018.
 */

import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.gms.cast.MediaInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility methods and constants for the Cast demo application.
 */
/* package */ final class DemoUtil {

    public static final String MIME_TYPE_DASH = MimeTypes.APPLICATION_MPD;
    public static final String MIME_TYPE_HLS = MimeTypes.APPLICATION_M3U8;
    public static final String MIME_TYPE_SS = MimeTypes.APPLICATION_SS;
    public static final String MIME_TYPE_VIDEO_MP4 = MimeTypes.VIDEO_MP4;

    /**
     * The list of samples available in the cast demo app.
     */
    public static final List<Sample> SAMPLES;

    /**
     * Represents a media sample.
     */
    public static final class Sample {

        /**
         * The uri from which the media sample is obtained.
         */
        public final String uri;
        /**
         * A descriptive name for the sample.
         */
        public final String name;
        /**
         * The mime type of the media sample, as required by {@link MediaInfo#setContentType}.
         */
        public final String mimeType;

        /**
         * @param uri      See {@link #uri}.
         * @param name     See {@link #name}.
         * @param mimeType See {@link #mimeType}.
         */
        public Sample(String uri, String name, String mimeType) {
            this.uri = uri;
            this.name = name;
            this.mimeType = mimeType;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    static {
        // App samples.
        ArrayList<Sample> samples = new ArrayList<>();
        samples.add(new Sample("https://storage.googleapis.com/wvmedia/clear/h264/tears/tears.mpd",
                "DASH (clear,MP4,H264)", MIME_TYPE_DASH));
        samples.add(new Sample("https://commondatastorage.googleapis.com/gtv-videos-bucket/CastVideos/"
                + "hls/TearsOfSteel.m3u8", "Tears of Steel (HLS)", MIME_TYPE_HLS));
        samples.add(new Sample("https://html5demos.com/assets/dizzy.mp4", "Dizzy (MP4)",
                MIME_TYPE_VIDEO_MP4));
        /*samples.add(new Sample("https://android-vod.uizacdn.net/16f8e65d8e2643ffa3ff5ee9f4f9ba03-stream/fe2865b7-09ec-4f71-afb6-12d7815555ca/package/manifest.mpd",
                "DASH (clear,MP4,H264)", MIME_TYPE_DASH));
        samples.add(new Sample("https://android-vod.uizacdn.net/16f8e65d8e2643ffa3ff5ee9f4f9ba03-stream/fe2865b7-09ec-4f71-afb6-12d7815555ca/package/manifest.mpd",
                "DASH (clear,MP4,H264)", MIME_TYPE_DASH));
        samples.add(new Sample("https://android-vod.uizacdn.net/16f8e65d8e2643ffa3ff5ee9f4f9ba03-stream/fe2865b7-09ec-4f71-afb6-12d7815555ca/package/manifest.mpd",
                "DASH (clear,MP4,H264)", MIME_TYPE_DASH));*/
        SAMPLES = Collections.unmodifiableList(samples);
    }

    private DemoUtil() {
    }

}