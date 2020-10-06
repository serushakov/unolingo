package com.sushakov.unolingo.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.sushakov.unolingo.R
import com.sushakov.unolingo.databinding.FragmentOnboardingBinding

class OnboardingFragment private constructor() : Fragment() {
    private var callback: Callback? = null
    private lateinit var binding: FragmentOnboardingBinding
    private lateinit var viewModel: OnboardingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_onboarding, container, false)

        viewModel = OnboardingViewModel(viewLifecycleOwner)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        binding.button.setOnClickListener {
            callback?.onNameEnter(viewModel.nameValue.value ?: "")
            showGreeting()
        }

        binding.goButton.setOnClickListener {
            callback?.onContinueClick()
        }

        launchAnimation()


        return binding.root
    }


    private fun launchAnimation() {
        binding.title
            .animate()
            .alpha(1f)
            .setDuration(300)
            .withEndAction {
                binding.linearLayoutCompat
                    .animate()
                    .alpha(1f)
                    .setDuration(300)
                    .setStartDelay(1000)
                    .start()

                binding.button
                    .animate()
                    .alpha(1f)
                    .setDuration(300)
                    .setStartDelay(1000)
                    .start()
            }
            .start()
    }

    private fun showGreeting() {
        binding.greetingTitle.text = resources.getString(
            R.string.onboarding_greeting,
            viewModel.nameValue.value ?: ""
        )
        binding.greetingContainer.translationZ = 10f
        binding.greetingContainer
            .animate()
            .alpha(1f)
            .start()
    }


    fun registerCallback(callback: Callback) {
        this.callback = callback
    }


    interface Callback {
        fun onContinueClick()
        fun onNameEnter(name: String)
    }

    companion object {
        fun newInstance(): OnboardingFragment {

            return OnboardingFragment()
        }
    }
}