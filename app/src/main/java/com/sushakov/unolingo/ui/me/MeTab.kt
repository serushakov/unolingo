package com.sushakov.unolingo.ui.me

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sushakov.unolingo.R
import com.sushakov.unolingo.databinding.FragmentMeTabBinding
import com.sushakov.unolingo.ui.InjectorUtils


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
        initializeListOfWords()

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

    private fun initializeListOfWords() {
        lifecycleScope.launchWhenCreated {
            val wordsToImprove = viewModel.getWordsToImprove()

            Log.d("list to improve", wordsToImprove.toString())

            val adapter = WordListItemAdapter(requireContext(), wordsToImprove)

            binding.wordsToImprove.apply {
                wordsToImprove.forEach {
                    val view = layoutInflater.inflate(R.layout.word_list_item, null) as TextView
                    view.width = this.width
                    view.text = it.first.text

                    addView(view)
                }
            }

        }
    }

    private fun initializeCards() {

        binding.streakCard.apply {
            emojiText.text = requireContext().getText(R.string.statistics_item_streak_emoji)
            labelText.text = requireContext().getText(R.string.statistics_item_streak)

            lifecycleScope.launchWhenCreated {
                val streak = viewModel.getStreak()
                val value = streak.toString()

                valueText.text = value
            }
        }

        binding.correctCard.apply {
            emojiText.text = requireContext().getText(R.string.statistics_item_correct_emoji)
            labelText.text = requireContext().getText(R.string.statistics_item_correct)

            lifecycleScope.launchWhenCreated {
                val percentage = viewModel.correctPercentage()

                val value = String.format("%.2f", percentage) + "%"
                valueText.text = value
            }

        }

        binding.xpCard.apply {
            emojiText.text = requireContext().getText(R.string.statistics_item_xp_emoji)
            labelText.text = requireContext().getText(R.string.statistics_item_xp)

            lifecycleScope.launchWhenCreated {
                val xp = viewModel.getXp()
                val value = xp.toString()
                valueText.text = value
            }
        }

        binding.levelCard.apply {
            emojiText.text = requireContext().getText(R.string.statistics_item_level_emoji)
            labelText.text = requireContext().getText(R.string.statistics_item_level)

            lifecycleScope.launchWhenCreated {
                val level = viewModel.getLevel()
                val value = level.toString()
                valueText.text = value

            }
        }

        lifecycleScope.launchWhenCreated {
            val xpToNextLevel = viewModel.getXpToNextLevel()
            binding.xpToNextLevel.text =
                resources.getString(R.string.statistics_xp_to_next_level, xpToNextLevel)

        }
    }


}