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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentOnboardingBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_onboarding, container, false)

        val viewModel: OnboardingViewModel = OnboardingViewModel(viewLifecycleOwner)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        binding.button.setOnClickListener {
            callback?.onContinueClick(viewModel.nameValue.value ?: "")
        }


        return binding.root
    }


    fun registerCallback(callback: Callback) {
        this.callback = callback
    }


    interface Callback {
        fun onContinueClick(name: String)
    }

    companion object {
        fun newInstance(): OnboardingFragment {

            return OnboardingFragment()
        }
    }
}