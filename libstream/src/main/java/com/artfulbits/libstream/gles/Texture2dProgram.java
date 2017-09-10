/*
 * Copyright 2014 Google Inc. All rights reserved.
 */
/*w  ww .  j a v  a  2  s.c om
        * Licensed under the Apache License, Version 2.0 (the "License");
        * you may not use this file except in compliance with the License.
        * You may obtain a copy of the License at
        *
        *      http://www.apache.org/licenses/LICENSE-2.0
        *
        * Unless required by applicable law or agreed to in writing, software
        * distributed under the License is distributed on an "AS IS" BASIS,
        * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        * See the License for the specific language governing permissions and
        * limitations under the License.
        */

package com.artfulbits.libstream.gles;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.util.Log;
import android.view.MotionEvent;

import java.nio.FloatBuffer;

/**
 * GL program and supporting functions for textured 2D shapes.
 *
 * @hide
 */
public class Texture2dProgram {
    private static final String TAG = "Texture2dProgram";

    public enum ProgramType {
        TEXTURE_2D, TEXTURE_EXT, TEXTURE_EXT_BW, TEXTURE_EXT_NIGHT, TEXTURE_EXT_CHROMA_KEY,
        TEXTURE_EXT_SQUEEZE, TEXTURE_EXT_TWIRL, TEXTURE_EXT_TUNNEL, TEXTURE_EXT_BULGE,
        TEXTURE_EXT_DENT, TEXTURE_EXT_FISHEYE, TEXTURE_EXT_STRETCH, TEXTURE_EXT_MIRROR,
        TEXTURE_EXT_FILT,TEXTURE_EXT_BEAUTY, TEXTURE_EXT_BILATERAL
    }

    // Simple fragment shader for use with "normal" 2D textures.
    private static final String FRAGMENT_SHADER_2D =
            "precision mediump float;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "uniform sampler2D sTexture;\n" +
                    "void main() {\n" +
                    "    gl_FragColor = texture2D(sTexture, vTextureCoord);\n" +
                    "}\n";

    // Simple fragment shader for use with external 2D textures (e.g. what we get from
    // SurfaceTexture).
    private static final String FRAGMENT_SHADER_EXT =
            "#extension GL_OES_EGL_image_external : require\n" +
                    "precision mediump float;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "uniform samplerExternalOES sTexture;\n" +
                    "void main() {\n" +
                    "    gl_FragColor = texture2D(sTexture, vTextureCoord);\n" +
                    "}\n";

    // Fragment shader that converts color to black & white with a simple transformation.
    private static final String FRAGMENT_SHADER_EXT_BW =
            "#extension GL_OES_EGL_image_external : require\n" +
                    "precision mediump float;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "uniform samplerExternalOES sTexture;\n" +
                    "void main() {\n" +
                    "    vec4 tc = texture2D(sTexture, vTextureCoord);\n" +
                    "    float color = tc.r * 0.3 + tc.g * 0.59 + tc.b * 0.11;\n" +
                    "    gl_FragColor = vec4(color, color, color, 1.0);\n" +
                    "}\n";

    // Fragment shader that attempts to produce a high contrast image
    private static final String FRAGMENT_SHADER_EXT_NIGHT =
            "#extension GL_OES_EGL_image_external : require\n" +
                    "precision mediump float;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "uniform samplerExternalOES sTexture;\n" +
                    "void main() {\n" +
                    "    vec4 tc = texture2D(sTexture, vTextureCoord);\n" +
                    "    float color = ((tc.r * 0.3 + tc.g * 0.59 + tc.b * 0.11) - 0.5 * 1.5) + 0.8;\n" +
                    "    gl_FragColor = vec4(color, color + 0.15, color, 1.0);\n" +
                    "}\n";

    // Fragment shader that applies a Chroma Key effect, making green pixels transparent
    private static final String FRAGMENT_SHADER_EXT_CHROMA_KEY =
            "#extension GL_OES_EGL_image_external : require\n" +
                    "precision mediump float;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "uniform samplerExternalOES sTexture;\n" +
                    "void main() {\n" +
                    "    vec4 tc = texture2D(sTexture, vTextureCoord);\n" +
                    "    float color = ((tc.r * 0.3 + tc.g * 0.59 + tc.b * 0.11) - 0.5 * 1.5) + 0.8;\n" +
                    "    if(tc.g > 0.6 && tc.b < 0.6 && tc.r < 0.6){ \n" +
                    "        gl_FragColor = vec4(0, 0, 0, 0.0);\n" +
                    "    }else{ \n" +
                    "        gl_FragColor = texture2D(sTexture, vTextureCoord);\n" +
                    "    }\n" +
                    "}\n";

    private static final String FRAGMENT_SHADER_SQUEEZE =
            "#extension GL_OES_EGL_image_external : require\n" +
                    "precision mediump float;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "uniform samplerExternalOES sTexture;\n" +
                    "uniform vec2 uPosition;\n" +
                    "void main() {\n" +
                    "    vec2 texCoord = vTextureCoord.xy;\n" +
                    "    vec2 normCoord = 2.0 * texCoord - 1.0;\n" +
                    "    float r = length(normCoord); // to polar coords \n" +
                    "    float phi = atan(normCoord.y + uPosition.y, normCoord.x + uPosition.x); // to polar coords \n" +
                    "    r = pow(r, 1.0/1.8) * 0.8;\n" +  // Squeeze it
                    "    normCoord.x = r * cos(phi); \n" +
                    "    normCoord.y = r * sin(phi); \n" +
                    "    texCoord = normCoord / 2.0 + 0.5;\n" +
                    "    gl_FragColor = texture2D(sTexture, texCoord);\n" +
                    "}\n";

