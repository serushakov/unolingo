package com.sushakov.unolingo.ui.learn.wordcard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WordCardViewModel: ViewModel() {
    val checkedId: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
}