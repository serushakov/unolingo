package com.sushakov.unolingo.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.sushakov.unolingo.data.Repository

class MainActivityViewModel(
    private val repository: Repository,
    private val lifecycleOwner: LifecycleOwner
) : ViewModel() {

}