package com.artfulbits.libstream.gles;

import java.nio.FloatBuffer;

public class Drawable2d {

   private static final int SIZEOF_FLOAT = 4;
   private static final float[] TRIANGLE_COORDS = new float[]{0.0F, 0.57735026F, -0.5F, -0.28867513F, 0.5F, -0.28867513F};
   private static final float[] TRIANGLE_TEX_COORDS = new float[]{0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F};
   private static final FloatBuffer TRIANGLE_BUF = GlUtil.createFloatBuffer(TRIANGLE_COORDS);
   private static final FloatBuffer TRIANGLE_TEX_BUF = GlUtil.createFloatBuffer(TRIANGLE_TEX_COORDS);
   private static final float[] RECTANGLE_COORDS = new float[]{-0.5F, -0.5F, 0.5F, -0.5F, -0.5F, 0.5F, 0.5F, 0.5F};
   private static final float[] RECTANGLE_TEX_COORDS = new float[]{0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F};
   private static final FloatBuffer RECTANGLE_BUF = GlUtil.createFloatBuffer(RECTANGLE_COORDS);
   private static final FloatBuffer RECTANGLE_TEX_BUF = GlUtil.createFloatBuffer(RECTANGLE_TEX_COORDS);
   private static final float[] FULL_RECTANGLE_COORDS = new float[]{-1.0F, -1.0F, 1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F};
   private static final float[] FULL_RECTANGLE_TEX_COORDS = new float[]{0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F};
   private static final FloatBuffer FULL_RECTANGLE_BUF = GlUtil.createFloatBuffer(FULL_RECTANGLE_COORDS);
   private static final FloatBuffer FULL_RECTANGLE_TEX_BUF = GlUtil.createFloatBuffer(FULL_RECTANGLE_TEX_COORDS);
   private FloatBuffer mVertexArray;
   private FloatBuffer mTexCoordArray;
   private int mVertexCount;
   private int mCoordsPerVertex;
   private int mVertexStride;
   private int mTexCoordStride;
   private Drawable2d.Prefab mPrefab;


   public Drawable2d(Drawable2d.Prefab shape) {
      switch(Drawable2d.NamelessClass1245084039.$SwitchMap$com$wmspanel$libstream$gles$Drawable2d$Prefab[shape.ordinal()]) {
      case 1:
         this.mVertexArray = TRIANGLE_BUF;
         this.mTexCoordArray = TRIANGLE_TEX_BUF;
         this.mCoordsPerVertex = 2;
         this.mVertexStride = this.mCoordsPerVertex * 4;
         this.mVertexCount = TRIANGLE_COORDS.length / this.mCoordsPerVertex;
         break;
      case 2:
         this.mVertexArray = RECTANGLE_BUF;
         this.mTexCoordArray = RECTANGLE_TEX_BUF;
         this.mCoordsPerVertex = 2;
         this.mVertexStride = this.mCoordsPerVertex * 4;
         this.mVertexCount = RECTANGLE_COORDS.length / this.mCoordsPerVertex;
         break;
      case 3:
         this.mVertexArray = FULL_RECTANGLE_BUF;
         this.mTexCoordArray = FULL_RECTANGLE_TEX_BUF;
         this.mCoordsPerVertex = 2;
         this.mVertexStride = this.mCoordsPerVertex * 4;
         this.mVertexCount = FULL_RECTANGLE_COORDS.length / this.mCoordsPerVertex;
         break;
      default:
         throw new RuntimeException("Unknown shape " + shape);
      }

      this.mTexCoordStride = 8;
      this.mPrefab = shape;
   }

   public FloatBuffer getVertexArray() {
      return this.mVertexArray;
   }

   public FloatBuffer getTexCoordArray() {
      return this.mTexCoordArray;
   }

   public int getVertexCount() {
      return this.mVertexCount;
   }

   public int getVertexStride() {
      return this.mVertexStride;
   }

   public int getTexCoordStride() {
      return this.mTexCoordStride;
   }

   public int getCoordsPerVertex() {
      return this.mCoordsPerVertex;
   }

   public String toString() {
      return this.mPrefab != null?"[Drawable2d: " + this.mPrefab + "]":"[Drawable2d: ...]";
   }


   // $FF: synthetic class
   static class NamelessClass1245084039 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$wmspanel$libstream$gles$Drawable2d$Prefab = new int[Drawable2d.Prefab.values().length];


      static {
         try {
            $SwitchMap$com$wmspanel$libstream$gles$Drawable2d$Prefab[Drawable2d.Prefab.TRIANGLE.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$gles$Drawable2d$Prefab[Drawable2d.Prefab.RECTANGLE.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$gles$Drawable2d$Prefab[Drawable2d.Prefab.FULL_RECTANGLE.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
            ;
         }

      }
   }

   public static enum Prefab {

      TRIANGLE("TRIANGLE", 0),
      RECTANGLE("RECTANGLE", 1),
      FULL_RECTANGLE("FULL_RECTANGLE", 2);
      // $FF: synthetic field
      private static final Drawable2d.Prefab[] $VALUES = new Drawable2d.Prefab[]{TRIANGLE, RECTANGLE, FULL_RECTANGLE};


      private Prefab(String var1, int var2) {}

   }
}
