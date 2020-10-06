package com.sushakov.unolingo.ui.onboarding

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sushakov.unolingo.data.Repository

class OnboardingViewModel( private val lifecycleOwner: LifecycleOwner) : ViewModel() {
    val nameValue: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val buttonEnabled: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    init {
        nameValue.observe(lifecycleOwner, Observer {
            buttonEnabled.value = it.isNotEmpty()
        })
    }
}