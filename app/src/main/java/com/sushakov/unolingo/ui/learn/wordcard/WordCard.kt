package com.sushakov.unolingo.ui.learn.wordcard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.button.MaterialButtonToggleGroup.OnButtonCheckedListener
import com.sushakov.unolingo.MainActivity
import com.sushakov.unolingo.R
import com.sushakov.unolingo.data.word.Word
import com.sushakov.unolingo.data.word.WordWithTranslations
import com.sushakov.unolingo.databinding.FragmentWordCardBinding
import java.util.ArrayList

class WordCard : Fragment() {

    private lateinit var binding: FragmentWordCardBinding

    val toggleButtons = mutableListOf<MaterialButton>()


    companion object {
        fun newInstance(word: WordWithTranslations, options: ArrayList<Word>, language: String): WordCard {
            val instance = WordCard()

            val args = Bundle().apply {
                putSerializable("word", word)
                putParcelableArrayList("options", options)
                putString("language", language)
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
        val options = arguments?.getParcelableArrayList<Word>("options")
        val language = arguments?.getString("language")

        Log.i("debug", word.word.text ?: "NO WORD FOUND")

        binding.label = word.word.text
        binding.viewModel = WordCardViewModel()

        this.binding = binding

        toggleButtons.add(binding.topStartButtonContainer)
        toggleButtons.add(binding.topEndButtonContainer)
        toggleButtons.add(binding.bottomStartButtonContainer)
        toggleButtons.add(binding.bottomEndButtonContainer)

        toggleButtons.random().text = word.translations.find { it.lang == language }?.text

        options?.forEach { option ->
            toggleButtons.find {
                it.text.isEmpty()
            }?.text = option.text
        }

        toggleButtons.forEach {
            it.addOnCheckedChangeListener { button, isChecked ->
                if (isChecked) {
                    clearChecks(ignore = button)
                }
            }


        }

        return binding.root
    }


    private fun clearChecks(ignore: MaterialButton) {
        Thread(Runnable {
            activity?.runOnUiThread {
                toggleButtons.forEach {
                    if (it != ignore) {
                        it.isChecked = false
                    }
                }
            }
        }).start()
    }

}