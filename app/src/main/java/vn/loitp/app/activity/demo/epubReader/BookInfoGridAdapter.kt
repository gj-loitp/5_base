package vn.loitp.app.activity.demo.epubReader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.loitp.core.utilities.LReaderUtil
import com.loitp.func.epub.model.BookInfo
import vn.loitp.app.R

class BookInfoGridAdapter(
    private val bookInfoList: ArrayList<BookInfo>
) : BaseAdapter() {

    private class ViewHolder {
        var tvBookTitle: TextView? = null
        var ivCover: ImageView? = null
    }

    override fun getCount(): Int {
        return bookInfoList.size
    }

    override fun getItem(i: Int): Any {
        return bookInfoList[i]
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var view = convertView
        val viewHolder: ViewHolder
        if (view == null) {
            val inflater =
                parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.view_item_book_epub_reader, parent, false)
            viewHolder = ViewHolder()
            viewHolder.tvBookTitle = view.findViewById(R.id.tvBookTitle)
            viewHolder.ivCover = view.findViewById(R.id.ivCover)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.tvBookTitle?.text = bookInfoList[position].title
        val isCoverImageNotExists = bookInfoList[position].isCoverImageNotExists
        if (isCoverImageNotExists) {
            viewHolder.ivCover?.setImageResource(LReaderUtil.defaultCover)
        } else {
            val savedBitmap = bookInfoList[position].coverImageBitmap
            if (savedBitmap == null) {
                val coverImageAsBytes = bookInfoList[position].coverImage
                if (coverImageAsBytes == null) {
                    bookInfoList[position].isCoverImageNotExists = true
                    viewHolder.ivCover?.setImageResource(LReaderUtil.defaultCover)
                } else {
                    val bitmap = LReaderUtil.decodeBitmapFromByteArray(
                        coverImage = coverImageAsBytes,
                        reqWidth = 100,
                        reqHeight = 200
                    )
                    bookInfoList[position].coverImageBitmap = bitmap
                    bookInfoList[position].coverImage = null
                    viewHolder.ivCover?.setImageBitmap(bitmap)
                }
            } else {
                viewHolder.ivCover?.setImageBitmap(savedBitmap)
            }
        }
        return view
    }
}
