package vn.loitp.app.a.cv.rv.dragAndDropDemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.loitp.R
import vn.loitp.app.a.cv.rv.dragAndDropDemo.callback.WordsDiffCallback

/**
 * A @androidx.recyclerview.widget.RecyclerView adapter to show dropped items
 * */
class SentenceAdapter : ListAdapter<String, SentenceAdapter.WordsViewHolder>(WordsDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WordsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_word_drag_n_drop, parent, false)
        return WordsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: WordsViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    fun addItem(selectedWord: String) {
        val list = ArrayList(currentList)
        list.add(selectedWord)
        submitList(list)
    }

    inner class WordsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(word: String) {
            itemView.findViewById<TextView>(R.id.tvWord).text = word
        }
    }
}
