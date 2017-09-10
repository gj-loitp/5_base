//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.artfulbits.libstream;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera.Parameters;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodec.Callback;
import android.media.MediaCodec.CodecException;
import android.media.MediaCodecInfo.CodecProfileLevel;
import android.os.Handler;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

import java.nio.ByteBuffer;
import java.util.List;

import com.artfulbits.libstream.Streamer.Size;
import com.artfulbits.libstream.Streamer.CAPTURE_STATE;
import com.artfulbits.libstream.StreamBuffer.VideoFormatParams;

abstract class VideoListener {
    private static final String TAG = "VideoListener";
    protected Streamer.CAPTURE_STATE state_;
    protected StreamBuffer streamBuffer_;
    protected Streamer.Listener listener_;
    protected VideoEncoder encoder_;
    protected static final byte[] NAL_SEPARATOR = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 1};
    protected Streamer.Size previewSize;
    protected Callback mediaCodecCallback;
    protected long frameId = 1L;
    protected Surface mSurface;
    protected Streamer.Size surfaceSize;
    protected int displayOrientation;
    protected int videoRotation;
    protected float scale = 1.0F;
    protected float scale_letterbox = 1.0F;
    protected float scale_landscape_pillarbox = 1.0F;
    protected float scale_landscape_letterbox = 1.0F;
    protected int cameraId;
    protected List<VideoListener.FlipCameraInfo> mFlipCameraInfo;

    public VideoListener(StreamBuffer streamBuffer, Streamer.Listener listener) {
        this.streamBuffer_ = streamBuffer;
        this.listener_ = listener;
        this.state_ = Streamer.CAPTURE_STATE.STOPPED;
        if (VERSION.SDK_INT >= 21) {
            this.mediaCodecCallback = new Callback() {
                @TargetApi(21)
                public void onInputBufferAvailable(MediaCodec codec, int index) {
                }

                @TargetApi(21)
                public void onOutputBufferAvailable(MediaCodec codec, int index, BufferInfo info) {
                    ByteBuffer outBuffer = codec.getOutputBuffer(index);
                    outBuffer.limit(info.offset + info.size);
                    if (Utils.LTrim(outBuffer, VideoListener.NAL_SEPARATOR)) {
                        info.size -= VideoListener.NAL_SEPARATOR.length;
                    }

                    if ((2 & info.flags) == 0) {
                        BufferItem bufferItem = BufferItem.newVideoBuffer((long) (VideoListener.this.frameId++), info.size);
                        bufferItem.setTimestamp(info.presentationTimeUs);
                        bufferItem.setFlags(info.flags);
                        outBuffer.get(bufferItem.getData(), 0, info.size);
                        VideoListener.this.streamBuffer_.putItem(bufferItem);
                    }

                    codec.releaseOutputBuffer(index, false);
                }

                @TargetApi(21)
                public void onError(MediaCodec codec, CodecException e) {
                    Log.e("VideoListener", "onError");
                    Log.e("VideoListener", Log.getStackTraceString(e));
                    VideoListener.this.setVideoCaptureState(Streamer.CAPTURE_STATE.FAILED);
                }

                @TargetApi(21)
                public void onOutputFormatChanged(MediaCodec codec, MediaFormat format) {
                    Log.d("VideoListener", "onOutputFormatChanged");
                    VideoListener.this.processOutputFormatChanged(format);
                }
            };
        }

    }

    protected void setVideoCaptureState(final CAPTURE_STATE videoCaptureState) {
        if (videoCaptureState != this.state_) {
            this.state_ = videoCaptureState;
            if (null != this.listener_) {
                Handler handler = this.listener_.getHandler();
                if (null != handler) {
                    handler.post(new Runnable() {
                        public void run() {
                            VideoListener.this.listener_.OnVideoCaptureStateChanged(videoCaptureState);
                        }
                    });
                }
            }
        }
    }

    protected void processOutputFormatChanged(MediaFormat format) {
        Log.d("VideoListener", "processOutputFormatChanged");
        VideoFormatParams videoFormatParams = new VideoFormatParams();
        ByteBuffer sps = format.getByteBuffer("csd-0");
        Utils.LTrim(sps, NAL_SEPARATOR);
        videoFormatParams.sps_len = sps.limit() - sps.position();
        videoFormatParams.sps_buf = new byte[videoFormatParams.sps_len];
        sps.get(videoFormatParams.sps_buf, 0, videoFormatParams.sps_len);
        ByteBuffer pps = format.getByteBuffer("csd-1");
        Utils.LTrim(pps, NAL_SEPARATOR);
        videoFormatParams.pps_len = pps.limit() - pps.position();
        videoFormatParams.pps_buf = new byte[videoFormatParams.pps_len];
        pps.get(videoFormatParams.pps_buf, 0, videoFormatParams.pps_len);
        this.streamBuffer_.setVideoFormatParams(videoFormatParams);
    }

    public abstract CodecProfileLevel[] getProfileLevels();

    public abstract boolean start(Context var1, String var2, SurfaceHolder var3, SurfaceTexture var4, VideoEncoder var5);

    public abstract boolean start(Context var1, String var2, SurfaceHolder var3, SurfaceTexture var4, VideoEncoder var5, int orientation);

    public abstract void stop();

    public abstract boolean flip();

    public abstract void setCameraParameters(Parameters var1);

    public abstract Parameters getCameraParameters();

    public abstract void setFilter(int filterId);


    protected static class FlipCameraInfo {
        public int cameraId;
        public String cameraIdStr;
        public Size videoSize;
        public float scale;
        public float scale_letterbox;
        public float scale_landscape_pillarbox;
        public float scale_landscape_letterbox;

        protected FlipCameraInfo() {
        }
    }
}
