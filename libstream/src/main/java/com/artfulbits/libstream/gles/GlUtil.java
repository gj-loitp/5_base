package com.artfulbits.libstream.gles;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GlUtil {

   public static final String TAG = "Grafika";
   public static final float[] IDENTITY_MATRIX = new float[16];
   private static final int SIZEOF_FLOAT = 4;


   public static int createProgram(String vertexSource, String fragmentSource) {
      int vertexShader = loadShader('\u8b31', vertexSource);
      if(vertexShader == 0) {
         return 0;
      } else {
         int pixelShader = loadShader('\u8b30', fragmentSource);
         if(pixelShader == 0) {
            return 0;
         } else {
            int program = GLES20.glCreateProgram();
            checkGlError("glCreateProgram");
            if(program == 0) {
               Log.e("Grafika", "Could not create program");
            }

            GLES20.glAttachShader(program, vertexShader);
            checkGlError("glAttachShader");
            GLES20.glAttachShader(program, pixelShader);
            checkGlError("glAttachShader");
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, '\u8b82', linkStatus, 0);
            if(linkStatus[0] != 1) {
               Log.e("Grafika", "Could not link program: ");
               Log.e("Grafika", GLES20.glGetProgramInfoLog(program));
               GLES20.glDeleteProgram(program);
               program = 0;
            }

            return program;
         }
      }
   }

   public static int loadShader(int shaderType, String source) {
      int shader = GLES20.glCreateShader(shaderType);
      checkGlError("glCreateShader type=" + shaderType);
      GLES20.glShaderSource(shader, source);
      GLES20.glCompileShader(shader);
      int[] compiled = new int[1];
      GLES20.glGetShaderiv(shader, '\u8b81', compiled, 0);
      if(compiled[0] == 0) {
         Log.e("Grafika", "Could not compile shader " + shaderType + ":");
         Log.e("Grafika", " " + GLES20.glGetShaderInfoLog(shader));
         GLES20.glDeleteShader(shader);
         shader = 0;
      }

      return shader;
   }

   public static void checkGlError(String op) {
      int error = GLES20.glGetError();
      if(error != 0) {
         String msg = op + ": glError 0x" + Integer.toHexString(error);
         Log.e("Grafika", msg);
         throw new RuntimeException(msg);
      }
   }

   public static void checkLocation(int location, String label) {
      if(location < 0) {
         throw new RuntimeException("Unable to locate \'" + label + "\' in program");
      }
   }

   public static int createImageTexture(ByteBuffer data, int width, int height, int format) {
      int[] textureHandles = new int[1];
      GLES20.glGenTextures(1, textureHandles, 0);
      int textureHandle = textureHandles[0];
      checkGlError("glGenTextures");
      GLES20.glBindTexture(3553, textureHandle);
      GLES20.glTexParameteri(3553, 10241, 9729);
      GLES20.glTexParameteri(3553, 10240, 9729);
      checkGlError("loadImageTexture");
      GLES20.glTexImage2D(3553, 0, format, width, height, 0, format, 5121, data);
      checkGlError("loadImageTexture");
      return textureHandle;
   }

   public static FloatBuffer createFloatBuffer(float[] coords) {
      ByteBuffer bb = ByteBuffer.allocateDirect(coords.length * 4);
      bb.order(ByteOrder.nativeOrder());
      FloatBuffer fb = bb.asFloatBuffer();
      fb.put(coords);
      fb.position(0);
      return fb;
   }

   public static void logVersionInfo() {
      Log.i("Grafika", "vendor  : " + GLES20.glGetString(7936));
      Log.i("Grafika", "renderer: " + GLES20.glGetString(7937));
      Log.i("Grafika", "version : " + GLES20.glGetString(7938));
   }

   static {
      Matrix.setIdentityM(IDENTITY_MATRIX, 0);
   }
}