    private static final String FRAGMENT_SHADER_TWIRL =
            "#extension GL_OES_EGL_image_external : require\n" +
                    "precision mediump float;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "uniform samplerExternalOES sTexture;\n" +
                    "uniform vec2 uPosition;\n" +
                    "void main() {\n" +
                    "    vec2 texCoord = vTextureCoord.xy;\n" +
                    "    vec2 normCoord = 2.0 * texCoord - 1.0;\n" +
                    "    float r = length(normCoord); // to polar coords \n" +
                    "    float phi = atan(normCoord.y + uPosition.y, normCoord.x + uPosition.x); // to polar coords \n" +
                    "    phi = phi + (1.0 - smoothstep(-0.5, 0.5, r)) * 4.0;\n" + // Twirl it
                    "    normCoord.x = r * cos(phi); \n" +
                    "    normCoord.y = r * sin(phi); \n" +
                    "    texCoord = normCoord / 2.0 + 0.5;\n" +
                    "    gl_FragColor = texture2D(sTexture, texCoord);\n" +
                    "}\n";

    private static final String FRAGMENT_SHADER_TUNNEL =
            "#extension GL_OES_EGL_image_external : require\n" +
                    "precision mediump float;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "uniform samplerExternalOES sTexture;\n" +
                    "uniform vec2 uPosition;\n" +
                    "void main() {\n" +
                    "    vec2 texCoord = vTextureCoord.xy;\n" +
                    "    vec2 normCoord = 2.0 * texCoord - 1.0;\n" +
                    "    float r = length(normCoord); // to polar coords \n" +
                    "    float phi = atan(normCoord.y + uPosition.y, normCoord.x + uPosition.x); // to polar coords \n" +
                    "    if (r > 0.5) r = 0.5;\n" + // Tunnel
                    "    normCoord.x = r * cos(phi); \n" +
                    "    normCoord.y = r * sin(phi); \n" +
                    "    texCoord = normCoord / 2.0 + 0.5;\n" +
                    "    gl_FragColor = texture2D(sTexture, texCoord);\n" +
                    "}\n";

    private static final String FRAGMENT_SHADER_BULGE =
            "#extension GL_OES_EGL_image_external : require\n" +
                    "precision mediump float;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "uniform samplerExternalOES sTexture;\n" +
                    "uniform vec2 uPosition;\n" +
                    "void main() {\n" +
                    "    vec2 texCoord = vTextureCoord.xy;\n" +
                    "    vec2 normCoord = 2.0 * texCoord - 1.0;\n" +
                    "    float r = length(normCoord); // to polar coords \n" +
                    "    float phi = atan(normCoord.y + uPosition.y, normCoord.x + uPosition.x); // to polar coords \n" +
                    "    r = r * smoothstep(-0.1, 0.5, r);\n" + // Bulge
                    "    normCoord.x = r * cos(phi); \n" +
                    "    normCoord.y = r * sin(phi); \n" +
                    "    texCoord = normCoord / 2.0 + 0.5;\n" +
                    "    gl_FragColor = texture2D(sTexture, texCoord);\n" +
                    "}\n";

    private static final String FRAGMENT_SHADER_DENT =
            "#extension GL_OES_EGL_image_external : require\n" +
                    "precision mediump float;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "uniform samplerExternalOES sTexture;\n" +
                    "uniform vec2 uPosition;\n" +
                    "void main() {\n" +
                    "    vec2 texCoord = vTextureCoord.xy;\n" +
                    "    vec2 normCoord = 2.0 * texCoord - 1.0;\n" +
                    "    float r = length(normCoord); // to polar coords \n" +
                    "    float phi = atan(normCoord.y + uPosition.y, normCoord.x + uPosition.x); // to polar coords \n" +
                    "    r = 2.0 * r - r * smoothstep(0.0, 0.7, r);\n" + // Dent
                    "    normCoord.x = r * cos(phi); \n" +
                    "    normCoord.y = r * sin(phi); \n" +
                    "    texCoord = normCoord / 2.0 + 0.5;\n" +
                    "    gl_FragColor = texture2D(sTexture, texCoord);\n" +
                    "}\n";

    private static final String FRAGMENT_SHADER_FISHEYE =
            "#extension GL_OES_EGL_image_external : require\n" +
                    "precision mediump float;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "uniform samplerExternalOES sTexture;\n" +
                    "uniform vec2 uPosition;\n" +
                    "void main() {\n" +
                    "    vec2 texCoord = vTextureCoord.xy;\n" +
                    "    vec2 normCoord = 2.0 * texCoord - 1.0;\n" +
                    "    float r = length(normCoord); // to polar coords \n" +
                    "    float phi = atan(normCoord.y + uPosition.y, normCoord.x + uPosition.x); // to polar coords \n" +
                    "    r = r * r / sqrt(2.0);\n" + // Fisheye
                    "    normCoord.x = r * cos(phi); \n" +
                    "    normCoord.y = r * sin(phi); \n" +
                    "    texCoord = normCoord / 2.0 + 0.5;\n" +
                    "    gl_FragColor = texture2D(sTexture, texCoord);\n" +
                    "}\n";

