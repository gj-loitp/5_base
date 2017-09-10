package com.artfulbits.libstream;

class NV21toYUV420 implements ColorConverter {

   public byte[] convert(byte[] input, int width, int height) {
      byte[] output = new byte[input.length];
      int frameSize = width * height;
      System.arraycopy(input, 0, output, 0, frameSize);

      for(int i = 0; i < width * height / 2; i += 2) {
         output[frameSize + i] = input[frameSize + i + 1];
         output[frameSize + i + 1] = input[frameSize + i];
      }

      return output;
   }
}
