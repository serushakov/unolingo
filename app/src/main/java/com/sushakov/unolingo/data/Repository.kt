package com.sushakov.unolingo.data

import com.sushakov.unolingo.data.word.Word
import com.sushakov.unolingo.data.word.WordDao

class Repository private constructor(private val wordDao: WordDao) {

    suspend fun addWord(word: Word) {
        wordDao.create(word)
    }

    suspend fun getWords() = wordDao.getAll()

    companion object {
        @Volatile private var instance: Repository? = null

        fun getInstance(wordDao: WordDao) =
            instance ?: synchronized(this) {
                instance ?: Repository(wordDao).also { instance = it }
            }
    }
}