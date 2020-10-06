package com.sushakov.unolingo.ui.me

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.sushakov.unolingo.R
import com.sushakov.unolingo.data.word.Word

class WordListItemAdapter(
    private val context: Context,
    private val dataSource: List<Pair<Word, Int>>
) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, converView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.word_list_item, parent, false) as TextView

        rowView.text = getItem(position).first.text
        return rowView
    }

    override fun getItem(p0: Int) = dataSource[p0]

    override fun getItemId(p0: Int) = dataSource[p0].first.id


    override fun getCount(): Int = dataSource.size

}