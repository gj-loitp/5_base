package com.artfulbits.libstream;

import java.nio.ByteBuffer;

class H264SpsInfo {

   int width;
   int height;
   int num_reorder_frames;


   private static boolean h264_nal_equal_rbsp(byte[] buffer, int offset, int buffer_size) {
      if(buffer_size < 3) {
         return true;
      } else {
         for(int end_offset = buffer_size - 3; offset <= end_offset; ++offset) {
            if(buffer[offset] == 0 && buffer[offset + 1] == 0 && buffer[offset + 2] == 3) {
               return false;
            }
         }

         return true;
      }
   }

   private static void h264_nal_2_rbsp(ByteBuffer buffer) {
      if(buffer.position() >= 3) {
         int end_offset = buffer.position() - 3;

         for(int offset = 0; offset <= end_offset; ++offset) {
            if(buffer.get(offset) == 0 && buffer.get(offset + 1) == 0 && buffer.get(offset + 2) == 3) {
               remove(buffer, offset + 2);
               end_offset = buffer.position() - 3;
               offset += 2;
            }
         }

      }
   }

   private static void remove(ByteBuffer buffer, int offset) {
      if(offset < buffer.position()) {
         System.arraycopy(buffer.array(), offset + 1, buffer.array(), offset, buffer.position() - offset - 1);
         buffer.position(buffer.position() - 1);
      }

   }

   private static void skip_scaling_list(H264BitStreamReader reader, int sizeOfScalingList) throws H264BitStreamReader.H264BitStreamReaderException {
      int lastScale = 8;
      int nextScale = 8;

      for(int j = 0; j < sizeOfScalingList; ++j) {
         if(nextScale != 0) {
            int delta_scale = reader.read_se();
            nextScale = (lastScale + delta_scale + 256) % 256;
         }

         if(nextScale == 0) {
            break;
         }

         lastScale = nextScale;
      }

   }

   private static boolean hrd_parameters(H264BitStreamReader reader) throws H264BitStreamReader.H264BitStreamReaderException {
      int cpb_cnt_minus1 = reader.read_ue();
      if(cpb_cnt_minus1 > 1024) {
         return false;
      } else {
         reader.skip(8);

         for(int SchedSelIdx = 0; SchedSelIdx <= cpb_cnt_minus1; ++SchedSelIdx) {
            int bit_rate_value_minus1 = reader.read_ue();
            int cpb_size_value_minus1 = reader.read_ue();
            reader.skip(1);
         }

         reader.skip(20);
         return true;
      }
   }

