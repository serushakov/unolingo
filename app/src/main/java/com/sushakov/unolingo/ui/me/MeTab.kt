package com.sushakov.unolingo.ui.me

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sushakov.unolingo.R
import com.sushakov.unolingo.databinding.FragmentMeTabBinding
import com.sushakov.unolingo.InjectorUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
        initializeCollapsingToolbar()

        return binding.root
    }

    private fun initializeCollapsingToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        binding.collapsingToolbarLayout.setExpandedTitleColor(Color.BLACK);
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
            withContext(Dispatchers.IO) {

                val wordsToImprove = viewModel.getWordsToImprove()

                if (wordsToImprove.size == 0) {
                    binding.headerWordsToImprove.visibility = View.GONE
                }

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
    }

    private fun initializeCards() {
        lifecycleScope.launchWhenCreated {
            withContext(Dispatchers.IO) {
                binding.streakCard.apply {
                    emojiText.text = requireContext().getText(R.string.statistics_item_streak_emoji)
                    labelText.text = requireContext().getText(R.string.statistics_item_streak)

                    val streak = viewModel.getStreak()
                    val value = streak.toString()

                    valueText.text = value

                }

                binding.correctCard.apply {
                    emojiText.text =
                        requireContext().getText(R.string.statistics_item_correct_emoji)
                    labelText.text = requireContext().getText(R.string.statistics_item_correct)

                    val percentage = viewModel.correctPercentage()

                    val value = String.format("%.2f", percentage) + "%"
                    valueText.text = value

                }

                binding.xpCard.apply {
                    emojiText.text = requireContext().getText(R.string.statistics_item_xp_emoji)
                    labelText.text = requireContext().getText(R.string.statistics_item_xp)

                    val xp = viewModel.getXp()
                    val value = xp.toString()
                    valueText.text = value
                }

                binding.levelCard.apply {
                    emojiText.text = requireContext().getText(R.string.statistics_item_level_emoji)
                    labelText.text = requireContext().getText(R.string.statistics_item_level)

                    val level = viewModel.getLevel()
                    val value = level.toString()
                    valueText.text = value
                }

                val xpToNextLevel = viewModel.getXpToNextLevel()

                activity?.runOnUiThread {
                    binding.xpToNextLevel.text =
                        resources.getString(R.string.statistics_xp_to_next_level, xpToNextLevel)
                }
            }
        }
    }
}