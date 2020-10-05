package com.sushakov.unolingo.ui.me

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sushakov.unolingo.R
import com.sushakov.unolingo.databinding.FragmentMeTabBinding
import com.sushakov.unolingo.ui.InjectorUtils
import com.sushakov.unolingo.ui.learn.LearnViewModel

class MeTab : Fragment() {

    companion object {
        fun newInstance() = MeTab()
    }

    private lateinit var viewModel: MeTabViewModel
    private lateinit var binding: FragmentMeTabBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeViewModel()
        createBinding(inflater, container)

        initializeCards()

        return binding.root
    }

    private fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_me_tab, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initializeViewModel() {
        val factory =
            InjectorUtils.provideMeTabViewModelFactory(requireContext(), viewLifecycleOwner)

        viewModel = ViewModelProvider(this, factory)
            .get(MeTabViewModel::class.java)
    }

    private fun initializeCards() {

        binding.streakCard.apply {
            emojiText.text = requireContext().getText(R.string.statistics_item_streak_emoji)
            labelText.text = requireContext().getText(R.string.statistics_item_streak)
        }

        binding.correctCard.apply {
            emojiText.text = requireContext().getText(R.string.statistics_item_correct_emoji)
            labelText.text = requireContext().getText(R.string.statistics_item_correct)

            lifecycleScope.launchWhenCreated {
                viewModel.correctPercentage().observe(viewLifecycleOwner, Observer {
                    val value = if (it != null) "${String.format("%.2f", it)}%" else "N/A"
                    valueText.text = value
                })
            }
        }

    }


}