package com.artfulbits.libstream;

class NV21toYUV420Planar implements ColorConverter {

   public byte[] convert(byte[] input, int width, int height) {
      byte[] output = new byte[input.length];
      int frameSize = width * height;
      int qFrameSize = frameSize / 4;
      System.arraycopy(input, 0, output, 0, frameSize);

      for(int i = 0; i < qFrameSize; ++i) {
         output[frameSize + i] = input[frameSize + i * 2];
         output[frameSize + qFrameSize + i] = input[frameSize + 1 + i * 2];
      }

      return output;
   }
}
