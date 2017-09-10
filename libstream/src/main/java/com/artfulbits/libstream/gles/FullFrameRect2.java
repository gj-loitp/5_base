package com.artfulbits.libstream.gles;

import android.opengl.Matrix;

public class FullFrameRect2 extends FullFrameRect {

   public FullFrameRect2(Texture2dProgram program) {
      super(program);
   }

   public void drawFrameMirrorY(int textureId, float[] texMatrix, int rotate, float scale) {
      float[] matrix = new float[16];
      Matrix.setIdentityM(matrix, 0);
      if(rotate != 0) {
         Matrix.rotateM(matrix, 0, (float)rotate, 0.0F, 0.0F, 1.0F);
      }

      if(rotate == 90 || rotate == 270) {
         scale = -scale;
      }

      if(scale != 1.0F) {
         Matrix.scaleM(matrix, 0, 1.0F, scale, 1.0F);
      }

      if(rotate == 0 || rotate == 180) {
         Matrix.scaleM(matrix, 0, -1.0F, 1.0F, 1.0F);
      }

      this.mProgram.draw(matrix, this.mRectDrawable.getVertexArray(), 0, this.mRectDrawable.getVertexCount(), this.mRectDrawable.getCoordsPerVertex(), this.mRectDrawable.getVertexStride(), texMatrix, this.mRectDrawable.getTexCoordArray(), textureId, this.mRectDrawable.getTexCoordStride());
   }

   public void drawFrameY(int textureId, float[] texMatrix, int rotate, float scale) {
      float[] matrix = new float[16];
      Matrix.setIdentityM(matrix, 0);
      if(rotate != 0) {
         Matrix.rotateM(matrix, 0, (float)rotate, 0.0F, 0.0F, 1.0F);
      }

      if(scale != 1.0F) {
         Matrix.scaleM(matrix, 0, 1.0F, scale, 1.0F);
      }

      this.mProgram.draw(matrix, this.mRectDrawable.getVertexArray(), 0, this.mRectDrawable.getVertexCount(), this.mRectDrawable.getCoordsPerVertex(), this.mRectDrawable.getVertexStride(), texMatrix, this.mRectDrawable.getTexCoordArray(), textureId, this.mRectDrawable.getTexCoordStride());
   }

   public void drawFrameX(int textureId, float[] texMatrix, int rotate, float scale) {
      float[] m = new float[16];
      Matrix.setIdentityM(m, 0);
      if(rotate != 0) {
         Matrix.rotateM(m, 0, (float)rotate, 0.0F, 0.0F, 1.0F);
      }

      if(scale != 1.0F) {
         Matrix.scaleM(m, 0, scale, 1.0F, 1.0F);
      }

      this.mProgram.draw(m, this.mRectDrawable.getVertexArray(), 0, this.mRectDrawable.getVertexCount(), this.mRectDrawable.getCoordsPerVertex(), this.mRectDrawable.getVertexStride(), texMatrix, this.mRectDrawable.getTexCoordArray(), textureId, this.mRectDrawable.getTexCoordStride());
   }

   public void drawFrameMirrorX(int textureId, float[] texMatrix, int rotate, float scale) {
      float[] m = new float[16];
      Matrix.setIdentityM(m, 0);
      if(rotate != 0) {
         Matrix.rotateM(m, 0, (float)rotate, 0.0F, 0.0F, 1.0F);
      }

      if(rotate == 0 || rotate == 180) {
         scale = -scale;
      }

      if(scale != 1.0F) {
         Matrix.scaleM(m, 0, scale, 1.0F, 1.0F);
      }

      if(rotate == 90 || rotate == 270) {
         Matrix.scaleM(m, 0, 1.0F, -1.0F, 1.0F);
      }

      this.mProgram.draw(m, this.mRectDrawable.getVertexArray(), 0, this.mRectDrawable.getVertexCount(), this.mRectDrawable.getCoordsPerVertex(), this.mRectDrawable.getVertexStride(), texMatrix, this.mRectDrawable.getTexCoordArray(), textureId, this.mRectDrawable.getTexCoordStride());
   }
}
