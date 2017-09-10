package com.artfulbits.libstream;

class YV12toYUV420Planar implements ColorConverter {

   public byte[] convert(byte[] input, int width, int height) {
      byte[] output = new byte[input.length];
      int frameSize = width * height;
      int qFrameSize = frameSize / 4;
      System.arraycopy(input, 0, output, 0, frameSize);
      System.arraycopy(input, frameSize, output, frameSize + qFrameSize, qFrameSize);
      System.arraycopy(input, frameSize + qFrameSize, output, frameSize, qFrameSize);
      return output;
   }
}
