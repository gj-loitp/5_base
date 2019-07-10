package vn.loitp.app.activity.demo.epubreader;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.core.utilities.LReaderUtil;
import com.function.epub.model.BookInfo;

import java.util.List;

import loitp.basemaster.R;

/**
 * Created by loitp on 08.09.2016.
 */
public class BookInfoGridAdapter extends BaseAdapter {
    private Context context;
    private List<BookInfo> bookInfoList;

    public BookInfoGridAdapter(Context context, List<BookInfo> bookInfoList) {
        this.context = context;
        this.bookInfoList = bookInfoList;
    }

    private final class ViewHolder {
        public TextView title;
        public ImageView coverImage;
    }

    @Override
    public int getCount() {
        if (bookInfoList == null) {
            return 0;
        }
        return bookInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        if (bookInfoList == null) {
            return null;
        }
        return bookInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_book_epub_reader, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.txt_book_title);
            viewHolder.coverImage = (ImageView) convertView.findViewById(R.id.img_cover);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(bookInfoList.get(position).getTitle());
        boolean isCoverImageNotExists = bookInfoList.get(position).isCoverImageNotExists();
        if (!isCoverImageNotExists) {
            // Not searched for coverImage for this position yet. It may exist.
            Bitmap savedBitmap = bookInfoList.get(position).getCoverImageBitmap();
            if (savedBitmap != null) {
                viewHolder.coverImage.setImageBitmap(savedBitmap);
            } else {
                byte[] coverImageAsBytes = bookInfoList.get(position).getCoverImage();
                if (coverImageAsBytes != null) {
                    Bitmap bitmap = LReaderUtil.decodeBitmapFromByteArray(coverImageAsBytes, 100, 200);
                    bookInfoList.get(position).setCoverImageBitmap(bitmap);
                    bookInfoList.get(position).setCoverImage(null);
                    viewHolder.coverImage.setImageBitmap(bitmap);
                } else {
                    // Searched and not found.
                    bookInfoList.get(position).setCoverImageNotExists(true);
                    viewHolder.coverImage.setImageResource(LReaderUtil.getDefaultCover());
                }
            }
        } else {
            // Searched before and not found.
            viewHolder.coverImage.setImageResource(LReaderUtil.getDefaultCover());
        }
        return convertView;
    }
}
