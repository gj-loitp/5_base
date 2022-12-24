package vn.loitp.a.tut.retrofit2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.i_retrofit2.view.*
import vn.loitp.R

class Retrofit2Adapter(
    private val cryptoList: ArrayList<RetroCrypto>,
    private val listener: Listener
) : RecyclerView.Adapter<Retrofit2Adapter.ViewHolder>() {
    interface Listener {
        fun onItemClick(retroCrypto: RetroCrypto)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(cryptoList[position], listener)
    }

    override fun getItemCount(): Int = cryptoList.count()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.i_retrofit2, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(retroCrypto: RetroCrypto, listener: Listener) {
            itemView.setOnClickListener { listener.onItemClick(retroCrypto) }
            itemView.tvName.text = retroCrypto.currency + " " + System.currentTimeMillis()
            itemView.tvPrice.text = retroCrypto.price + " " + System.currentTimeMillis()
        }
    }
}
