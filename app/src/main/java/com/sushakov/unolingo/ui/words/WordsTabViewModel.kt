package com.sushakov.unolingo.ui.words

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.sushakov.unolingo.data.Language
import com.sushakov.unolingo.data.Repository

class WordsTabViewModel(val repository: Repository, val lifecycleOwner: LifecycleOwner) : ViewModel() {
    val words = repository.getWords(Language.ENGLISH)

    fun deleteWord(wordId: Long) {
        lifecycleOwner.lifecycleScope.launchWhenCreated {
            repository.deleteWordWithTranslations(wordId)
        }
    }
}