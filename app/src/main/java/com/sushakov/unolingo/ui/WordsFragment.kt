package com.sushakov.unolingo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sushakov.unolingo.R
import com.sushakov.unolingo.databinding.FragmentWordsBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class WordsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentWordsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_words, container, true)

        binding.setLifecycleOwner { this.lifecycle }

        val factory =
            InjectorUtils.provideWordsViewModelFactory(requireContext())
        val viewModel = ViewModelProvider(this, factory)
            .get(WordsViewModel::class.java)

        binding.wordViewModel = viewModel

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getWords().observe(viewLifecycleOwner, Observer { words ->
                viewModel.wordsListText.value = words.toString()
            })
        }

        binding.go.setOnClickListener {
            viewModel.submitWord()
        }


        return binding.root
    }
}