package vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerviewyayandroid

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.core.utilities.LImageUtil
import com.views.recyclerview.parallaxyay.ParallaxViewHolder
import kotlinx.android.synthetic.main.row_parrallax_yaya.view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerviewyayandroid.FakeData.Companion.instance

class YayaParallaxAdapter internal constructor(private val context: Context, private val callback: Callback?) :
        RecyclerView.Adapter<YayaParallaxAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.row_parrallax_yaya, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind()
    }

    override fun getItemCount(): Int {
        return instance.stringList.size
    }

    /**
     * # CAUTION:
     * ViewHolder must extend from ParallaxViewHolder
     */
    inner class ViewHolder(v: View) : ParallaxViewHolder(v) {

        override fun getParallaxImageId(): Int {
            return R.id.backgroundImage
        }

        @SuppressLint("SetTextI18n")
        fun bind() {
            LImageUtil.load(context = context, url = instance.stringList[bindingAdapterPosition], imageView = itemView.backgroundImage)
            itemView.label.text = "Row $bindingAdapterPosition"
            itemView.rootView.setOnClickListener {
                callback?.onClick(bindingAdapterPosition)
            }
            itemView.rootView.setOnLongClickListener {
                callback?.onLongClick(bindingAdapterPosition)
                true
            }

            // # CAUTION:
            // Important to call this method
            itemView.backgroundImage.reuse()
        }
    }

    interface Callback {
        fun onClick(pos: Int)
        fun onLongClick(pos: Int)
    }

}
