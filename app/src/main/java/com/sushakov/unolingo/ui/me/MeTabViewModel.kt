package com.sushakov.unolingo.ui.me

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.sushakov.unolingo.data.Repository

class MeTabViewModel(private val repository: Repository, lifecycleOwner: LifecycleOwner) :
    ViewModel() {

    suspend fun correctPercentage() = repository.getLastResultsPercentage()
    suspend fun getStreak() = repository.getStreak()
    suspend fun getXp() = repository.getXP()
    suspend fun getLevel() = repository.getLevel()
    suspend fun getXpToNextLevel() = repository.getXpToNextLevel()
    suspend fun getWordsToImprove() = repository.getWordsToImprove()
}