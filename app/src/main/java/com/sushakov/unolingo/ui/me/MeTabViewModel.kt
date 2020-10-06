package com.sushakov.unolingo.ui.me

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.sushakov.unolingo.data.Repository

class MeTabViewModel(private val repository: Repository, lifecycleOwner: LifecycleOwner) :
    ViewModel() {

    fun correctPercentage() = repository.getLastResultsPercentage()

    fun getStreak() = repository.getStreak()
}