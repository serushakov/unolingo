package com.sushakov.unolingo.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sushakov.unolingo.data.Repository
import com.sushakov.unolingo.ui.onboarding.OnboardingViewModel

class MainActivityViewModelFactory(private val repository: Repository, private val lifecycleOwner: LifecycleOwner): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(
            repository,
            lifecycleOwner
        ) as T
    }
}