package com.sushakov.unolingo.data

import android.util.Log
import com.sushakov.unolingo.data.word.Word
import com.sushakov.unolingo.data.word.WordDao
import com.sushakov.unolingo.data.word.WordWithTranslations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class Repository private constructor(private val wordDao: WordDao) {
    suspend fun addWord(word: Word) {
        wordDao.createWord(word)
    }

    suspend fun addTranslation(word: Word, translation: Word) {
        wordDao.addTranslation(word, translation)
    }

    suspend fun getWordById(wordId: Long) {
        wordDao.getWordById(wordId)
    }

    suspend fun getRandomWordWithTranslations(): WordWithTranslations {
        return wordDao.getRandomWordWithTranslations()
    }

    suspend fun getRandomWord(ignoreList: List<Long> = emptyList()): Word {
        return wordDao.getRandomWord(ignoreList)
    }

    suspend fun fetchWords() {
        try {
            val wordLists = WordsApi.retrofitService.getWords()

            val pairs = wordLists.map {
                it[0] to it[1]
            }

            wordDao.addTranslationPairs(pairs)
        } catch (e: Exception) {
            Log.e("fetch", "Failed to fetch words", e)
            // TODO Handle exception
        }

    }

    fun getWords() = wordDao.getAll()
    fun getRelations() = wordDao.getRelations()

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(wordDao: WordDao) =
            instance ?: synchronized(this) {
                instance ?: Repository(wordDao).also { instance = it }
            }
    }
}