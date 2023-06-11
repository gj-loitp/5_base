package vn.loitp.up.a.cv.rv.gv

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.loitp.core.ext.loadGlide
import vn.loitp.up.common.Constants


class GridViewAdapter() : BaseAdapter() {
    private val list = ArrayList<String>()
    private var isLoadImageNetwork = true

    private class ViewHolder {
        var tv: TextView? = null
        var iv: ImageView? = null
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(i: Int): Any {
        return list[i]
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    fun updateList(list: ArrayList<String>, isLoadImageNetwork: Boolean) {
        this.isLoadImageNetwork = isLoadImageNetwork
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getView(
        position: Int, convertView: View?, parent: ViewGroup
    ): View? {
        Log.d("loitpp", "getView position $position")
        var view = convertView
        val viewHolder: ViewHolder
        if (view == null) {
            val inflater =
                parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(vn.loitp.R.layout.i_gv, parent, false)
            viewHolder = ViewHolder()
            viewHolder.tv = view.findViewById(vn.loitp.R.id.tv)
            viewHolder.iv = view.findViewById(vn.loitp.R.id.iv)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.tv?.text = list[position]

        if (isLoadImageNetwork) {
            if (position % 2 == 0) {
                viewHolder.iv?.loadGlide(Constants.URL_IMG_LARGE)
            } else {
                viewHolder.iv?.loadGlide(Constants.URL_IMG_LARGE_2)
            }
            viewHolder.tv?.isVisible = true
        } else {
            val path = list[position]
            viewHolder.tv?.isVisible = false
            viewHolder.iv?.let { iv ->
                iv.loadGlide(path)

//                Glide.with(iv.context)
//                    .load(path)
//                    .placeholder(R.drawable.ic_launcher)
//                    .error(R.drawable.l_error_404)
//                    .into(iv)

//                Glide.with(iv.context)
//                    .asBitmap().load(File(path))
//                    .listener(object : RequestListener<Bitmap?> {
//                        override fun onLoadFailed(
//                            e: GlideException?,
//                            model: Any?,
//                            target: Target<Bitmap?>?,
//                            isFirstResource: Boolean
//                        ): Boolean {
//                            iv.setImageDrawable(null)
//                            return false
//                        }
//
//                        override fun onResourceReady(
//                            resource: Bitmap?,
//                            model: Any?,
//                            target: Target<Bitmap?>?,
//                            dataSource: DataSource?,
//                            isFirstResource: Boolean
//                        ): Boolean {
//                            iv.setImageBitmap(resource)
//                            return false;
//                        }
//                    }
//                    ).submit()


            }
        }
        return view
    }
}
