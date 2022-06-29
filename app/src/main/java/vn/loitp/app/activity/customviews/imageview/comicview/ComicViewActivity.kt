package vn.loitp.app.activity.customviews.imageview.comicview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.bumptech.glide.Glide
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_comic_view.*
import vn.loitp.app.R
import java.util.* // ktlint-disable no-wildcard-imports

// https://github.com/nahzur-h/ScollZoomListView
@LogTag("ComicViewActivity")
@IsFullScreen(false)
class ComicViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_comic_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        initData()
    }

    private fun initData() {
        val list = ArrayList<String>()
        list.add("http://truyentranhtuan.com/manga2/onepunch-man/182-8/img-00001.jpg")
        list.add("http://truyentranhtuan.com/manga2/onepunch-man/182-8/img-00002.jpg")
        list.add("http://truyentranhtuan.com/manga2/onepunch-man/182-8/img-00003.jpg")
        list.add("http://truyentranhtuan.com/manga2/onepunch-man/182-8/img-00004.jpg")
        list.add("http://truyentranhtuan.com/manga2/onepunch-man/182-8/img-00005.jpg")

        val comicAdapter = ComicAdapter()
        comicView.adapter = comicAdapter
        comicAdapter.setData(data = list)
    }

    private inner class ComicAdapter : BaseAdapter() {

        private var listData = ArrayList<String>()

        fun setData(data: List<String>) {
            listData.clear()
            listData.addAll(data)
            notifyDataSetChanged()
        }

        override fun getCount(): Int {
            return listData.size
        }

        override fun getItem(position: Int): Any {
            return listData[position]
        }

        override fun getItemId(position: Int): Long {
            return (position + 1000).toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var mConvertView = convertView
            val holder: ComicHolder

            if (mConvertView == null) {
                mConvertView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_comic_view, parent, false)

                holder = ComicHolder()
                holder.ivComic = mConvertView!!.findViewById(R.id.ivComic)
                mConvertView.tag = holder
            } else {
                holder = mConvertView.tag as ComicHolder
            }

            // wont work
//            LImageUtil.load(
//                    context = parent.context,
//                    any = listData[position],
//                    imageView = holder.ivComic
//            )

            holder.ivComic?.let { iv ->
                Glide.with(parent.context)
                    .load(listData[position])
                    .dontAnimate()
                    .into(iv)
            }

            return mConvertView
        }
    }

    internal class ComicHolder {
        var ivComic: ImageView? = null
    }
}
