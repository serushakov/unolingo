package com.sushakov.unolingo.ui.words

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.sushakov.unolingo.R
import com.sushakov.unolingo.databinding.FragmentWordsTabBinding
import com.sushakov.unolingo.ui.InjectorUtils
import com.sushakov.unolingo.ui.words.add_word_dialog.AddWordModalSheetDialog


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

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        binding.collapsingToolbarLayout.setExpandedTitleColor(Color.BLACK);


        binding.recyclerView.adapter = adapter

        viewModel.words.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("words changed", "${it.size}")
                adapter.words = it
            }
        })

        binding.addWordFab.setOnClickListener {
            openAddWordBottomSheetDialog()
        }


        return binding.root
    }

    fun openAddWordBottomSheetDialog() {
        val dialog =
            AddWordModalSheetDialog()

        dialog.show(childFragmentManager, AddWordModalSheetDialog.TAG)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WordsTabViewModel::class.java)
        // TODO: Use the ViewModel
    }

}