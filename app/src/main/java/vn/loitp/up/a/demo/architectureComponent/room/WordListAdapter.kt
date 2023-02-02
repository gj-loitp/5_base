package vn.loitp.up.a.demo.architectureComponent.room

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.ext.printBeautyJson
import vn.loitp.databinding.IRoomNoteBinding
import vn.loitp.up.a.demo.architectureComponent.room.md.Word

class WordListAdapter(val callback: Callback?) :
    RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    interface Callback {
        fun onDelete(word: Word)
        fun onUpdate(word: Word)
    }

    private var words = emptyList<Word>()

    inner class WordViewHolder(val binding: IRoomNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {
            binding.tvNote.printBeautyJson(o = word)

            binding.ivDelete.setOnClickListener {
                callback?.onDelete(word = word)
            }
            binding.cardView.setOnClickListener {
                callback?.onUpdate(word = word)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WordViewHolder {
        val binding =
            IRoomNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: WordViewHolder,
        position: Int
    ) {
        holder.bind(words[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setWords(words: List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = words.size
}
