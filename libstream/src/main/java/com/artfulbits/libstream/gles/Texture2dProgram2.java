//package com.example.test_libstream.gles;
//
//import android.opengl.GLES20;
//import android.util.Log;
//
//import java.nio.FloatBuffer;
//
//public class Texture2dProgram {
//
//    private static final String TAG = "Grafika";
//    private static final String VERTEX_SHADER = "uniform mat4 uMVPMatrix;\nuniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n";
//    private static final String FRAGMENT_SHADER_2D = "precision mediump float;\nvarying vec2 vTextureCoord;\nuniform sampler2D sTexture;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
//    private static final String FRAGMENT_SHADER_EXT = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
//    private static final String FRAGMENT_SHADER_EXT_BW = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n    vec4 tc = texture2D(sTexture, vTextureCoord);\n    float color = tc.r * 0.3 + tc.g * 0.59 + tc.b * 0.11;\n    gl_FragColor = vec4(color, color, color, 1.0);\n}\n";
//    public static final int KERNEL_SIZE = 9;
//    private static final String FRAGMENT_SHADER_EXT_FILT = "#extension GL_OES_EGL_image_external : require\n#define KERNEL_SIZE 9\nprecision highp float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nuniform float uKernel[KERNEL_SIZE];\nuniform vec2 uTexOffset[KERNEL_SIZE];\nuniform float uColorAdjust;\nvoid main() {\n    int i = 0;\n    vec4 sum = vec4(0.0);\n    if (vTextureCoord.x < vTextureCoord.y - 0.005) {\n        for (i = 0; i < KERNEL_SIZE; i++) {\n            vec4 texc = texture2D(sTexture, vTextureCoord + uTexOffset[i]);\n            sum += texc * uKernel[i];\n        }\n    sum += uColorAdjust;\n    } else if (vTextureCoord.x > vTextureCoord.y + 0.005) {\n        sum = texture2D(sTexture, vTextureCoord);\n    } else {\n        sum.r = 1.0;\n    }\n    gl_FragColor = sum;\n}\n";
//    private Texture2dProgram.ProgramType mProgramType;
//    private int mProgramHandle;
//    private int muMVPMatrixLoc;
//    private int muTexMatrixLoc;
//    private int muKernelLoc;
//    private int muTexOffsetLoc;
//    private int muColorAdjustLoc;
//    private int maPositionLoc;
//    private int maTextureCoordLoc;
//    private int mTextureTarget;
//    private float[] mKernel = new float[9];
//    private float[] mTexOffset;
//    private float mColorAdjust;
//
//
//    public Texture2dProgram(Texture2dProgram.ProgramType programType) {
//        this.mProgramType = programType;
//        switch (Texture2dProgram.NamelessClass308245922.$SwitchMap$com$wmspanel$libstream$gles$Texture2dProgram$ProgramType[programType.ordinal()]) {
//            case 1:
//                this.mTextureTarget = 3553;
//                this.mProgramHandle = GlUtil.createProgram("uniform mat4 uMVPMatrix;\nuniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n", "precision mediump float;\nvarying vec2 vTextureCoord;\nuniform sampler2D sTexture;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
//                break;
//            case 2:
//                this.mTextureTarget = '\u8d65';
//                this.mProgramHandle = GlUtil.createProgram("uniform mat4 uMVPMatrix;\nuniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
//                break;
//            case 3:
//                this.mTextureTarget = '\u8d65';
//                this.mProgramHandle = GlUtil.createProgram("uniform mat4 uMVPMatrix;\nuniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n    vec4 tc = texture2D(sTexture, vTextureCoord);\n    float color = tc.r * 0.3 + tc.g * 0.59 + tc.b * 0.11;\n    gl_FragColor = vec4(color, color, color, 1.0);\n}\n");
//                break;
//            case 4:
//                this.mTextureTarget = '\u8d65';
//                this.mProgramHandle = GlUtil.createProgram("uniform mat4 uMVPMatrix;\nuniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\n#define KERNEL_SIZE 9\nprecision highp float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nuniform float uKernel[KERNEL_SIZE];\nuniform vec2 uTexOffset[KERNEL_SIZE];\nuniform float uColorAdjust;\nvoid main() {\n    int i = 0;\n    vec4 sum = vec4(0.0);\n    if (vTextureCoord.x < vTextureCoord.y - 0.005) {\n        for (i = 0; i < KERNEL_SIZE; i++) {\n            vec4 texc = texture2D(sTexture, vTextureCoord + uTexOffset[i]);\n            sum += texc * uKernel[i];\n        }\n    sum += uColorAdjust;\n    } else if (vTextureCoord.x > vTextureCoord.y + 0.005) {\n        sum = texture2D(sTexture, vTextureCoord);\n    } else {\n        sum.r = 1.0;\n    }\n    gl_FragColor = sum;\n}\n");
//                break;
//            default:
//                throw new RuntimeException("Unhandled type " + programType);
//        }
//
//        if (this.mProgramHandle == 0) {
//            throw new RuntimeException("Unable to create program");
//        } else {
//            Log.d("Grafika", "Created program " + this.mProgramHandle + " (" + programType + ")");
//            this.maPositionLoc = GLES20.glGetAttribLocation(this.mProgramHandle, "aPosition");
//            GlUtil.checkLocation(this.maPositionLoc, "aPosition");
//            this.maTextureCoordLoc = GLES20.glGetAttribLocation(this.mProgramHandle, "aTextureCoord");
//            GlUtil.checkLocation(this.maTextureCoordLoc, "aTextureCoord");
//            this.muMVPMatrixLoc = GLES20.glGetUniformLocation(this.mProgramHandle, "uMVPMatrix");
//            GlUtil.checkLocation(this.muMVPMatrixLoc, "uMVPMatrix");
//            this.muTexMatrixLoc = GLES20.glGetUniformLocation(this.mProgramHandle, "uTexMatrix");
//            GlUtil.checkLocation(this.muTexMatrixLoc, "uTexMatrix");
//            this.muKernelLoc = GLES20.glGetUniformLocation(this.mProgramHandle, "uKernel");
//            if (this.muKernelLoc < 0) {
//                this.muKernelLoc = -1;
//                this.muTexOffsetLoc = -1;
//                this.muColorAdjustLoc = -1;
//            } else {
//                this.muTexOffsetLoc = GLES20.glGetUniformLocation(this.mProgramHandle, "uTexOffset");
//                GlUtil.checkLocation(this.muTexOffsetLoc, "uTexOffset");
//                this.muColorAdjustLoc = GLES20.glGetUniformLocation(this.mProgramHandle, "uColorAdjust");
//                GlUtil.checkLocation(this.muColorAdjustLoc, "uColorAdjust");
//                this.setKernel(new float[]{0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F}, 0.0F);
//                this.setTexSize(256, 256);
//            }
//
//        }
//    }
//
//    public void release() {
//        Log.d("Grafika", "deleting program " + this.mProgramHandle);
//        GLES20.glDeleteProgram(this.mProgramHandle);
//        this.mProgramHandle = -1;
//    }
//
//    public Texture2dProgram.ProgramType getProgramType() {
//        return this.mProgramType;
//    }
//
//    public int createTextureObject() {
//        int[] textures = new int[1];
//        GLES20.glGenTextures(1, textures, 0);
//        GlUtil.checkGlError("glGenTextures");
//        int texId = textures[0];
//        GLES20.glBindTexture(this.mTextureTarget, texId);
//        GlUtil.checkGlError("glBindTexture " + texId);
//        GLES20.glTexParameterf('\u8d65', 10241, 9728.0F);
//        GLES20.glTexParameterf('\u8d65', 10240, 9729.0F);
//        GLES20.glTexParameteri('\u8d65', 10242, '\u812f');
//        GLES20.glTexParameteri('\u8d65', 10243, '\u812f');
//        GlUtil.checkGlError("glTexParameter");
//        return texId;
//    }
//
//    public void setKernel(float[] values, float colorAdj) {
//        if (values.length != 9) {
//            throw new IllegalArgumentException("Kernel size is " + values.length + " vs. " + 9);
//        } else {
//            System.arraycopy(values, 0, this.mKernel, 0, 9);
//            this.mColorAdjust = colorAdj;
//        }
//    }
//
//    public void setTexSize(int width, int height) {
//        float rw = 1.0F / (float) width;
//        float rh = 1.0F / (float) height;
//        this.mTexOffset = new float[]{-rw, -rh, 0.0F, -rh, rw, -rh, -rw, 0.0F, 0.0F, 0.0F, rw, 0.0F, -rw, rh, 0.0F, rh, rw, rh};
//    }
//
//    public void draw(float[] mvpMatrix, FloatBuffer vertexBuffer, int firstVertex, int vertexCount, int coordsPerVertex, int vertexStride, float[] texMatrix, FloatBuffer texBuffer, int textureId, int texStride) {
//        GlUtil.checkGlError("draw start");
//        GLES20.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
//        GlUtil.checkGlError("glClearColor");
//        GLES20.glClear(16640);
//        GlUtil.checkGlError("glClear");
//        GLES20.glUseProgram(this.mProgramHandle);
//        GlUtil.checkGlError("glUseProgram");
//        GLES20.glActiveTexture('\u84c0');
//        GLES20.glBindTexture(this.mTextureTarget, textureId);
//        GLES20.glUniformMatrix4fv(this.muMVPMatrixLoc, 1, false, mvpMatrix, 0);
//        GlUtil.checkGlError("glUniformMatrix4fv");
//        GLES20.glUniformMatrix4fv(this.muTexMatrixLoc, 1, false, texMatrix, 0);
//        GlUtil.checkGlError("glUniformMatrix4fv");
//        GLES20.glEnableVertexAttribArray(this.maPositionLoc);
//        GlUtil.checkGlError("glEnableVertexAttribArray");
//        GLES20.glVertexAttribPointer(this.maPositionLoc, coordsPerVertex, 5126, false, vertexStride, vertexBuffer);
//        GlUtil.checkGlError("glVertexAttribPointer");
//        GLES20.glEnableVertexAttribArray(this.maTextureCoordLoc);
//        GlUtil.checkGlError("glEnableVertexAttribArray");
//        GLES20.glVertexAttribPointer(this.maTextureCoordLoc, 2, 5126, false, texStride, texBuffer);
//        GlUtil.checkGlError("glVertexAttribPointer");
//        if (this.muKernelLoc >= 0) {
//            GLES20.glUniform1fv(this.muKernelLoc, 9, this.mKernel, 0);
//            GLES20.glUniform2fv(this.muTexOffsetLoc, 9, this.mTexOffset, 0);
//            GLES20.glUniform1f(this.muColorAdjustLoc, this.mColorAdjust);
//        }
//
//        GLES20.glDrawArrays(5, firstVertex, vertexCount);
//        GlUtil.checkGlError("glDrawArrays");
//        GLES20.glDisableVertexAttribArray(this.maPositionLoc);
//        GLES20.glDisableVertexAttribArray(this.maTextureCoordLoc);
//        GLES20.glBindTexture(this.mTextureTarget, 0);
//        GLES20.glUseProgram(0);
//    }
//
//    //   public static enum ProgramType {
////
////      TEXTURE_2D("TEXTURE_2D", 0),
////      TEXTURE_EXT("TEXTURE_EXT", 1),
////      TEXTURE_EXT_BW("TEXTURE_EXT_BW", 2),
////      TEXTURE_EXT_FILT("TEXTURE_EXT_FILT", 3);
////      // $FF: synthetic field
////      private static final Texture2dProgram.ProgramType[] $VALUES = new Texture2dProgram.ProgramType[]{TEXTURE_2D, TEXTURE_EXT, TEXTURE_EXT_BW, TEXTURE_EXT_FILT};
////
////
////      private ProgramType(String var1, int var2) {}
////
////   }
//
//    public enum ProgramType {
//        TEXTURE_2D, TEXTURE_EXT, TEXTURE_EXT_BW, TEXTURE_EXT_NIGHT, TEXTURE_EXT_CHROMA_KEY,
//        TEXTURE_EXT_SQUEEZE, TEXTURE_EXT_TWIRL, TEXTURE_EXT_TUNNEL, TEXTURE_EXT_BULGE,
//        TEXTURE_EXT_DENT, TEXTURE_EXT_FISHEYE, TEXTURE_EXT_STRETCH, TEXTURE_EXT_MIRROR,
//        TEXTURE_EXT_FILT
//    }
//
//    // $FF: synthetic class
//    static class NamelessClass308245922 {
//
//        // $FF: synthetic field
//        static final int[] $SwitchMap$com$wmspanel$libstream$gles$Texture2dProgram$ProgramType = new int[Texture2dProgram.ProgramType.values().length];
//
//
//        static {
//            try {
//                $SwitchMap$com$wmspanel$libstream$gles$Texture2dProgram$ProgramType[Texture2dProgram.ProgramType.TEXTURE_2D.ordinal()] = 1;
//            } catch (NoSuchFieldError var4) {
//                ;
//            }
//
//            try {
//                $SwitchMap$com$wmspanel$libstream$gles$Texture2dProgram$ProgramType[Texture2dProgram.ProgramType.TEXTURE_EXT.ordinal()] = 2;
//            } catch (NoSuchFieldError var3) {
//                ;
//            }
//
//            try {
//                $SwitchMap$com$wmspanel$libstream$gles$Texture2dProgram$ProgramType[Texture2dProgram.ProgramType.TEXTURE_EXT_BW.ordinal()] = 3;
//            } catch (NoSuchFieldError var2) {
//                ;
//            }
//
//            try {
//                $SwitchMap$com$wmspanel$libstream$gles$Texture2dProgram$ProgramType[Texture2dProgram.ProgramType.TEXTURE_EXT_FILT.ordinal()] = 4;
//            } catch (NoSuchFieldError var1) {
//                ;
//            }
//
//        }
//    }
//}
