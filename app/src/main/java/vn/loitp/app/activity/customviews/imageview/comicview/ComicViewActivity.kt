package vn.loitp.app.activity.customviews.imageview.comicview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.bumptech.glide.Glide
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_comic_view.*
import vn.loitp.app.R
import java.util.ArrayList

//https://github.com/nahzur-h/ScollZoomListView
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
        val mData = ArrayList<Int>()
        mData.add(R.drawable.loitp)
        mData.add(R.drawable.loitp)
        mData.add(R.drawable.loitp)
        mData.add(R.drawable.loitp)
        mData.add(R.drawable.loitp)
        mData.add(R.drawable.loitp)
        mData.add(R.drawable.loitp)

        val adapter = MyAdapter()
        scrollZoomListView.adapter = adapter
        adapter.setData(mData)
    }

    private inner class MyAdapter : BaseAdapter() {

        private var mData = ArrayList<Int>()

        fun setData(data: List<Int>) {
            mData.clear()
            mData.addAll(data)
            notifyDataSetChanged()
        }

        override fun getCount(): Int {
            return mData.size
        }

        override fun getItem(position: Int): Any {
            return mData[position]
        }

        override fun getItemId(position: Int): Long {
            return (position + 1000).toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView
            val holder: MyHolder
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.context).inflate(R.layout.row_comic_view, parent, false)
                holder = MyHolder()
                holder.picIv = convertView?.findViewById<View>(R.id.pic_iv) as ImageView
                convertView.tag = holder
            } else {
                holder = convertView.tag as MyHolder
            }
            Glide.with(parent.context)
                    .load(mData[position])
                    .dontAnimate()
                    .into(holder.picIv!!)

            return convertView
        }
    }

    internal class MyHolder {
        var picIv: ImageView? = null
    }
}
