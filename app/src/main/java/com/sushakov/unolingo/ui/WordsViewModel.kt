package com.sushakov.unolingo.ui

import android.util.Log
import androidx.lifecycle.*
import com.sushakov.unolingo.data.Repository
import com.sushakov.unolingo.data.word.Word
import com.sushakov.unolingo.data.word.WordWithTranslations
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

const val LANGUAGE = "ENGLISH"

class WordsViewModel(private val repository: Repository, private val lifecycleOwner: LifecycleOwner) : ViewModel() {

    init {
        viewModelScope.launch {
            getWords().observe(lifecycleOwner, Observer { words ->
                if(words.isEmpty()) {
                    viewModelScope.launch {
                        repository.fetchWords()
                    }
                }
                wordsListText.value = words.size.toString()
            })
        }
    }

    val wordText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val translationText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val wordsListText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun getWords() = repository.getWords()

    fun clear() {
        wordText.value = ""
        translationText.value = ""
    }

    fun submitWord() {

        viewModelScope.launch {
            repository.fetchWords()
        }
//        val word = Word(
//            lang = LANGUAGE,
//            text = wordText.value!!
//        )
//
//        val word2 = Word(
//            lang = "FINNISH",
//            text = translationText.value!!
//        )
//
//        viewModelScope.launch {
//            repository.addTranslation(word, word2)
//        }
//        clear()
    }
}