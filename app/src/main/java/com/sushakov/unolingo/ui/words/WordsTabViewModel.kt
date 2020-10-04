package com.sushakov.unolingo.ui.words

import androidx.lifecycle.ViewModel
import com.sushakov.unolingo.data.Language
import com.sushakov.unolingo.data.Repository

class WordsTabViewModel(repository: Repository) : ViewModel() {
    val words = repository.getWords(Language.ENGLISH)
}