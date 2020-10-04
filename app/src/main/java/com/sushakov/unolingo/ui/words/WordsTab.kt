package com.sushakov.unolingo.ui.words

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sushakov.unolingo.R

class WordsTab : Fragment() {

    companion object {
        fun newInstance() = WordsTab()
    }

    private lateinit var viewModel: WordsTabViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_words_tab, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WordsTabViewModel::class.java)
        // TODO: Use the ViewModel
    }

}