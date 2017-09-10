package com.artfulbits.libstream;

class YV12toYUV420 implements ColorConverter {

   public byte[] convert(byte[] input, int width, int height) {
      byte[] output = new byte[input.length];
      int frameSize = width * height;
      int qFrameSize = frameSize / 4;
      System.arraycopy(input, 0, output, 0, frameSize);

      for(int i = 0; i < qFrameSize; ++i) {
         output[frameSize + 2 * i] = input[frameSize + qFrameSize + i];
         output[frameSize + 2 * i + 1] = input[frameSize + i];
      }

      return output;
   }
}
