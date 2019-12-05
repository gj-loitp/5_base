package vn.loitp.app.activity.demo.architecturecomponent.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.core.utilities.LUIUtil
import loitp.basemaster.R
import vn.loitp.app.activity.demo.architecturecomponent.room.model.Word

class WordListAdapter(val callback: Callback?) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    interface Callback {
        fun onDelete(word: Word)
        fun onUpdate(word: Word)
    }

    private var words = emptyList<Word>()

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNote: TextView = itemView.findViewById(R.id.tvNote)
        val ivDelete: ImageView = itemView.findViewById(R.id.ivDelete)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_room_note, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = words[position]
        LUIUtil.printBeautyJson(word, holder.tvNote)

        holder.ivDelete.setOnClickListener {
            callback?.onDelete(word)
        }
        holder.cardView.setOnClickListener {
            callback?.onUpdate(word)
        }
    }

    fun setWords(words: List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = words.size
}