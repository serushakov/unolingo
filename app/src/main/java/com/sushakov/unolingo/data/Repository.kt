package com.sushakov.unolingo.data

import android.util.Log
import com.sushakov.unolingo.data.record.Record
import com.sushakov.unolingo.data.record.RecordDao
import com.sushakov.unolingo.data.word.Word
import com.sushakov.unolingo.data.word.WordDao
import com.sushakov.unolingo.data.word.WordWithTranslations
import java.lang.Exception

class Repository private constructor(
    private val wordDao: WordDao,
    private val recordDao: RecordDao
) {
    suspend fun addTranslation(word: Word, translation: Word): Long {
        return wordDao.addTranslation(word, translation)
    }

    suspend fun getRandomWordWithTranslations(language: String): WordWithTranslations? {
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

    suspend fun deleteWordWithTranslations(wordId: Long) {
        wordDao.deleteWordWithTranslations(wordId)
    }

    suspend fun addRecord(record: Record) {
        recordDao.addRecord(record)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(wordDao: WordDao, recordDao: RecordDao) =
            instance ?: synchronized(this) {
                instance ?: Repository(wordDao, recordDao).also { instance = it }
            }
    }
}