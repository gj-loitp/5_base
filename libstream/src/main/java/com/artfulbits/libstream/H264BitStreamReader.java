package com.artfulbits.libstream;


public class H264BitStreamReader {

   byte[] buffer_;
   int buffer_size_;
   int buffer_byte_offset_ = 0;
   int buffer_bit_offset_ = 0;
   static final int[] BIT_MASKS = new int[]{255, 127, 63, 31, 15, 7, 3, 1};
   static final int[] REVERTED_BIT_MASKS = new int[]{0, 128, 192, 224, 240, 248, 252, 254, 255};


   H264BitStreamReader(byte[] buffer, int buffer_size) {
      this.buffer_ = buffer;
      this.buffer_size_ = buffer_size;
   }

   void inc_buffer_offset(int read_requested) {
      this.buffer_byte_offset_ += read_requested / 8;
      this.buffer_bit_offset_ += read_requested % 8;
      this.buffer_byte_offset_ += this.buffer_bit_offset_ / 8;
      this.buffer_bit_offset_ %= 8;
   }

   boolean validate_buffer(int read_requested) {
      if(read_requested <= 0) {
         return false;
      } else {
         int buffer_byte_offset = this.buffer_byte_offset_ + read_requested / 8;
         int buffer_bit_offset = this.buffer_bit_offset_ + read_requested % 8;
         buffer_byte_offset += buffer_bit_offset / 8;
         buffer_bit_offset %= 8;
         return buffer_byte_offset > this.buffer_size_?false:(buffer_byte_offset < this.buffer_size_?true:this.buffer_bit_offset_ == 0);
      }
   }

   int read_u8() throws H264BitStreamReader.H264BitStreamReaderException {
      if(!this.validate_buffer(8)) {
         throw new H264BitStreamReader.H264BitStreamReaderException();
      } else {
         byte current_byte = this.buffer_[this.buffer_byte_offset_];
         int value;
         if(this.buffer_bit_offset_ != 0) {
            int current_byte1 = (current_byte & BIT_MASKS[this.buffer_bit_offset_]) << this.buffer_bit_offset_;
            int next_byte = (this.buffer_[this.buffer_byte_offset_ + 1] & REVERTED_BIT_MASKS[this.buffer_bit_offset_]) >> 8 - this.buffer_bit_offset_;
            ++this.buffer_byte_offset_;
            value = current_byte1 | next_byte;
         } else {
            ++this.buffer_byte_offset_;
            value = current_byte;
         }

         return value;
      }
   }

   int read_uv(int bits) throws H264BitStreamReader.H264BitStreamReaderException {
      if(!this.validate_buffer(bits)) {
         throw new H264BitStreamReader.H264BitStreamReaderException();
      } else {
         byte current_byte = this.buffer_[this.buffer_byte_offset_];
         int value;
         if(this.buffer_bit_offset_ != 0) {
            if(bits > 8 - this.buffer_bit_offset_) {
               int second_byte_bits = bits - (8 - this.buffer_bit_offset_);
               int current_byte1 = (current_byte & BIT_MASKS[this.buffer_bit_offset_]) << second_byte_bits;
               int next_byte = (this.buffer_[this.buffer_byte_offset_ + 1] & REVERTED_BIT_MASKS[second_byte_bits]) >> 8 - second_byte_bits;
               value = current_byte1 + next_byte;
               this.inc_buffer_offset(bits);
            } else {
               value = (current_byte & BIT_MASKS[this.buffer_bit_offset_] & REVERTED_BIT_MASKS[bits + this.buffer_bit_offset_]) >> 8 - this.buffer_bit_offset_ - bits;
               this.inc_buffer_offset(bits);
            }
         } else {
            value = (current_byte & REVERTED_BIT_MASKS[bits]) >> 8 - bits;
            this.inc_buffer_offset(bits);
         }

         return value;
      }
   }

   int read_1_bit() throws H264BitStreamReader.H264BitStreamReaderException {
      if(!this.validate_buffer(1)) {
         throw new H264BitStreamReader.H264BitStreamReaderException();
      } else {
         byte current_byte = this.buffer_[this.buffer_byte_offset_];
         int value = current_byte >> 7 - this.buffer_bit_offset_ & 1;
         this.inc_buffer_offset(1);
         return value;
      }
   }

   int read_ue() throws H264BitStreamReader.H264BitStreamReaderException {
      int b = this.read_1_bit();

      int n;
      for(n = 0; b == 0 && n <= 31; ++n) {
         b = this.read_1_bit();
      }

      if(b != 0 && n <= 31) {
         int value = 0;

         int index;
         for(index = n; index >= 8; index -= 8) {
            b = this.read_u8();
            value += b << index - 8;
         }

         if(index > 0) {
            b = this.read_uv(index);
            value += b;
         }

         value = (1 << n) + value - 1;
         return value;
      } else {
         throw new H264BitStreamReader.H264BitStreamReaderException();
      }
   }

   int read_se() throws H264BitStreamReader.H264BitStreamReaderException {
      int value_32 = this.read_ue();
      if(value_32 == 0) {
         byte var3 = 0;
         return var3;
      } else {
         int value = value_32 / 2;
         if((value_32 & 1) == 0) {
            value = -value;
            return value;
         } else {
            ++value;
            return value;
         }
      }
   }

   void skip(int skip) throws H264BitStreamReader.H264BitStreamReaderException {
      if(!this.validate_buffer(skip)) {
         throw new H264BitStreamReader.H264BitStreamReaderException();
      } else {
         this.inc_buffer_offset(skip);
      }
   }


   public static class H264BitStreamReaderException extends Exception {

   }
}
