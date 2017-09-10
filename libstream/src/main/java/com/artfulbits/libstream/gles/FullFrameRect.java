package com.artfulbits.libstream.gles;

public class FullFrameRect {

    protected final Drawable2d mRectDrawable;
    protected Texture2dProgram mProgram;
    private static final float[] FULL_RECTANGLE_TEX_COORDS = new float[]{0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F};


    public FullFrameRect(Texture2dProgram program) {
        this.mRectDrawable = new Drawable2d(Drawable2d.Prefab.FULL_RECTANGLE);
        this.mProgram = program;
    }

    public void release(boolean doEglCleanup) {
        if (this.mProgram != null) {
            if (doEglCleanup) {
                this.mProgram.release();
            }

            this.mProgram = null;
        }

    }

    public Texture2dProgram getProgram() {
        return this.mProgram;
    }

    public void changeProgram(Texture2dProgram program) {
        this.mProgram.release();
        this.mProgram = program;
    }

    public int createTextureObject() {
        return this.mProgram.createTextureObject();
    }

    public void drawFrame(int textureId, float[] texMatrix) {
        this.mProgram.draw(GlUtil.IDENTITY_MATRIX, this.mRectDrawable.getVertexArray(), 0, this.mRectDrawable.getVertexCount(), this.mRectDrawable.getCoordsPerVertex(), this.mRectDrawable.getVertexStride(), texMatrix, this.mRectDrawable.getTexCoordArray(), textureId, this.mRectDrawable.getTexCoordStride());
    }

}
