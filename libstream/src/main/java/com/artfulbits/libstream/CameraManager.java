package com.artfulbits.libstream;

import android.content.Context;
import java.util.List;

abstract class CameraManager {

   private static final String TAG = "CameraManager";


   abstract List getCameraList(Context var1);

   abstract Streamer.CameraInfo getCameraInfo(Context var1, String var2);
}
