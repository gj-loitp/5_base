package com.artfulbits.libstream.gles;

import android.graphics.SurfaceTexture;
import android.view.Surface;

public class WindowSurface extends EglSurfaceBase {

   private Surface mSurface;
   private boolean mReleaseSurface;


   public WindowSurface(EglCore eglCore, Surface surface, boolean releaseSurface) {
      super(eglCore);
      this.createWindowSurface(surface);
      this.mSurface = surface;
      this.mReleaseSurface = releaseSurface;
   }

   public WindowSurface(EglCore eglCore, SurfaceTexture surfaceTexture) {
      super(eglCore);
      this.createWindowSurface(surfaceTexture);
   }

   public void release() {
      this.releaseEglSurface();
      if(this.mSurface != null) {
         if(this.mReleaseSurface) {
            this.mSurface.release();
         }

         this.mSurface = null;
      }

   }

   public void recreate(EglCore newEglCore) {
      if(this.mSurface == null) {
         throw new RuntimeException("not yet implemented for SurfaceTexture");
      } else {
         this.mEglCore = newEglCore;
         this.createWindowSurface(this.mSurface);
      }
   }
}
