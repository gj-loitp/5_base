package com.artfulbits.libstream;

import android.content.Context;
import android.view.Surface;

import java.util.ArrayList;
import java.util.List;

public class StreamerGLBuilder {
    private static final String TAG = StreamerGLBuilder.class.getSimpleName();
    private List mCameraList;
    private StreamerGL streamer;
    private Streamer.CAMERA_API cameraApi;
    private String userAgent;
    private int videoRotation;
    private Streamer.Listener listener;
    private Context context;
    private String cameraId;
    private VideoEncoder videoEncoder;
    private int bitRate;
    private int key_frame_interval;
    private Streamer.Size video_size;
    private Streamer.FpsRange fpsRange;
    private Surface surface;
    private Streamer.Size surfaceSize;
    private int displayRotation;
    private AudioEncoder audioEncoder;
    private int audioSource;
    private int sampleRate;
    private int channelCount;
    protected List mFlipCameraInfo;
    CameraManager mCameraManager;

    public StreamerGLBuilder(Context c) {
        this.cameraApi = Streamer.CAMERA_API.CAMERA;
        this.userAgent = "LarixBroadcaster/2.0-1";
        this.videoRotation = 0;
        this.cameraId = "";
        this.bitRate = 2000000;
        this.key_frame_interval = 2;
        this.video_size = new Streamer.Size(640, 480);
        this.displayRotation = 0;
        this.audioSource = 5;
        this.sampleRate = '\uac44';
        this.channelCount = 1;
        this.mFlipCameraInfo = new ArrayList();
        this.context = c;
    }

    public void addCamera(String id, Streamer.Size sz) {
        VideoListener.FlipCameraInfo info = new VideoListener.FlipCameraInfo();
        info.cameraIdStr = id;
        info.cameraId = Integer.parseInt(info.cameraIdStr);
        info.videoSize = sz;
        double streamAspect = (double) this.video_size.width / (double) this.video_size.height;
        double camAspect = (double) info.videoSize.width / (double) info.videoSize.height;
        double aspectDiff = streamAspect / camAspect - 1.0D;
        info.scale_letterbox = 1.0F;
        info.scale = 1.0F;
        info.scale_landscape_pillarbox = 1.0F;
        info.scale_landscape_letterbox = 1.0F;
        float w;
        float h;
        float wCam;
        float hCam;
        float ratio;
        float ratioCam;
        if (Math.abs(aspectDiff) < 0.01D) {
            //Log.d("Builde", "Aspect ratio is the same");
            w = (float) this.video_size.width;
            h = (float) this.video_size.height;
            wCam = w / h;
            //Log.d("Builde", "Aspect ratio: " + Float.toString(wCam));
            hCam = h / w;
            //Log.d("Builde", "Vertical portrait scale: " + Float.toString(hCam));
            ratio = h * hCam;
            //Log.d("Builde", "Horizontal portrait image size: " + Float.toString(ratio));
            ratioCam = ratio / w;
            //Log.d("Builde", "Horizontal portrait GL scale: " + Float.toString(ratioCam));
            info.scale = ratioCam;
            //Log.d("Builde", "Portrait mode GL scale: " + Float.toString(info.scale));
        } else {
            //Log.d("Builde", "Aspect ratio different");
            w = (float) this.video_size.width;
            h = (float) this.video_size.height;
            wCam = (float) sz.width;
            hCam = (float) sz.height;
            ratio = w / h;
            //Log.d("Builde", "Aspect ratio: " + Float.toString(ratio) + " (" + Float.toString(w) + "x" + Float.toString(h) + ")");
            ratioCam = wCam / hCam;
            //Log.d("Builde", "Aspect ratio camera: " + Float.toString(ratioCam) + " (" + Float.toString(wCam) + "x" + Float.toString(hCam) + ")");
            float hscale;
            float vsize;
            float vscalegl;
            if (w > h) {
                hscale = h / wCam;
                //Log.d("Builde", "Vertical portrait scale: " + Float.toString(hscale));
                vsize = hCam * hscale;
                //Log.d("Builde", "Horizontal portrait image size: " + Float.toString(vsize));
                vscalegl = vsize / w;
                //Log.d("Builde", "Horizontal portrait GL scale: " + Float.toString(vscalegl));
                info.scale = vscalegl;
                //Log.d("Builde", "Portrait mode GL scale: " + Float.toString(info.scale));
                if (camAspect < streamAspect) {
                    hscale = h / hCam;
                    //Log.d("Builde", "Vertical portrait scale: " + Float.toString(hscale));
                    vsize = wCam * hscale;
                    //Log.d("Builde", "Horizontal portrait image size: " + Float.toString(vsize));
                    vscalegl = vsize / w;
                    //Log.d("Builde", "Horizontal pillarbox GL scale: " + Float.toString(vscalegl));
                    info.scale_landscape_pillarbox = vscalegl;
                    //Log.d("Builde", "Landscape mode GL scale: " + Float.toString(info.scale_landscape_pillarbox));
                } else {
                    hscale = w / wCam;
                    //Log.d("Builde", "Horizontal portrait scale: " + Float.toString(hscale));
                    vsize = hCam * hscale;
                    //Log.d("Builde", "Vertical image size: " + Float.toString(vsize));
                    vscalegl = vsize / h;
                    //Log.d("Builde", "Vertical GL scale: " + Float.toString(vscalegl));
                    info.scale_landscape_letterbox = vscalegl;
                    //Log.d("Builde", "Letterbox GL scale: " + Float.toString(info.scale_landscape_letterbox));
                }
            } else {
                hscale = w / wCam;
                //Log.d("Builde", "Horizontal portrait scale: " + Float.toString(hscale));
                vsize = hCam * hscale;
                //Log.d("Builde", "Vertical image size: " + Float.toString(vsize));
                vscalegl = vsize / h;
                //Log.d("Builde", "Vertical GL scale: " + Float.toString(vscalegl));
                info.scale_landscape_letterbox = vscalegl;
                //Log.d("Builde", "Letterbox GL scale: " + Float.toString(info.scale_landscape_letterbox));
                if (w / h > hCam / wCam) {
                    //Log.d("Builde", "GOAT");
                    hscale = h / wCam;
                    //Log.d("Builde", "Vertical portrait scale: " + Float.toString(hscale));
                    vsize = hCam * hscale;
                    //Log.d("Builde", "Horizontal portrait image size: " + Float.toString(vsize));
                    vscalegl = vsize / w;
                    //Log.d("Builde", "Horizontal portrait GL scale: " + Float.toString(vscalegl));
                    info.scale = vscalegl;
                    //Log.d("Builde", "Portrait mode GL scale: " + Float.toString(info.scale));
                } else {
                    //Log.d("Builde", "GOAT2");
                    hscale = w / hCam;
                    //Log.d("Builde", "Horizontal portrait scale: " + Float.toString(hscale));
                    vsize = wCam * hscale;
                    //Log.d("Builde", "Vertical image size: " + Float.toString(vsize));
                    vscalegl = vsize / h;
                    //Log.d("Builde", "Vertical GL scale: " + Float.toString(vscalegl));
                    info.scale_letterbox = vscalegl;
                    //Log.d("Builde", "Letterbox GL scale: " + Float.toString(info.scale_letterbox));
                }
            }
        }

        this.mFlipCameraInfo.add(info);
        if (this.mFlipCameraInfo.size() > 2) {
            throw new IllegalArgumentException();
        }
    }

