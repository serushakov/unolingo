package com.sushakov.unolingo.ui.learn

import androidx.lifecycle.*
import com.sushakov.unolingo.data.Repository
import com.sushakov.unolingo.data.word.Word
import com.sushakov.unolingo.data.word.WordWithTranslations
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LearnViewModel(
    private val repository: Repository,
    private val lifecycleOwner: LifecycleOwner
) : ViewModel() {

    suspend fun getWord(): WordWithTranslations {
        return repository.getRandomWordWithTranslations()
    }

    suspend fun getWordOptions(ignore: Word): ArrayList<Word> {
        val options = arrayListOf<Word>()

        repeat(3) {
            val ignoreList = mutableListOf(ignore.id)
            ignoreList.addAll(options.map { it.id })

            options += repository.getRandomWord(ignoreList)
        }

        return options
    }

}