package com.sushakov.unolingo.ui.learn.wordcard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.button.MaterialButton
import com.sushakov.unolingo.R
import com.sushakov.unolingo.data.word.Word
import com.sushakov.unolingo.data.word.WordWithTranslations
import com.sushakov.unolingo.databinding.FragmentWordCardBinding
import kotlinx.coroutines.runBlocking
import java.util.ArrayList

class WordCard : Fragment() {
    private lateinit var binding: FragmentWordCardBinding
    private val toggleButtons = mutableListOf<MaterialButton>()
    private lateinit var callback: OnCardSelectedListener

    private var word: WordWithTranslations? = null
    private var language: String? = null
    private var options: ArrayList<Word>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_word_card, container, false)
        binding.setLifecycleOwner { this.lifecycle }


        extractArguments()
        fillButtons()
        setCardLabel()

        return binding.root
    }

    private fun extractArguments() {
        word = arguments?.getSerializable("word") as WordWithTranslations?
        options = arguments?.getParcelableArrayList<Word>("options")
        language = arguments?.getString("language")
    }

    private fun fillButtons() {
        toggleButtons.addAll(
            listOf(
                binding.topStartButtonContainer,
                binding.topEndButtonContainer,
                binding.bottomStartButtonContainer,
                binding.bottomEndButtonContainer
            )
        )

        chooseCorrectButton()
        fillIncorrectOptions()
        setButtonListeners()
    }

    private fun chooseCorrectButton() {
        val correctButton = toggleButtons.random()
        val correctWord = word?.translations?.find { it.lang == language }
        correctButton.text = correctWord?.text
        correctButton.tag = correctWord;
    }

    private fun fillIncorrectOptions() {
        options?.forEach { option ->
            val emptyButton = toggleButtons.find {
                it.text.isEmpty()
            }
            emptyButton?.text = option.text
            emptyButton?.tag = option
        }
    }

    private fun setButtonListeners() {
        toggleButtons.forEach {
            it.setOnClickListener { view ->
                val button = view as MaterialButton
                button.isCheckable = !button.isCheckable
                button.isChecked = button.isCheckable

                callback.onCardSelected(if (button.isChecked) button.tag as Word else null)
                clearChecks(button)
            }
        }
    }

    private fun setCardLabel() {
        binding.label = word?.word?.text
    }

    // Unchecks all buttons excluding `ignore`
    private fun clearChecks(ignore: MaterialButton) {
        Thread(Runnable {
            activity?.runOnUiThread {
                toggleButtons.forEach {
                    if (it != ignore && it.isChecked) {
                        it.isChecked = false
                        it.isCheckable = false
                    }
                }
            }
        }).start()
    }

    fun setOnCardSelectedListener(callback: OnCardSelectedListener) {
        this.callback = callback
    }

    interface OnCardSelectedListener {
        fun onCardSelected(word: Word?)
    }

    companion object {
        fun newInstance(
            word: WordWithTranslations,
            options: ArrayList<Word>,
            language: String
        ): WordCard {
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
}