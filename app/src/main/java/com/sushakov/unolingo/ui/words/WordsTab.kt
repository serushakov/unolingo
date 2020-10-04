package com.sushakov.unolingo.ui.words

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sushakov.unolingo.R
import com.sushakov.unolingo.databinding.FragmentWordsTabBinding
import com.sushakov.unolingo.ui.InjectorUtils
import com.sushakov.unolingo.ui.learn.LearnViewModel

class WordsTab : Fragment() {
    companion object {
        fun newInstance() = WordsTab()
    }

    private lateinit var viewModel: WordsTabViewModel
    private lateinit var binding: FragmentWordsTabBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_words_tab, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        val factory =
            InjectorUtils.provideWordsTabViewModelFactory(requireContext(), viewLifecycleOwner)
        viewModel = ViewModelProvider(this, factory)
            .get(WordsTabViewModel::class.java)

        val adapter = WordsAdapter()


        binding.recyclerView.adapter = adapter

        viewModel.words.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.words = it
            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WordsTabViewModel::class.java)
        // TODO: Use the ViewModel
    }

}