    public void setCamera2(boolean camera2) {
        this.cameraApi = camera2 ? Streamer.CAMERA_API.CAMERA2 : Streamer.CAMERA_API.CAMERA;
    }

    public void setUserAgent(String ua) {
        if (null != ua) {
            this.userAgent = ua;
        }

    }

    public void setVideoRotation(int r) {
        this.videoRotation = r;
    }

    public void setBitRate(int br) {
        this.bitRate = br;
    }

    public void setKeyFrameInterval(int k) {
        this.key_frame_interval = k;
    }

    public StreamerGL build() {
        return this.createStreamer();
    }

    public void setSurface(Surface s) {
        this.surface = s;
    }

    public void setSurfaceSize(Streamer.Size s) {
        this.surfaceSize = s;
    }

    public void setListener(Streamer.Listener l) {
        this.listener = l;
    }

    public void setAudioSource(int as) {
        this.audioSource = as;
    }

    public void setSampleRate(int sr) {
        this.sampleRate = sr;
    }

    public void setChannelCount(int cc) {
        this.channelCount = cc;
    }

    public void setFrameRate(Streamer.FpsRange fr) {
        this.fpsRange = fr;
    }

    public void setVideoSize(Streamer.Size s) {
        this.video_size = s;
    }

    public void setCameraId(String s) {
        this.cameraId = s;
    }

    public void setDisplayRotation(int r) {
        this.displayRotation = r;
    }

    private void createVideoEncoder() {
        this.videoEncoder = VideoEncoder.createVideoEncoder(this.video_size);
        this.videoEncoder.setFrameRate(this.fpsRange);
        this.videoEncoder.setBitRate(this.bitRate);
        this.videoEncoder.setKeyFrameInterval(this.key_frame_interval);
    }

    private void createAudioEncoder() {
        this.audioEncoder = AudioEncoder.createAudioEncoder();
        int[] supportedSampleRates = this.audioEncoder.getSupportedSampleRates();
        if (supportedSampleRates != null) {
            boolean maxInputChannelCount = false;
            int[] audioBitRate = supportedSampleRates;
            int len$ = supportedSampleRates.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                int cursor = audioBitRate[i$];
                if (cursor == this.sampleRate) {
                    maxInputChannelCount = true;
                    break;
                }
            }

            if (!maxInputChannelCount) {
                this.sampleRate = supportedSampleRates[0];
            }

            this.audioEncoder.setSampleRate(this.sampleRate);
        }

        int var7 = this.audioEncoder.getMaxInputChannelCount();
        if (var7 == 1) {
            this.channelCount = 1;
        }

        this.audioEncoder.setChannelCount(this.channelCount);
        this.audioEncoder.setAACProfile(2);
        int var8 = AudioEncoder.calcBitRate(this.sampleRate, this.channelCount, 2);
        this.audioEncoder.setBitRate(var8);
    }

    private StreamerGL createStreamer() {
        this.createAudioEncoder();
        this.createVideoEncoder();
        StreamerGL streamer = new StreamerGL(this.cameraApi);
        streamer.setUserAgent(this.userAgent);
        streamer.setVideoEncoder(this.videoEncoder);
        streamer.setAudioEncoder(this.audioEncoder);
        streamer.setSurface(this.surface);
        streamer.setSurfaceSize(this.surfaceSize);
        streamer.setDisplayRotation(this.displayRotation);
        streamer.setVideoOrientation(this.videoRotation);
        streamer.mFlipCameraInfo = this.mFlipCameraInfo;
        streamer.cameraId = this.cameraId;
        streamer.mContext = this.context;
        return streamer;
    }

    public List getCameraList(Context context, boolean camera2) {
        CameraManager cameraManager = this.getCameraManager(camera2);
        this.mCameraList = cameraManager.getCameraList(context);
        return this.mCameraList;
    }

    protected CameraManager getCameraManager(boolean camera2) {
        if (!camera2) {
            this.mCameraManager = new CameraManager16();
        } else {
            this.mCameraManager = new CameraManager21();
        }

        return this.mCameraManager;
    }
}
