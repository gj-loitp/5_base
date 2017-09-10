package com.artfulbits.libstream.gles;

import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLExt;
import android.opengl.EGLSurface;
import android.util.Log;
import android.view.Surface;

public final class EglCore {

   private static final String TAG = "Grafika";
   public static final int FLAG_RECORDABLE = 1;
   public static final int FLAG_TRY_GLES3 = 2;
   private static final int EGL_RECORDABLE_ANDROID = 12610;
   private EGLDisplay mEGLDisplay;
   private EGLContext mEGLContext;
   private EGLConfig mEGLConfig;
   private int mGlVersion;


   public EglCore() {
      this((EGLContext)null, 0);
   }

   public EglCore(EGLContext sharedContext, int flags) {
      this.mEGLDisplay = EGL14.EGL_NO_DISPLAY;
      this.mEGLContext = EGL14.EGL_NO_CONTEXT;
      this.mEGLConfig = null;
      this.mGlVersion = -1;
      if(this.mEGLDisplay != EGL14.EGL_NO_DISPLAY) {
         throw new RuntimeException("EGL already set up");
      } else {
         if(sharedContext == null) {
            sharedContext = EGL14.EGL_NO_CONTEXT;
         }

         this.mEGLDisplay = EGL14.eglGetDisplay(0);
         if(this.mEGLDisplay == EGL14.EGL_NO_DISPLAY) {
            throw new RuntimeException("unable to get EGL14 display");
         } else {
            int[] version = new int[2];
            if(!EGL14.eglInitialize(this.mEGLDisplay, version, 0, version, 1)) {
               this.mEGLDisplay = null;
               throw new RuntimeException("unable to initialize EGL14");
            } else {
               EGLConfig values;
               int[] attrib2_list;
               EGLContext context;
               if((flags & 2) != 0) {
                  values = this.getConfig(flags, 3);
                  if(values != null) {
                     attrib2_list = new int[]{12440, 3, 12344};
                     context = EGL14.eglCreateContext(this.mEGLDisplay, values, sharedContext, attrib2_list, 0);
                     if(EGL14.eglGetError() == 12288) {
                        this.mEGLConfig = values;
                        this.mEGLContext = context;
                        this.mGlVersion = 3;
                     }
                  }
               }

               if(this.mEGLContext == EGL14.EGL_NO_CONTEXT) {
                  values = this.getConfig(flags, 2);
                  if(values == null) {
                     throw new RuntimeException("Unable to find a suitable EGLConfig");
                  }

                  attrib2_list = new int[]{12440, 2, 12344};
                  context = EGL14.eglCreateContext(this.mEGLDisplay, values, sharedContext, attrib2_list, 0);
                  this.checkEglError("eglCreateContext");
                  this.mEGLConfig = values;
                  this.mEGLContext = context;
                  this.mGlVersion = 2;
               }

               int[] values1 = new int[1];
               EGL14.eglQueryContext(this.mEGLDisplay, this.mEGLContext, 12440, values1, 0);
               Log.d("Grafika", "EGLContext created, client version " + values1[0]);
            }
         }
      }
   }

   private EGLConfig getConfig(int flags, int version) {
      int renderableType = 4;
      if(version >= 3) {
         renderableType |= 64;
      }

      int[] attribList = new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, renderableType, 12344, 0, 12344};
      if((flags & 1) != 0) {
         attribList[attribList.length - 3] = 12610;
         attribList[attribList.length - 2] = 1;
      }

