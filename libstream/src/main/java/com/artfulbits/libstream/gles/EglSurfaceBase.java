package com.artfulbits.libstream.gles;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.opengl.EGL14;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class EglSurfaceBase {

    protected static final String TAG = "Grafika";
    protected EglCore mEglCore;
    private EGLSurface mEGLSurface;
    private int mWidth;
    private int mHeight;


    protected EglSurfaceBase(EglCore eglCore) {
        this.mEGLSurface = EGL14.EGL_NO_SURFACE;
        this.mWidth = -1;
        this.mHeight = -1;
        this.mEglCore = eglCore;
    }

    public void createWindowSurface(Object surface) {
        if (this.mEGLSurface != EGL14.EGL_NO_SURFACE) {
            throw new IllegalStateException("surface already created");
        } else {
            this.mEGLSurface = this.mEglCore.createWindowSurface(surface);
        }
    }

    public void createOffscreenSurface(int width, int height) {
        if (this.mEGLSurface != EGL14.EGL_NO_SURFACE) {
            throw new IllegalStateException("surface already created");
        } else {
            this.mEGLSurface = this.mEglCore.createOffscreenSurface(width, height);
            this.mWidth = width;
            this.mHeight = height;
        }
    }

    public int getWidth() {
        return this.mWidth < 0 ? this.mEglCore.querySurface(this.mEGLSurface, 12375) : this.mWidth;
    }

    public int getHeight() {
        return this.mHeight < 0 ? this.mEglCore.querySurface(this.mEGLSurface, 12374) : this.mHeight;
    }

    public void releaseEglSurface() {
        this.mEglCore.releaseSurface(this.mEGLSurface);
        this.mEGLSurface = EGL14.EGL_NO_SURFACE;
        this.mWidth = this.mHeight = -1;
    }

    public void makeCurrent() {
        this.mEglCore.makeCurrent(this.mEGLSurface);
    }

    public void makeCurrentReadFrom(EglSurfaceBase readSurface) {
        this.mEglCore.makeCurrent(this.mEGLSurface, readSurface.mEGLSurface);
    }

    public boolean swapBuffers() {
        boolean result = this.mEglCore.swapBuffers(this.mEGLSurface);
        if (!result) {
            Log.d("Grafika", "WARNING: swapBuffers() failed");
        }

        return result;
    }

    public void setPresentationTime(long nsecs) {
        this.mEglCore.setPresentationTime(this.mEGLSurface, nsecs);
    }

    public void saveFrame(File file) throws IOException {
        if (!this.mEglCore.isCurrent(this.mEGLSurface)) {
            throw new RuntimeException("Expected EGL context/surface is not current");
        } else {
            String filename = file.toString();
            int width = this.getWidth();
            int height = this.getHeight();
            ByteBuffer buf = ByteBuffer.allocateDirect(width * height * 4);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            GLES20.glReadPixels(0, 0, width, height, 6408, 5121, buf);
            GlUtil.checkGlError("glReadPixels");
            buf.rewind();
            BufferedOutputStream bos = null;

            try {
                bos = new BufferedOutputStream(new FileOutputStream(filename));
                Bitmap bmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                bmp.copyPixelsFromBuffer(buf);
                bmp.compress(CompressFormat.PNG, 90, bos);
                bmp.recycle();
            } finally {
                if (bos != null) {
                    bos.close();
                }

            }

            Log.d("Grafika", "Saved " + width + "x" + height + " frame as \'" + filename + "\'");
        }
    }
}