    private static final String FRAGMENT_SHADER_STRETCH =
            "#extension GL_OES_EGL_image_external : require\n" +
                    "precision mediump float;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "uniform samplerExternalOES sTexture;\n" +
                    "uniform vec2 uPosition;\n" +
                    "void main() {\n" +
                    "    vec2 texCoord = vTextureCoord.xy;\n" +
                    "    vec2 normCoord = 2.0 * texCoord - 1.0;\n" +
                    "    vec2 s = sign(normCoord + uPosition);\n" +
                    "    normCoord = abs(normCoord);\n" +
                    "    normCoord = 0.5 * normCoord + 0.5 * smoothstep(0.25, 0.5, normCoord) * normCoord;\n" +
                    "    normCoord = s * normCoord;\n" +
                    "    texCoord = normCoord / 2.0 + 0.5;\n" +
                    "    gl_FragColor = texture2D(sTexture, texCoord);\n" +
                    "}\n";

    private static final String FRAGMENT_SHADER_MIRROR =
            "#extension GL_OES_EGL_image_external : require\n" +
                    "precision mediump float;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "uniform samplerExternalOES sTexture;\n" +
                    "uniform vec2 uPosition;\n" +
                    "void main() {\n" +
                    "    vec2 texCoord = vTextureCoord.xy;\n" +
                    "    vec2 normCoord = 2.0 * texCoord - 1.0;\n" +
                    "    normCoord.x = normCoord.x * sign(normCoord.x + uPosition.x);\n" +
                    "    texCoord = normCoord / 2.0 + 0.5;\n" +
                    "    gl_FragColor = texture2D(sTexture, texCoord);\n" +
                    "}\n";

    private static final String VERTEX_SHADER_BEAUTY =
            "precision mediump float;\n" +
                    "uniform mat4 uMVPMatrix;\n" +
                    "uniform mat4 uTexMatrix;\n" +
                    "attribute vec4 aPosition;\n" +
                    "attribute vec4 aTextureCoord;\n" +
                    "const int GAUSSIAN_SAMPLES = 16;\n" +
                    "const vec2 singleStepOffset = vec2(1.0/256.0, 1.0/256.0);\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "varying vec2 blurCoordinates[GAUSSIAN_SAMPLES];\n" +

                    "void main()\n" +
                    "{\n" +
                    "    gl_Position = uMVPMatrix * aPosition;\n" +
                    "    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n" +
                    "blurCoordinates[0] = vTextureCoord.xy + singleStepOffset * vec2(0.0, -10.0);\n" +
                    "blurCoordinates[1] = vTextureCoord.xy + singleStepOffset * vec2(0.0, 10.0);\n" +
                    "blurCoordinates[2] = vTextureCoord.xy + singleStepOffset * vec2(-10.0, 0.0);\n" +
                    "blurCoordinates[3] = vTextureCoord.xy + singleStepOffset * vec2(10.0, 0.0);\n" +
                    "blurCoordinates[4] = vTextureCoord.xy + singleStepOffset * vec2(5.0, -8.0);\n" +
                    "blurCoordinates[5] = vTextureCoord.xy + singleStepOffset * vec2(5.0, 8.0);\n" +
                    "blurCoordinates[6] = vTextureCoord.xy + singleStepOffset * vec2(-5.0, 8.0);\n" +
                    "blurCoordinates[7] = vTextureCoord.xy + singleStepOffset * vec2(-5.0, -8.0);\n" +
                    "blurCoordinates[8] = vTextureCoord.xy + singleStepOffset * vec2(8.0, -5.0);\n" +
                    "blurCoordinates[9] = vTextureCoord.xy + singleStepOffset * vec2(8.0, 5.0);\n" +
                    "blurCoordinates[10] = vTextureCoord.xy + singleStepOffset * vec2(-8.0, 5.0);\n" +
                    "blurCoordinates[11] = vTextureCoord.xy + singleStepOffset * vec2(-8.0, -5.0);\n" +
                    "blurCoordinates[12] = vTextureCoord.xy + singleStepOffset * vec2(0.0, -6.0);\n" +
                    "blurCoordinates[13] = vTextureCoord.xy + singleStepOffset * vec2(0.0, 6.0);\n" +
                    "blurCoordinates[14] = vTextureCoord.xy + singleStepOffset * vec2(6.0, 0.0);\n" +
                    "blurCoordinates[15] = vTextureCoord.xy + singleStepOffset * vec2(-6.0, 0.0);\n" +
//                    "blurCoordinates[16] = vTextureCoord.xy + singleStepOffset * vec2(-4.0, -4.0);\n" +
//                    "blurCoordinates[17] = vTextureCoord.xy + singleStepOffset * vec2(-4.0, 4.0);\n" +
//                    "blurCoordinates[18] = vTextureCoord.xy + singleStepOffset * vec2(4.0, -4.0);\n" +
//                    "blurCoordinates[19] = vTextureCoord.xy + singleStepOffset * vec2(4.0, 4.0);\n" +
//                    "blurCoordinates[20] = vTextureCoord.xy + singleStepOffset * vec2(-2.0, -2.0);\n" +
//                    "blurCoordinates[21] = vTextureCoord.xy + singleStepOffset * vec2(-2.0, 2.0);\n" +
//                    "blurCoordinates[22] = vTextureCoord.xy + singleStepOffset * vec2(2.0, -2.0);\n" +
//                    "blurCoordinates[23] = vTextureCoord.xy + singleStepOffset * vec2(2.0, 2.0);\n" +
                    "}";
    private static final String FRAGMENT_SHADER_BEAUTY =
            "#extension GL_OES_EGL_image_external : require\n" +
                    "precision mediump float;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "uniform samplerExternalOES sTexture;\n" +
//                    "uniform highp vec2 singleStepOffset;\n" +
//                  "uniform highp vec4 params;\n" +
                    "const float beauty = 0.5;\n" +
                    "const float tone = 0.5;\n" +
                    "const vec4 params = vec4(1.0 - 0.6 * beauty, 1.0 - 0.3 * beauty, 0.1 + 0.3 * tone, 0.1 + 0.3 * tone);\n" +
                    "const int GAUSSIAN_SAMPLES = 16;\n" +
//                    "uniform highp float brightness;\n" +
                    "const float brightLevel = 0.5;\n" +
                    "const float brightness = 0.6 * (-0.5 + brightLevel);\n" +

