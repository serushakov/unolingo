package com.sushakov.unolingo.ui

import android.util.Log
import androidx.lifecycle.*
import com.sushakov.unolingo.data.Repository
import com.sushakov.unolingo.data.word.Word
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

const val LANGUAGE = "ENGLISH"

class WordsViewModel(private val repository: Repository) : ViewModel() {
    val wordText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val translationText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val wordsListText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    suspend fun getWords() = repository.getWords()

    suspend fun addWord(word: Word) = repository.addWord(word)

    fun clear() {
        wordText.value = ""
        translationText.value = ""
    }

    fun submitWord() {
        val word = Word(
            lang = LANGUAGE,
            text = wordText.value!!
        )

        viewModelScope.launch {
            addWord(word);
        }
        clear()
    }
}