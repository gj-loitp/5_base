package vn.loitp.up.a.tut.retrofit2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.hamed.floatinglayout.service.FloatingService.view
import vn.loitp.databinding.IRetrofit2Binding

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
        val binding = IRetrofit2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: IRetrofit2Binding) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(retroCrypto: RetroCrypto, listener: Listener) {
            binding.cl.setOnClickListener { listener.onItemClick(retroCrypto) }
            binding.tvName.text = retroCrypto.currency + " " + System.currentTimeMillis()
            binding.tvPrice.text = retroCrypto.price + " " + System.currentTimeMillis()
        }
    }
}