                    "const vec3 W = vec3(0.299, 0.587, 0.114);\n" +
                    "const mat3 saturateMatrix = mat3(\n" +
                    "1.1102, -0.0598, -0.061,\n" +
                    "-0.0774, 1.0826, -0.1186,\n" +
                    "-0.0228, -0.0228, 1.1772);\n" +
                    "varying vec2 blurCoordinates[GAUSSIAN_SAMPLES];\n" +
//                    "vec2 blurCoordinates[GAUSSIAN_SAMPLES];\n" +

                    "float hardLight(float color) {\n" +
                    "if (color <= 0.5)\n" +
                    "   color = color * color * 2.0;\n" +
                    "else\n" +
                    "   color = 1.0 - ((1.0 - color)*(1.0 - color) * 2.0);\n" +
                    "return color;\n" +
                    "}\n" +

                    "void main(){\n" +
                    "vec3 centralColor = texture2D(sTexture, vTextureCoord).rgb;\n" +

                    "float sampleColor = centralColor.g * 22.0;\n" +
                    "sampleColor += texture2D(sTexture, blurCoordinates[0]).g;\n" +
                    "sampleColor += texture2D(sTexture, blurCoordinates[1]).g;\n" +
                    "sampleColor += texture2D(sTexture, blurCoordinates[2]).g;\n" +
                    "sampleColor += texture2D(sTexture, blurCoordinates[3]).g;\n" +
                    "sampleColor += texture2D(sTexture, blurCoordinates[4]).g;\n" +
                    "sampleColor += texture2D(sTexture, blurCoordinates[5]).g;\n" +
                    "sampleColor += texture2D(sTexture, blurCoordinates[6]).g;\n" +
                    "sampleColor += texture2D(sTexture, blurCoordinates[7]).g;\n" +
                    "sampleColor += texture2D(sTexture, blurCoordinates[8]).g;\n" +
                    "sampleColor += texture2D(sTexture, blurCoordinates[9]).g;\n" +
                    "sampleColor += texture2D(sTexture, blurCoordinates[10]).g;\n" +
                    "sampleColor += texture2D(sTexture, blurCoordinates[11]).g;\n" +
                    "sampleColor += texture2D(sTexture, blurCoordinates[12]).g * 2.0;\n" +
                    "sampleColor += texture2D(sTexture, blurCoordinates[13]).g * 2.0;\n" +
                    "sampleColor += texture2D(sTexture, blurCoordinates[14]).g * 2.0;\n" +
                    "sampleColor += texture2D(sTexture, blurCoordinates[15]).g * 2.0;\n" +
//                    "sampleColor += texture2D(sTexture, blurCoordinates[16]).g * 2.0;\n" +
//                    "sampleColor += texture2D(sTexture, blurCoordinates[17]).g * 2.0;\n" +
//                    "sampleColor += texture2D(sTexture, blurCoordinates[18]).g * 2.0;\n" +
//                    "sampleColor += texture2D(sTexture, blurCoordinates[19]).g * 2.0;\n" +
//                    "sampleColor += texture2D(sTexture, blurCoordinates[20]).g * 3.0;\n" +
//                    "sampleColor += texture2D(sTexture, blurCoordinates[21]).g * 3.0;\n" +
//                    "sampleColor += texture2D(sTexture, blurCoordinates[22]).g * 3.0;\n" +
//                    "sampleColor += texture2D(sTexture, blurCoordinates[23]).g * 3.0;\n" +

                    "sampleColor = sampleColor / 62.0;\n" +

                    "float highPass = centralColor.g - sampleColor + 0.5;\n" +

                    "for (int i = 0; i < 5; i++) {\n" +
                    "   highPass = hardLight(highPass);\n" +
                    "}\n" +
                    "float lumance = dot(centralColor, W);\n" +

                    "float alpha = pow(lumance, params.r);\n" +

                    "vec3 smoothColor = centralColor + (centralColor-vec3(highPass))*alpha*0.1;\n" +

                    "smoothColor.r = clamp(pow(smoothColor.r, params.g), 0.0, 1.0);\n" +
                    "smoothColor.g = clamp(pow(smoothColor.g, params.g), 0.0, 1.0);\n" +
                    "smoothColor.b = clamp(pow(smoothColor.b, params.g), 0.0, 1.0);\n" +

                    "vec3 lvse = vec3(1.0)-(vec3(1.0)-smoothColor)*(vec3(1.0)-centralColor);\n" +
                    "vec3 bianliang = max(smoothColor, centralColor);\n" +
                    "vec3 rouguang = 2.0*centralColor*smoothColor + centralColor*centralColor - 2.0*centralColor*centralColor*smoothColor;\n" +