      EGLConfig[] configs = new EGLConfig[1];
      int[] numConfigs = new int[1];
      if(!EGL14.eglChooseConfig(this.mEGLDisplay, attribList, 0, configs, 0, configs.length, numConfigs, 0)) {
         Log.w("Grafika", "unable to find RGB8888 / " + version + " EGLConfig");
         return null;
      } else {
         return configs[0];
      }
   }

   public void release() {
      if(this.mEGLDisplay != EGL14.EGL_NO_DISPLAY) {
         EGL14.eglMakeCurrent(this.mEGLDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
         EGL14.eglDestroyContext(this.mEGLDisplay, this.mEGLContext);
         EGL14.eglReleaseThread();
         EGL14.eglTerminate(this.mEGLDisplay);
      }

      this.mEGLDisplay = EGL14.EGL_NO_DISPLAY;
      this.mEGLContext = EGL14.EGL_NO_CONTEXT;
      this.mEGLConfig = null;
   }

   protected void finalize() throws Throwable {
      try {
         if(this.mEGLDisplay != EGL14.EGL_NO_DISPLAY) {
            Log.w("Grafika", "WARNING: EglCore was not explicitly released -- state may be leaked");
            this.release();
         }
      } finally {
         super.finalize();
      }

   }

   public void releaseSurface(EGLSurface eglSurface) {
      EGL14.eglDestroySurface(this.mEGLDisplay, eglSurface);
   }

   public EGLSurface createWindowSurface(Object surface) {
      if(!(surface instanceof Surface) && !(surface instanceof SurfaceTexture)) {
         throw new RuntimeException("invalid surface: " + surface);
      } else {
         int[] surfaceAttribs = new int[]{12344};
         EGLSurface eglSurface = EGL14.eglCreateWindowSurface(this.mEGLDisplay, this.mEGLConfig, surface, surfaceAttribs, 0);
         this.checkEglError("eglCreateWindowSurface");
         if(eglSurface == null) {
            throw new RuntimeException("surface was null");
         } else {
            return eglSurface;
         }
      }
   }

   public EGLSurface createOffscreenSurface(int width, int height) {
      int[] surfaceAttribs = new int[]{12375, width, 12374, height, 12344};
      EGLSurface eglSurface = EGL14.eglCreatePbufferSurface(this.mEGLDisplay, this.mEGLConfig, surfaceAttribs, 0);
      this.checkEglError("eglCreatePbufferSurface");
      if(eglSurface == null) {
         throw new RuntimeException("surface was null");
      } else {
         return eglSurface;
      }
   }

   public void makeCurrent(EGLSurface eglSurface) {
      if(this.mEGLDisplay == EGL14.EGL_NO_DISPLAY) {
         Log.d("Grafika", "NOTE: makeCurrent w/o display");
      }

      if(!EGL14.eglMakeCurrent(this.mEGLDisplay, eglSurface, eglSurface, this.mEGLContext)) {
         throw new RuntimeException("eglMakeCurrent failed");
      }
   }

   public void makeCurrent(EGLSurface drawSurface, EGLSurface readSurface) {
      if(this.mEGLDisplay == EGL14.EGL_NO_DISPLAY) {
         Log.d("Grafika", "NOTE: makeCurrent w/o display");
      }

      if(!EGL14.eglMakeCurrent(this.mEGLDisplay, drawSurface, readSurface, this.mEGLContext)) {
         throw new RuntimeException("eglMakeCurrent(draw,read) failed");
      }
   }

   public void makeNothingCurrent() {
      if(!EGL14.eglMakeCurrent(this.mEGLDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT)) {
         throw new RuntimeException("eglMakeCurrent failed");
      }
   }

   public boolean swapBuffers(EGLSurface eglSurface) {
      return EGL14.eglSwapBuffers(this.mEGLDisplay, eglSurface);
   }

   public void setPresentationTime(EGLSurface eglSurface, long nsecs) {
      EGLExt.eglPresentationTimeANDROID(this.mEGLDisplay, eglSurface, nsecs);
   }

   public boolean isCurrent(EGLSurface eglSurface) {
      return this.mEGLContext.equals(EGL14.eglGetCurrentContext()) && eglSurface.equals(EGL14.eglGetCurrentSurface(12377));
   }

   public int querySurface(EGLSurface eglSurface, int what) {
      int[] value = new int[1];
      EGL14.eglQuerySurface(this.mEGLDisplay, eglSurface, what, value, 0);
      return value[0];
   }

   public String queryString(int what) {
      return EGL14.eglQueryString(this.mEGLDisplay, what);
   }

   public int getGlVersion() {
      return this.mGlVersion;
   }

   public static void logCurrent(String msg) {
      EGLDisplay display = EGL14.eglGetCurrentDisplay();
      EGLContext context = EGL14.eglGetCurrentContext();
      EGLSurface surface = EGL14.eglGetCurrentSurface(12377);
      Log.i("Grafika", "Current EGL (" + msg + "): display=" + display + ", context=" + context + ", surface=" + surface);
   }

   private void checkEglError(String msg) {
      int error;
      if((error = EGL14.eglGetError()) != 12288) {
         throw new RuntimeException(msg + ": EGL error: 0x" + Integer.toHexString(error));
      }
   }
}
