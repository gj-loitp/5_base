package com.artfulbits.libstream;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

class CameraManager16 extends CameraManager {

   private static final String TAG = "CameraManager16";


   List getCameraList(Context context) {
      ArrayList cameraInfoList = new ArrayList();
      int numberOfCameras = Camera.getNumberOfCameras();

      for(int i = 0; i < numberOfCameras; ++i) {
         Streamer.CameraInfo cameraInfo = this.getCameraInfo(i);
         if(cameraInfo != null) {
            cameraInfoList.add(cameraInfo);
         }
      }

      return cameraInfoList;
   }

   Streamer.CameraInfo getCameraInfo(Context context, String cameraIdStr) {
      int cameraId = Integer.valueOf(cameraIdStr).intValue();
      return this.getCameraInfo(cameraId);
   }

   private boolean getCameraFacing(int cameraId) {
      CameraInfo cameraInfo = new CameraInfo();
      Camera.getCameraInfo(cameraId, cameraInfo);
      return cameraInfo.facing == 0;
   }

   private Streamer.CameraInfo getCameraInfo(int cameraId) {
      Streamer.CameraInfo cameraInfo = null;
      Camera camera = null;

      try {
         camera = Camera.open(cameraId);
         Parameters e = camera.getParameters();
         cameraInfo = new Streamer.CameraInfo();
         cameraInfo.cameraId = Integer.toString(cameraId);
         CameraInfo info = new CameraInfo();
         Camera.getCameraInfo(cameraId, info);
         cameraInfo.lensFacingBack = info.facing == 0;
         cameraInfo.orientation = info.orientation;
         List previewSizes = e.getSupportedPreviewSizes();
         if(null != previewSizes) {
            cameraInfo.previewSizes = new Streamer.Size[previewSizes.size()];

            for(int fpsRanges = 0; fpsRanges < previewSizes.size(); ++fpsRanges) {
               cameraInfo.previewSizes[fpsRanges] = new Streamer.Size(((Size)previewSizes.get(fpsRanges)).width, ((Size)previewSizes.get(fpsRanges)).height);
            }
         }

         cameraInfo.recordSizes = cameraInfo.previewSizes;
         List var10 = e.getSupportedPreviewFpsRange();
         cameraInfo.fpsRanges = new Streamer.FpsRange[var10.size()];

         for(int i = 0; i < var10.size(); ++i) {
            cameraInfo.fpsRanges[i] = new Streamer.FpsRange((float)((int[])var10.get(i))[0], (float)((int[])var10.get(i))[1]);
         }
      } catch (Exception var9) {
         Log.e("", "failed to get camera info, cameraId=" + cameraId);
         Log.e("CameraManager16", Log.getStackTraceString(var9));
      }

      if(null != camera) {
         camera.release();
      }

      return cameraInfo;
   }
}