                    "gl_FragColor = vec4(mix(centralColor, lvse, alpha), 1.0);\n" +
                    "gl_FragColor.rgb = mix(gl_FragColor.rgb, bianliang, alpha);\n" +
                    "gl_FragColor.rgb = mix(gl_FragColor.rgb, rouguang, params.b);\n" +

                    "vec3 satcolor = gl_FragColor.rgb * saturateMatrix;\n" +
                    "gl_FragColor.rgb = mix(gl_FragColor.rgb, satcolor, params.a);\n" +
                    "gl_FragColor.rgb = vec3(gl_FragColor.rgb + vec3(brightness));\n" +
                    "}\n";


    // Simple vertex shader, used for all programs.
    private static final String VERTEX_SHADER =
            "uniform mat4 uMVPMatrix;\n" +
                    "uniform mat4 uTexMatrix;\n" +
                    "attribute vec4 aPosition;\n" +
                    "attribute vec4 aTextureCoord;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "void main() {\n" +
                    "    gl_Position = uMVPMatrix * aPosition;\n" +
                    "    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n" +
                    "}\n";

    public static final String VERTEX_SHADER_BILATERAL = "" +
            "precision mediump float;\n" +
            "uniform mat4 uMVPMatrix;\n" +
            "uniform mat4 uTexMatrix;\n" +
            "attribute vec4 aPosition;\n" +
            "attribute vec4 aTextureCoord;\n" +
            "const int GAUSSIAN_SAMPLES = 9;\n" +
            "const vec2 singleStepOffset = vec2(1.0/256.0, 1.0/256.0);\n" +
            "varying vec2 vTextureCoord;\n" +
            "varying vec2 blurCoordinates[GAUSSIAN_SAMPLES];\n" +

            "void main()\n" +
            "{\n" +
            "    gl_Position = uMVPMatrix * aPosition;\n" +
            "    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n" +
            "	int multiplier = 0;\n" +
            "	vec2 blurStep;\n" +
            "	for (int i = 0; i < GAUSSIAN_SAMPLES; i++)\n" +
            "	{\n" +
            "		multiplier = (i - ((GAUSSIAN_SAMPLES - 1) / 2));\n" +
            "		blurStep = float(multiplier) * singleStepOffset;\n" +
            "		blurCoordinates[i] = vTextureCoord + blurStep;\n" +
            "	}\n" +
            "}";

    public static final String FRAGMENT_SHADER_BILATERAL = "" +
            "#extension GL_OES_EGL_image_external : require\n" +
            "precision mediump float;\n" +
            "const int GAUSSIAN_SAMPLES = 9;\n" +
            "uniform float distanceNormalizationFactor;\n" +
            "uniform samplerExternalOES sTexture;\n" +
            "varying vec2 vTextureCoord;\n" +
            "varying vec2 blurCoordinates[GAUSSIAN_SAMPLES];\n" +

            " void main()\n" +
            " {\n" +
            "     vec4 centralColor;\n" +
            "     float gaussianWeightTotal;\n" +
            "     vec4 sum;\n" +
            "     vec4 sampleColor;\n" +
            "     float distanceFromCentralColor;\n" +
            "     float gaussianWeight;\n" +
            "     \n" +
            "     centralColor = texture2D(sTexture, blurCoordinates[4]);\n" +
            "     gaussianWeightTotal = 0.18;\n" +
            "     sum = centralColor * 0.18;\n" +
            "     \n" +
            "     sampleColor = texture2D(sTexture, blurCoordinates[0]);\n" +
            "     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);\n" +
            "     gaussianWeight = 0.05 * (1.0 - distanceFromCentralColor);\n" +
            "     gaussianWeightTotal += gaussianWeight;\n" +
            "     sum += sampleColor * gaussianWeight;\n" +

            "     sampleColor = texture2D(sTexture, blurCoordinates[1]);\n" +
            "     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);\n" +
            "     gaussianWeight = 0.09 * (1.0 - distanceFromCentralColor);\n" +
            "     gaussianWeightTotal += gaussianWeight;\n" +
            "     sum += sampleColor * gaussianWeight;\n" +

            "     sampleColor = texture2D(sTexture, blurCoordinates[2]);\n" +
            "     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);\n" +
            "     gaussianWeight = 0.12 * (1.0 - distanceFromCentralColor);\n" +
            "     gaussianWeightTotal += gaussianWeight;\n" +
            "     sum += sampleColor * gaussianWeight;\n" +

            "     sampleColor = texture2D(sTexture, blurCoordinates[3]);\n" +
            "     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);\n" +
            "     gaussianWeight = 0.15 * (1.0 - distanceFromCentralColor);\n" +
            "     gaussianWeightTotal += gaussianWeight;\n" +
            "     sum += sampleColor * gaussianWeight;\n" +

            "     sampleColor = texture2D(sTexture, blurCoordinates[5]);\n" +
            "     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);\n" +
            "     gaussianWeight = 0.15 * (1.0 - distanceFromCentralColor);\n" +
            "     gaussianWeightTotal += gaussianWeight;\n" +
            "     sum += sampleColor * gaussianWeight;\n" +

            "     sampleColor = texture2D(sTexture, blurCoordinates[6]);\n" +
            "     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);\n" +
            "     gaussianWeight = 0.12 * (1.0 - distanceFromCentralColor);\n" +
            "     gaussianWeightTotal += gaussianWeight;\n" +
            "     sum += sampleColor * gaussianWeight;\n" +

            "     sampleColor = texture2D(sTexture, blurCoordinates[7]);\n" +
            "     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);\n" +
            "     gaussianWeight = 0.09 * (1.0 - distanceFromCentralColor);\n" +
            "     gaussianWeightTotal += gaussianWeight;\n" +
            "     sum += sampleColor * gaussianWeight;\n" +

            "     sampleColor = texture2D(sTexture, blurCoordinates[8]);\n" +
            "     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);\n" +
            "     gaussianWeight = 0.05 * (1.0 - distanceFromCentralColor);\n" +
            "     gaussianWeightTotal += gaussianWeight;\n" +
            "     sum += sampleColor * gaussianWeight;\n" +
            "     gl_FragColor = sum / gaussianWeightTotal;\n" +
//			" gl_FragColor.r = distanceNormalizationFactor / 20.0;" +
            " }";

    // Fragment shader with a convolution filter.  The upper-left half will be drawn normally,
    // the lower-right half will have the filter applied, and a thin red line will be drawn
    // at the border.
    //
    // This is not optimized for performance.  Some things that might make this faster:
    // - Remove the conditionals.  They're used to present a half & half view with a red
    //   stripe across the middle, but that's only useful for a demo.
    // - Unroll the loop.  Ideally the compiler does this for you when it's beneficial.
    // - Bake the filter kernel into the shader, instead of passing it through a uniform
    //   array.  That, combined with loop unrolling, should reduce memory accesses.
    public static final int KERNEL_SIZE = 9;
    private static final String FRAGMENT_SHADER_EXT_FILT =
            "#extension GL_OES_EGL_image_external : require\n" +
                    "#define KERNEL_SIZE " + KERNEL_SIZE + "\n" +
                    "precision highp float;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "uniform samplerExternalOES sTexture;\n" +
                    "uniform float uKernel[KERNEL_SIZE];\n" +
                    "uniform vec2 uTexOffset[KERNEL_SIZE];\n" +
                    "uniform float uColorAdjust;\n" +
                    "void main() {\n" +
                    "    int i = 0;\n" +
                    "    vec4 sum = vec4(0.0);\n" +
                    "    for (i = 0; i < KERNEL_SIZE; i++) {\n" +
                    "            vec4 texc = texture2D(sTexture, vTextureCoord + uTexOffset[i]);\n" +
                    "            sum += texc * uKernel[i];\n" +
                    "    }\n" +
                    "    sum += uColorAdjust;\n" +
                    "    gl_FragColor = sum;\n" +
                    "}\n";

    private ProgramType mProgramType;

    private float mTexWidth;
    private float mTexHeight;

    // Handles to the GL program and various components of it.
    private int mProgramHandle;
    private int muMVPMatrixLoc;
    private int muTexMatrixLoc;
    private int muKernelLoc;
    private int muTexOffsetLoc;
    private int muColorAdjustLoc;
    private int maPositionLoc;
    private int maTextureCoordLoc;
    private int muTouchPositionLoc;

    private int mTextureTarget;

    //Bilateral blur
    private int mDisFactorLocation;
    //private int mSingleStepOffsetLocation;
    //texelSpacingMultiplier: A multiplier for the spacing between texel reads, ranging from 0.0 on up, with a default
    //of 4.0
    //private float mSingleStepOffset = 4.0f;
    //distanceNormalizationFactor: A normalization factor for the distance between central color and sample color,
    //with a default of 8.0.
    public static float mDistance = 20.0f;

    private float[] mKernel = new float[KERNEL_SIZE];       // Inputs for convolution filter based shaders
    private float[] mSummedTouchPosition = new float[2];    // Summed touch event delta
    private float[] mLastTouchPosition = new float[2];      // Raw location of last touch event
    private float[] mTexOffset;
    private float mColorAdjust;


    /**
     * Prepares the program in the current EGL context.
     */
    public Texture2dProgram(ProgramType programType) {
        mProgramType = programType;

        switch (programType) {
            case TEXTURE_2D:
                mTextureTarget = GLES20.GL_TEXTURE_2D;
                mProgramHandle = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER_2D);
                break;
            case TEXTURE_EXT:
                mTextureTarget = GLES11Ext.GL_TEXTURE_EXTERNAL_OES;
                mProgramHandle = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER_EXT);
                break;
            case TEXTURE_EXT_BW:
                mTextureTarget = GLES11Ext.GL_TEXTURE_EXTERNAL_OES;
                mProgramHandle = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER_EXT_BW);
                break;
            case TEXTURE_EXT_NIGHT:
                mTextureTarget = GLES11Ext.GL_TEXTURE_EXTERNAL_OES;
                mProgramHandle = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER_EXT_NIGHT);
                break;
            case TEXTURE_EXT_CHROMA_KEY:
                mTextureTarget = GLES11Ext.GL_TEXTURE_EXTERNAL_OES;
                mProgramHandle = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER_EXT_CHROMA_KEY);
                break;
            case TEXTURE_EXT_SQUEEZE:
                mTextureTarget = GLES11Ext.GL_TEXTURE_EXTERNAL_OES;
                mProgramHandle = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER_SQUEEZE);
                break;
            case TEXTURE_EXT_TWIRL:
                mTextureTarget = GLES11Ext.GL_TEXTURE_EXTERNAL_OES;
                mProgramHandle = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER_TWIRL);
                break;
            case TEXTURE_EXT_TUNNEL:
                mTextureTarget = GLES11Ext.GL_TEXTURE_EXTERNAL_OES;
                mProgramHandle = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER_TUNNEL);
                break;
            case TEXTURE_EXT_BULGE:
                mTextureTarget = GLES11Ext.GL_TEXTURE_EXTERNAL_OES;
                mProgramHandle = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER_BULGE);
                break;
            case TEXTURE_EXT_FISHEYE:
                mTextureTarget = GLES11Ext.GL_TEXTURE_EXTERNAL_OES;
                mProgramHandle = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER_FISHEYE);
                break;
            case TEXTURE_EXT_DENT:
                mTextureTarget = GLES11Ext.GL_TEXTURE_EXTERNAL_OES;
                mProgramHandle = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER_DENT);
                break;
            case TEXTURE_EXT_MIRROR:
                mTextureTarget = GLES11Ext.GL_TEXTURE_EXTERNAL_OES;
                mProgramHandle = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER_MIRROR);
                break;
            case TEXTURE_EXT_STRETCH:
                mTextureTarget = GLES11Ext.GL_TEXTURE_EXTERNAL_OES;
                mProgramHandle = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER_STRETCH);
                break;
            case TEXTURE_EXT_FILT:
                mTextureTarget = GLES11Ext.GL_TEXTURE_EXTERNAL_OES;
                mProgramHandle = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER_EXT_FILT);
                break;
            case TEXTURE_EXT_BEAUTY:
                mTextureTarget = GLES11Ext.GL_TEXTURE_EXTERNAL_OES;
                mProgramHandle = GlUtil.createProgram(VERTEX_SHADER_BEAUTY, FRAGMENT_SHADER_BEAUTY);
                break;
            case TEXTURE_EXT_BILATERAL:
                mTextureTarget = GLES11Ext.GL_TEXTURE_EXTERNAL_OES;
                mProgramHandle = GlUtil.createProgram(VERTEX_SHADER_BILATERAL, FRAGMENT_SHADER_BILATERAL);
                break;
            default:
                throw new RuntimeException("Unhandled type " + programType);
        }
        if (mProgramHandle == 0) {
            throw new RuntimeException("Unable to create program");
        }
        Log.d(TAG, "Created program " + mProgramHandle + " (" + programType + ")");

        // get locations of attributes and uniforms

        maPositionLoc = GLES20.glGetAttribLocation(mProgramHandle, "aPosition");
        GlUtil.checkLocation(maPositionLoc, "aPosition");
        maTextureCoordLoc = GLES20.glGetAttribLocation(mProgramHandle, "aTextureCoord");
        GlUtil.checkLocation(maTextureCoordLoc, "aTextureCoord");
        muMVPMatrixLoc = GLES20.glGetUniformLocation(mProgramHandle, "uMVPMatrix");
        GlUtil.checkLocation(muMVPMatrixLoc, "uMVPMatrix");
        muTexMatrixLoc = GLES20.glGetUniformLocation(mProgramHandle, "uTexMatrix");
        GlUtil.checkLocation(muTexMatrixLoc, "uTexMatrix");
        muKernelLoc = GLES20.glGetUniformLocation(mProgramHandle, "uKernel");
        if (muKernelLoc < 0) {
            // no kernel in this one
            muKernelLoc = -1;
            muTexOffsetLoc = -1;
            muColorAdjustLoc = -1;
        } else {
            // has kernel, must also have tex offset and color adj
            muTexOffsetLoc = GLES20.glGetUniformLocation(mProgramHandle, "uTexOffset");
            GlUtil.checkLocation(muTexOffsetLoc, "uTexOffset");
            muColorAdjustLoc = GLES20.glGetUniformLocation(mProgramHandle, "uColorAdjust");
            GlUtil.checkLocation(muColorAdjustLoc, "uColorAdjust");

            // initialize default values
            setKernel(new float[]{0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f}, 0f);
            setTexSize(256, 256);
        }

        muTouchPositionLoc = GLES20.glGetUniformLocation(mProgramHandle, "uPosition");
        if (muTouchPositionLoc < 0) {
            // Shader doesn't use position
            muTouchPositionLoc = -1;
        } else {
            // initialize default values
            //handleTouchEvent(new float[]{0f, 0f});
        }

        //Bilateral blur variables
        mDisFactorLocation = GLES20.glGetUniformLocation(mProgramHandle, "distanceNormalizationFactor");
        //mSingleStepOffsetLocation = GLES20.glGetUniformLocation(mProgramHandle, "singleStepOffset");
    }

    /**
     * Releases the program.
     */
    public void release() {
        Log.d(TAG, "deleting program " + mProgramHandle);
        GLES20.glDeleteProgram(mProgramHandle);
        mProgramHandle = -1;
    }

    /**
     * Returns the program type.
     */
    public ProgramType getProgramType() {
        return mProgramType;
    }

    /**
     * Creates a texture object suitable for use with this program.
     * <p/>
     * On exit, the texture will be bound.
     */
    public int createTextureObject() {
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        GlUtil.checkGlError("glGenTextures");

        int texId = textures[0];
        GLES20.glBindTexture(mTextureTarget, texId);
        GlUtil.checkGlError("glBindTexture " + texId);

        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_S,
                GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_T,
                GLES20.GL_CLAMP_TO_EDGE);
        GlUtil.checkGlError("glTexParameter");

        return texId;
    }

    /**
     * Configures the effect offset
     * <p/>
     * This only has an effect for programs that
     * use positional effects like SQUEEZE and MIRROR
     */
    public void handleTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            // A finger is dragging about
            if (mTexHeight != 0 && mTexWidth != 0) {
                mSummedTouchPosition[0] += (2 * (ev.getX() - mLastTouchPosition[0])) / mTexWidth;
                mSummedTouchPosition[1] += (2 * (ev.getY() - mLastTouchPosition[1])) / -mTexHeight;
                mLastTouchPosition[0] = ev.getX();
                mLastTouchPosition[1] = ev.getY();
            }
        } else if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // The primary finger has landed
            mLastTouchPosition[0] = ev.getX();
            mLastTouchPosition[1] = ev.getY();
        }
    }

    /**
     * Configures the convolution filter values.
     * This only has an effect for programs that use the
     * FRAGMENT_SHADER_EXT_FILT Fragment shader.
     *
     * @param values Normalized filter values; must be KERNEL_SIZE elements.
     */
    public void setKernel(float[] values, float colorAdj) {
        if (values.length != KERNEL_SIZE) {
            throw new IllegalArgumentException("Kernel size is " + values.length +
                    " vs. " + KERNEL_SIZE);
        }
        System.arraycopy(values, 0, mKernel, 0, KERNEL_SIZE);
        mColorAdjust = colorAdj;
        //Log.d(TAG, "filt kernel: " + Arrays.toString(mKernel) + ", adj=" + colorAdj);
    }

    /**
     * Sets the size of the texture.  This is used to find adjacent texels when filtering.
     */
    public void setTexSize(int width, int height) {
        mTexHeight = height;
        mTexWidth = width;
        float rw = 1.0f / width;
        float rh = 1.0f / height;

        // Don't need to create a new array here, but it's syntactically convenient.
        mTexOffset = new float[]{
                -rw, -rh, 0f, -rh, rw, -rh,
                -rw, 0f, 0f, 0f, rw, 0f,
                -rw, rh, 0f, rh, rw, rh
        };
        //Log.d(TAG, "filt size: " + width + "x" + height + ": " + Arrays.toString(mTexOffset));
    }

    /**
     * Issues the draw call.  Does the full setup on every call.
     *
     * @param mvpMatrix       The 4x4 projection matrix.
     * @param vertexBuffer    Buffer with vertex position data.
     * @param firstVertex     Index of first vertex to use in vertexBuffer.
     * @param vertexCount     Number of vertices in vertexBuffer.
     * @param coordsPerVertex The number of coordinates per vertex (e.g. x,y is 2).
     * @param vertexStride    Width, in bytes, of the position data for each vertex (often
     *                        vertexCount * sizeof(float)).
     * @param texMatrix       A 4x4 transformation matrix for texture coords.
     * @param texBuffer       Buffer with vertex texture data.
     * @param texStride       Width, in bytes, of the texture data for each vertex.
     */
    public void draw(float[] mvpMatrix, FloatBuffer vertexBuffer, int firstVertex,
                     int vertexCount, int coordsPerVertex, int vertexStride,
                     float[] texMatrix, FloatBuffer texBuffer, int textureId, int texStride) {
        GlUtil.checkGlError("draw start");

        // Select the program.
        GLES20.glUseProgram(mProgramHandle);
        GlUtil.checkGlError("glUseProgram");

        // Set the texture.
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(mTextureTarget, textureId);

        // Copy the model / view / projection matrix over.
        GLES20.glUniformMatrix4fv(muMVPMatrixLoc, 1, false, mvpMatrix, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");

        // Copy the texture transformation matrix over.
        GLES20.glUniformMatrix4fv(muTexMatrixLoc, 1, false, texMatrix, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");

        // Enable the "aPosition" vertex attribute.
        GLES20.glEnableVertexAttribArray(maPositionLoc);
        GlUtil.checkGlError("glEnableVertexAttribArray");

        // Connect vertexBuffer to "aPosition".
        GLES20.glVertexAttribPointer(maPositionLoc, coordsPerVertex,
                GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
        GlUtil.checkGlError("glVertexAttribPointer");

        // Enable the "aTextureCoord" vertex attribute.
        GLES20.glEnableVertexAttribArray(maTextureCoordLoc);
        GlUtil.checkGlError("glEnableVertexAttribArray");

        // Connect texBuffer to "aTextureCoord".
        GLES20.glVertexAttribPointer(maTextureCoordLoc, 2,
                GLES20.GL_FLOAT, false, texStride, texBuffer);
        GlUtil.checkGlError("glVertexAttribPointer");

        // Populate the convolution kernel, if present.
        if (muKernelLoc >= 0) {
            GLES20.glUniform1fv(muKernelLoc, KERNEL_SIZE, mKernel, 0);
            GLES20.glUniform2fv(muTexOffsetLoc, KERNEL_SIZE, mTexOffset, 0);
            GLES20.glUniform1f(muColorAdjustLoc, mColorAdjust);
        }


        //Bilateral filter params
        GLES20.glUniform1f(mDisFactorLocation, mDistance);
        //GLES20.glUniform2fv(mSingleStepOffsetLocation, 1, FloatBuffer.wrap(new float[]{1.0f/mTexWidth,
        //        1.0f/mTexHeight}));

        // Populate touch position data, if present
        if (muTouchPositionLoc >= 0) {
            GLES20.glUniform2fv(muTouchPositionLoc, 1, mSummedTouchPosition, 0);
        }

        // Draw the rect.
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, firstVertex, vertexCount);
        GlUtil.checkGlError("glDrawArrays");

        // Done -- disable vertex array, texture, and program.
        GLES20.glDisableVertexAttribArray(maPositionLoc);
        GLES20.glDisableVertexAttribArray(maTextureCoordLoc);
        GLES20.glBindTexture(mTextureTarget, 0);
        GLES20.glUseProgram(0);
    }
}