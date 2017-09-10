package com.artfulbits.libstream;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.MediaCodec;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import android.view.SurfaceHolder;
import java.util.ArrayList;
import java.util.List;

@TargetApi(21)
class CameraManager21 extends CameraManager {

   private static final String TAG = "CameraManager21";


   List getCameraList(Context context) {
      ArrayList cameraList = new ArrayList();

      try {
         android.hardware.camera2.CameraManager e = (android.hardware.camera2.CameraManager)context.getSystemService("camera");
         String[] cameraIdList = e.getCameraIdList();
         String[] arr$ = cameraIdList;
         int len$ = cameraIdList.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String cameraId = arr$[i$];
            Streamer.CameraInfo camera = this.getCameraInfo(context, cameraId);
            if(null != camera) {
               cameraList.add(camera);
            }
         }

         return cameraList;
      } catch (CameraAccessException var10) {
         Log.e("CameraManager21", Log.getStackTraceString(var10));
         return null;
      }
   }

   Streamer.CameraInfo getCameraInfo(Context context, String cameraId) {
      android.hardware.camera2.CameraManager cameraManager = (android.hardware.camera2.CameraManager)context.getSystemService("camera");

      try {
         Streamer.CameraInfo cameraInfo = new Streamer.CameraInfo();
         cameraInfo.cameraId = cameraId;
         CameraCharacteristics e = cameraManager.getCameraCharacteristics(cameraId);
         StreamConfigurationMap map = (StreamConfigurationMap)e.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
         Size[] recordSizes = map.getOutputSizes(MediaCodec.class);
         cameraInfo.recordSizes = new Streamer.Size[recordSizes.length];

         for(int formats = 0; formats < recordSizes.length; ++formats) {
            cameraInfo.recordSizes[formats] = new Streamer.Size(recordSizes[formats].getWidth(), recordSizes[formats].getHeight());
         }

         int[] var13 = map.getOutputFormats();
         Size[] previewSizes = map.getOutputSizes(SurfaceHolder.class);
         cameraInfo.previewSizes = new Streamer.Size[previewSizes.length];

         for(int fpsRanges = 0; fpsRanges < previewSizes.length; ++fpsRanges) {
            Log.d("", " " + previewSizes[fpsRanges].getWidth() + "x" + previewSizes[fpsRanges].getHeight());
            cameraInfo.previewSizes[fpsRanges] = new Streamer.Size(previewSizes[fpsRanges].getWidth(), previewSizes[fpsRanges].getHeight());
         }

         if(((Integer)e.get(CameraCharacteristics.LENS_FACING)).intValue() == 1) {
            cameraInfo.lensFacingBack = true;
         } else {
            cameraInfo.lensFacingBack = false;
         }

         Range[] var14 = (Range[])e.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
         cameraInfo.fpsRanges = new Streamer.FpsRange[var14.length];

         for(int i = 0; i < var14.length; ++i) {
            cameraInfo.fpsRanges[i] = new Streamer.FpsRange((float)((Integer)var14[i].getLower()).intValue(), (float)((Integer)var14[i].getUpper()).intValue());
         }

         return cameraInfo;
      } catch (CameraAccessException var12) {
         Log.e("CameraManager21", Log.getStackTraceString(var12));
         return null;
      }
   }
}
