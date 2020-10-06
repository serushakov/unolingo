package com.sushakov.unolingo.ui.learn

import android.util.Log
import androidx.lifecycle.*
import com.sushakov.unolingo.data.Language
import com.sushakov.unolingo.data.Repository
import com.sushakov.unolingo.data.record.Record
import com.sushakov.unolingo.data.word.Word
import com.sushakov.unolingo.data.word.WordWithTranslations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LearnViewModel(
    private val repository: Repository,
    private val lifecycleOwner: LifecycleOwner
) : ViewModel() {
    val currentWord: MutableLiveData<WordWithTranslations> by lazy {
        MutableLiveData<WordWithTranslations>()
    }

    val selectedWord: MutableLiveData<Word?> by lazy {
        MutableLiveData<Word?>()
    }


    fun setSelectedWord(word: Word?) {
        selectedWord.value = word;
    }

    suspend fun pickNextWord(): WordWithTranslations? {
        val selectedWord = repository.getRandomWordWithTranslations(Language.ENGLISH)
        currentWord.value = selectedWord

        return selectedWord
    }

    suspend fun checkWord(): Boolean {
        val correctWord = currentWord.value?.translations?.find { it.lang == Language.SPANISH }
        val checkedWord = selectedWord.value

        require(correctWord != null && checkedWord != null) { "Both words should not be null" }

        selectedWord.value = null;


        val isCorrect = correctWord.text.contentEquals(checkedWord.text)

        val wordId = currentWord.value?.word?.id

        if (wordId != null) {
            saveResult(wordId, isCorrect)
        }


        return isCorrect
    }

    private suspend fun saveResult(wordId: Long, result: Boolean) {
        val record = Record(wordId = wordId, result = result)
        repository.addRecord(record)
    }

    suspend fun getWordOptions(ignore: Word): ArrayList<Word> {
        val options = arrayListOf<Word>()

        repeat(3) {
            val ignoreList = mutableListOf(ignore.id)
            ignoreList.addAll(options.map { it.id })

            options += repository.getRandomWord(ignoreList, Language.SPANISH)
        }

        return options
    }
}