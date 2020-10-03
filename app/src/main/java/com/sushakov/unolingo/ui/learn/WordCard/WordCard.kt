package com.sushakov.unolingo.ui.learn.WordCard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sushakov.unolingo.R
import com.sushakov.unolingo.data.word.Word
import com.sushakov.unolingo.data.word.WordWithTranslations
import com.sushakov.unolingo.databinding.FragmentWordCardBinding
import java.util.ArrayList

class WordCard : Fragment() {

    companion object {
        fun newInstance(word: WordWithTranslations, options: ArrayList<Word>): WordCard {
            val instance = WordCard()

            val args = Bundle().apply {
                putSerializable("word", word)
                putParcelableArrayList("options", options)
            }

            instance.arguments = args

            return instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentWordCardBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_word_card, container, false)

        Log.i("debug", "WORD CARD CREATE")
        binding.setLifecycleOwner { this.lifecycle }


        val word = arguments?.getSerializable("word") as WordWithTranslations

        Log.i("debug",  word.word.text ?: "NO WORD FOUND")

        binding.label = word.word.text

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}