   private static H264SpsInfo seq_parameter_set_data(H264BitStreamReader reader) {
      try {
         int e = reader.read_u8();
         reader.skip(16);
         int seq_parameter_set_id = reader.read_ue();
         int log2_max_frame_num_minus4;
         int pic_order_cnt_type;
         int max_num_ref_frames;
         int pic_width_in_mbs_minus1;
         int pic_height_in_map_units_minus1;
         int frame_mbs_only_flag;
         if(e == 100 || e == 110 || e == 122 || e == 244 || e == 44 || e == 83 || e == 86 || e == 118 || e == 128 || e == 138 || e == 144) {
            log2_max_frame_num_minus4 = reader.read_ue();
            if(log2_max_frame_num_minus4 == 3) {
               reader.skip(1);
            }

            pic_order_cnt_type = reader.read_ue();
            max_num_ref_frames = reader.read_ue();
            reader.skip(1);
            pic_width_in_mbs_minus1 = reader.read_1_bit();
            if(pic_width_in_mbs_minus1 != 0) {
               for(pic_height_in_map_units_minus1 = 0; pic_height_in_map_units_minus1 < (log2_max_frame_num_minus4 != 3?8:12); ++pic_height_in_map_units_minus1) {
                  frame_mbs_only_flag = reader.read_1_bit();
                  if(frame_mbs_only_flag != 0) {
                     if(pic_height_in_map_units_minus1 < 6) {
                        skip_scaling_list(reader, 16);
                     } else {
                        skip_scaling_list(reader, 64);
                     }
                  }
               }
            }
         }

         log2_max_frame_num_minus4 = reader.read_ue();
         pic_order_cnt_type = reader.read_ue();
         int frame_cropping_flag;
         if(pic_order_cnt_type == 0) {
            max_num_ref_frames = reader.read_ue();
         } else if(pic_order_cnt_type == 1) {
            reader.skip(1);
            max_num_ref_frames = reader.read_se();
            pic_width_in_mbs_minus1 = reader.read_se();
            pic_height_in_map_units_minus1 = reader.read_ue();
            if(pic_height_in_map_units_minus1 > 1024) {
               return null;
            }

            for(frame_mbs_only_flag = 0; frame_mbs_only_flag < pic_height_in_map_units_minus1; ++frame_mbs_only_flag) {
               frame_cropping_flag = reader.read_se();
            }
         }

         max_num_ref_frames = reader.read_ue();
         reader.skip(1);
         pic_width_in_mbs_minus1 = reader.read_ue();
         pic_height_in_map_units_minus1 = reader.read_ue();
         frame_mbs_only_flag = reader.read_1_bit();
         if(frame_mbs_only_flag == 0) {
            reader.skip(1);
         }

         reader.skip(1);
         frame_cropping_flag = reader.read_1_bit();
         int frame_crop_left_offset = 0;
         int frame_crop_right_offset = 0;
         int frame_crop_top_offset = 0;
         int frame_crop_bottom_offset = 0;
         if(frame_cropping_flag != 0) {
            frame_crop_left_offset = reader.read_ue();
            frame_crop_right_offset = reader.read_ue();
            frame_crop_top_offset = reader.read_ue();
            frame_crop_bottom_offset = reader.read_ue();
         }

         H264SpsInfo info = new H264SpsInfo();
         info.width = (pic_width_in_mbs_minus1 + 1) * 16 - frame_crop_left_offset * 2 - frame_crop_right_offset * 2;
         info.height = (2 - frame_mbs_only_flag) * (pic_height_in_map_units_minus1 + 1) * 16 - frame_crop_top_offset * 2 - frame_crop_bottom_offset * 2;
         int vui_parameters_present_flag = reader.read_1_bit();
         if(vui_parameters_present_flag != 0) {
            int aspect_ratio_info_present_flag = reader.read_1_bit();
            int overscan_info_present_flag;
            if(aspect_ratio_info_present_flag != 0) {
               overscan_info_present_flag = reader.read_u8();
               short video_signal_type_present_flag = 255;
               if(overscan_info_present_flag == video_signal_type_present_flag) {
                  reader.skip(32);
               }
            }

            overscan_info_present_flag = reader.read_1_bit();
            if(overscan_info_present_flag != 0) {
               reader.skip(1);
            }

            int var31 = reader.read_1_bit();
            int chroma_loc_info_present_flag;
            if(var31 != 0) {
               reader.skip(4);
               chroma_loc_info_present_flag = reader.read_1_bit();
               if(chroma_loc_info_present_flag != 0) {
                  reader.skip(24);
               }
            }

            chroma_loc_info_present_flag = reader.read_1_bit();
            int timing_info_present_flag;
            int nal_hrd_parameters_present_flag;
            if(chroma_loc_info_present_flag != 0) {
               timing_info_present_flag = reader.read_ue();
               nal_hrd_parameters_present_flag = reader.read_ue();
            }

            timing_info_present_flag = reader.read_1_bit();
            if(timing_info_present_flag != 0) {
               reader.skip(65);
            }

            nal_hrd_parameters_present_flag = reader.read_1_bit();
            if(0 != nal_hrd_parameters_present_flag && !hrd_parameters(reader)) {
               return null;
            }

            int vcl_hrd_parameters_present_flag = reader.read_1_bit();
            if(0 != vcl_hrd_parameters_present_flag && !hrd_parameters(reader)) {
               return null;
            }

            if(nal_hrd_parameters_present_flag != 0 || vcl_hrd_parameters_present_flag != 0) {
               reader.skip(1);
            }

            reader.skip(1);
            int bitstream_restriction_flag = reader.read_1_bit();
            if(0 != bitstream_restriction_flag) {
               reader.skip(1);
               int max_bytes_per_pic_denom = reader.read_ue();
               int max_bits_per_mb_denom = reader.read_ue();
               int log2_max_mv_length_horizontal = reader.read_ue();
               int log2_max_mv_length_vertical = reader.read_ue();
               int num_reorder_frames = reader.read_ue();
               info.num_reorder_frames = num_reorder_frames;
               int max_dec_frame_buffering = reader.read_ue();
            }
         }

         return info;
      } catch (Exception var30) {
         var30.printStackTrace();
         return null;
      }
   }

   static H264SpsInfo getSpsInfo(byte[] sps, int sps_size) {
      try {
         H264BitStreamReader e = new H264BitStreamReader(sps, sps_size);
         int nal_unit_type = e.read_u8();
         nal_unit_type &= 31;
         if(nal_unit_type != 7) {
            return null;
         } else if(h264_nal_equal_rbsp(sps, 1, sps_size - 1)) {
            return seq_parameter_set_data(e);
         } else {
            ByteBuffer nal = ByteBuffer.allocate(sps_size - 1);
            nal.put(sps, 1, sps_size - 1);
            h264_nal_2_rbsp(nal);
            H264BitStreamReader reader_rbsp = new H264BitStreamReader(nal.array(), nal.position());
            return seq_parameter_set_data(reader_rbsp);
         }
      } catch (Exception var6) {
         var6.printStackTrace();
         return null;
      }
   }
}
