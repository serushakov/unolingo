package com.sushakov.unolingo.data

import com.sushakov.unolingo.data.word.Word
import com.sushakov.unolingo.data.word.WordDao
import com.sushakov.unolingo.data.word.WordWithTranslations

class Repository private constructor(private val wordDao: WordDao) {

    suspend fun addWord(word: Word) {
        wordDao.createWord(word)
    }

    suspend fun addTranslation(word: Word, translation: Word) {
        wordDao.addTranslation(word, translation)
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