package com.sushakov.unolingo.ui.words

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sushakov.unolingo.R
import com.sushakov.unolingo.data.word.WordWithTranslations

class WordsAdapter : RecyclerView.Adapter<WordItemViewHolder>() {
    var words = listOf<WordWithTranslations>()
        set(value) {
            field = value
            notifyDataSetChanged() // TODO: "Implement proper data change notification"
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val view = layoutInflater
            .inflate(R.layout.word_list_item, parent, false) as TextView

        return WordItemViewHolder(view)

    }

    override fun getItemCount() = words.size

    override fun onBindViewHolder(holder: WordItemViewHolder, position: Int) {
        val wordWithTranslations = words[position]

        holder.textView.text = wordWithTranslations.word.text

    }

    fun getItemAt(index: Int) = words[index]


}