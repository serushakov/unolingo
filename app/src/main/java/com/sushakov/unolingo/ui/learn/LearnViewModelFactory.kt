package com.sushakov.unolingo.ui.learn

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sushakov.unolingo.data.Repository

class LearnViewModelFactory(private val repository: Repository, private val lifecycleOwner: LifecycleOwner): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LearnViewModel(repository, lifecycleOwner) as T
    }
}