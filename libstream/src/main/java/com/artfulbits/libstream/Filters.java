package com.artfulbits.libstream;

//public class FILTER {
//    public static final int FILTER_BLACK_WHITE = 1;
//    public static final int FILTER_NONE = 0;
//    public static final int FILTER_BLUR = 2;
//    public static final int FILTER_SHARPEN = 3;
//    public static final int FILTER_EDGE_DETECT = 4;
//    public static final int FILTER_EMBOSS = 5;
//
//}

import android.util.Log;

import com.artfulbits.libstream.gles.FullFrameRect;
import com.artfulbits.libstream.gles.Texture2dProgram;

/**
 * This class matches descriptive final int
 * variables to Texture2dProgram.ProgramType
 *
 * @hide
 */
public class Filters {
    private static final String TAG = "Filters";
    private static final boolean VERBOSE = false;

    public static enum FILTERS{
        FILTER_NONE,
        FILTER_BLACK_WHITE,
        FILTER_NIGHT,
        FILTER_CHROMA_KEY,
        FILTER_BLUR,
        FILTER_SHARPEN,
        FILTER_EDGE_DETECT,
        FILTER_EMBOSS,
        FILTER_SQUEEZE,
        FILTER_TWIRL,
        FILTER_TUNNEL,
        FILTER_BULGE,
        FILTER_DENT,
        FILTER_FISHEYE,
        FILTER_STRETCH,
        FILTER_MIRROR,
        FILTER_BEAUTY
    }
    public static final int FILTER_NONE = 0;
    public static final int FILTER_BLACK_WHITE = 1;
    public static final int FILTER_NIGHT = 2;
    public static final int FILTER_CHROMA_KEY = 3;
    public static final int FILTER_BLUR = 4;
    public static final int FILTER_SHARPEN = 5;
    public static final int FILTER_EDGE_DETECT = 6;
    public static final int FILTER_EMBOSS = 7;
    public static final int FILTER_SQUEEZE = 8;
    public static final int FILTER_TWIRL = 9;
    public static final int FILTER_TUNNEL = 10;
    public static final int FILTER_BULGE = 11;
    public static final int FILTER_DENT = 12;
    public static final int FILTER_FISHEYE = 13;
    public static final int FILTER_STRETCH = 14;
    public static final int FILTER_MIRROR = 15;
    public static final int FILTER_BEAUTY = 16;
    public static final int FILTER_BILATERAL = 17;

    /**
     * Updates the filter on the provided FullFrameRect
     *
     * @return the int code of the new filter
     */
    public static void updateFilter(FullFrameRect rect, int newFilter) {
        Texture2dProgram.ProgramType programType;
        float[] kernel = null;
        float colorAdj = 0.0f;

        if (VERBOSE) Log.d(TAG, "Updating filter to " + newFilter);
        switch (newFilter) {
            case FILTER_NONE:
                programType = Texture2dProgram.ProgramType.TEXTURE_EXT;
                break;
            case FILTER_BLACK_WHITE:
                // (In a previous version the TEXTURE_EXT_BW variant was enabled by a flag called
                // ROSE_COLORED_GLASSES, because the shader set the red channel to the B&W color
                // and green/blue to zero.)
                programType = Texture2dProgram.ProgramType.TEXTURE_EXT_BW;
                break;
            case FILTER_NIGHT:
                programType = Texture2dProgram.ProgramType.TEXTURE_EXT_NIGHT;
                break;
            case FILTER_CHROMA_KEY:
                programType = Texture2dProgram.ProgramType.TEXTURE_EXT_CHROMA_KEY;
                break;
            case FILTER_SQUEEZE:
                programType = Texture2dProgram.ProgramType.TEXTURE_EXT_SQUEEZE;
                break;
            case FILTER_TWIRL:
                programType = Texture2dProgram.ProgramType.TEXTURE_EXT_TWIRL;
                break;
            case FILTER_TUNNEL:
                programType = Texture2dProgram.ProgramType.TEXTURE_EXT_TUNNEL;
                break;
            case FILTER_BULGE:
                programType = Texture2dProgram.ProgramType.TEXTURE_EXT_BULGE;
                break;
            case FILTER_DENT:
                programType = Texture2dProgram.ProgramType.TEXTURE_EXT_DENT;
                break;
            case FILTER_FISHEYE:
                programType = Texture2dProgram.ProgramType.TEXTURE_EXT_FISHEYE;
                break;
            case FILTER_STRETCH:
                programType = Texture2dProgram.ProgramType.TEXTURE_EXT_STRETCH;
                break;
            case FILTER_MIRROR:
                programType = Texture2dProgram.ProgramType.TEXTURE_EXT_MIRROR;
                break;
            case FILTER_BEAUTY:
                programType = Texture2dProgram.ProgramType.TEXTURE_EXT_BEAUTY;
                break;
            case FILTER_BILATERAL:
                programType = Texture2dProgram.ProgramType.TEXTURE_EXT_BILATERAL;
                break;
            case FILTER_BLUR:
                programType = Texture2dProgram.ProgramType.TEXTURE_EXT_FILT;
                kernel = new float[]{
                        1f / 16f, 2f / 16f, 1f / 16f,
                        2f / 16f, 4f / 16f, 2f / 16f,
                        1f / 16f, 2f / 16f, 1f / 16f};
                break;
            case FILTER_SHARPEN:
                programType = Texture2dProgram.ProgramType.TEXTURE_EXT_FILT;
                kernel = new float[]{
                        0f, -1f, 0f,
                        -1f, 5f, -1f,
                        0f, -1f, 0f};
                break;
            case FILTER_EDGE_DETECT:
                programType = Texture2dProgram.ProgramType.TEXTURE_EXT_FILT;
                kernel = new float[]{
                        -1f, -1f, -1f,
                        -1f, 8f, -1f,
                        -1f, -1f, -1f};
                break;
            case FILTER_EMBOSS:
                programType = Texture2dProgram.ProgramType.TEXTURE_EXT_FILT;
                kernel = new float[]{
                        2f, 0f, 0f,
                        0f, -1f, 0f,
                        0f, 0f, -1f};
                colorAdj = 0.5f;
                break;
            default:
                throw new RuntimeException("Unknown filter mode " + newFilter);
        }

        // Do we need a whole new program?  (We want to avoid doing this if we don't have
        // too -- compiling a program could be expensive.)
        if (programType != rect.getProgram().getProgramType()) {
            rect.changeProgram(new Texture2dProgram(programType));
        }

        // Update the filter kernel (if any).
        if (kernel != null) {
            rect.getProgram().setKernel(kernel, colorAdj);
        }

    }
}