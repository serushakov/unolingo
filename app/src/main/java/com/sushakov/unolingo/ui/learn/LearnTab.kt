package com.sushakov.unolingo.ui.learn

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sushakov.unolingo.R
import com.sushakov.unolingo.data.Language
import com.sushakov.unolingo.data.word.Word
import com.sushakov.unolingo.data.word.WordWithTranslations
import com.sushakov.unolingo.databinding.FragmentLearnTabBinding
import com.sushakov.unolingo.ui.InjectorUtils
import com.sushakov.unolingo.ui.learn.wordcard.WordCard
import kotlinx.coroutines.launch

class LearnTab : Fragment(), WordCard.OnCardSelectedListener {
    private lateinit var viewModel: LearnViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLearnTabBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_learn_tab, container, false)

        val factory =
            InjectorUtils.provideLearnViewModelFactory(requireContext(), viewLifecycleOwner)
        val viewModel = ViewModelProvider(this, factory)
            .get(LearnViewModel::class.java)

        this.viewModel = viewModel
        binding.learnViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        binding.fetchbutton.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                viewModel.init()
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.selectWord()
        }

        binding.checkButton.setOnClickListener {
            lifecycleScope.launch {
                val correct = viewModel.checkWord()

                Toast.makeText(
                    requireContext(),
                    if (correct) "correct" else "incorrect",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.currentWord.observe(viewLifecycleOwner, currentWordChangeObserver)

        return binding.root
    }

    private val currentWordChangeObserver = Observer<WordWithTranslations?> { nextWord ->
        if (nextWord != null) {
            showNextWordCard(nextWord)
        }
    }

    fun showNextWordCard(wordWithTranslations: WordWithTranslations) {
        lifecycleScope.launchWhenCreated {

            val fragment = WordCard.newInstance(
                wordWithTranslations,
                viewModel.getWordOptions(wordWithTranslations.word),
                Language.SPANISH
            )

            Log.i("debug", wordWithTranslations.word.text)

            val fragmentTransaction = childFragmentManager.beginTransaction();

            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
            fragmentTransaction.replace(R.id.cardContainer, fragment)
            fragmentTransaction.commit();
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LearnTab()
    }


    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is WordCard) {
            fragment.setOnCardSelectedListener(this)
        }
    }

    override fun onCardSelected(word: Word?) {
        viewModel.wordSelected(word)
    }
}