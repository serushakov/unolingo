package com.sushakov.unolingo.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.sushakov.unolingo.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivityViewModel(
    private val repository: Repository,
    private val lifecycleOwner: LifecycleOwner
) : ViewModel() {

    suspend fun loadWords() = withContext(Dispatchers.IO) { repository.fetchWordsIfNeeded() }
}