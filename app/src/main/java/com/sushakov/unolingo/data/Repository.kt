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

    suspend fun addTranslation(word: Word, translation: Word): Long {
        return wordDao.addTranslation(word, translation)
    }

    suspend fun getWordById(wordId: Long) {
        wordDao.getWordById(wordId)
    }

    suspend fun getRandomWordWithTranslations(language: String): WordWithTranslations {
        return wordDao.getRandomWordWithTranslations(language)
    }

    suspend fun getRandomWord(ignoreList: List<Long> = emptyList(), language: String): Word {
        return wordDao.getRandomWord(ignoreList, language)
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
    fun getWords(language: String) = wordDao.getAll(language)
    fun getRelations() = wordDao.getRelations()

    suspend fun deleteWordWithTranslations(wordId: Long) {
        wordDao.deleteWordWithTranslations(wordId)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(wordDao: WordDao) =
            instance ?: synchronized(this) {
                instance ?: Repository(wordDao).also { instance = it }
            }
    }
}