package com.sushakov.unolingo.data

import android.R.attr.level
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sushakov.unolingo.data.record.Record
import com.sushakov.unolingo.data.record.RecordDao
import com.sushakov.unolingo.data.word.Word
import com.sushakov.unolingo.data.word.WordDao
import com.sushakov.unolingo.data.word.WordWithTranslations
import java.lang.Math.pow
import kotlin.math.floor
import kotlin.math.pow


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

    fun getLastResultsPercentage(): LiveData<Double?> {
        val results = recordDao.getLastResults()

        return Transformations.distinctUntilChanged(
            Transformations.map(results) {
                Log.d("records", it.toString())
                val correctItems = it.filter { item -> item.result }
                val ratio = (correctItems.size.toDouble() / it.size)
                ratio * 100.0
            }
        )
    }

    /**
     * This is not the best way to find this out,
     * but I don't have time to do a proper SQL query
     */
    fun getStreak(): LiveData<Int> {
        val results = recordDao.getLastResults()

        return Transformations.distinctUntilChanged(
            Transformations.map(results) {
                Log.d("streak", it.toString())
                it.indexOfFirst { item -> !item.result }
            }
        )
    }

    /**
     * This is not the best way to find this out,
     * but I don't have time to do a proper SQL query
     */
    fun getXP(): LiveData<Int> {
        val correctAnswers = recordDao.getCorrectAnswerCount()

        return Transformations.distinctUntilChanged(Transformations.map(correctAnswers) { it * 35 })
    }


    private fun getXpToNextLevel(xp: Int): Double {
        return floor(xp + 300 * 2.0.pow(xp / 7.0));
    };


    /**
     * Converts level into an XP value required to get that level
     */
    private fun levelToXp(level: Int): Double {
        var xp = 0.0;

        for (i in (1..level)) {
            xp += this.getXpToNextLevel(i);
        }

        return floor(xp / 4.0);
    };

    /**
     * Finds user's current level by finding which level bracket current xp belongs to
     */
    fun getLevel(): LiveData<Int> {
        val xp = getXP()

        return Transformations.distinctUntilChanged(
            Transformations.map(xp) {
                var level = 1
                while (true) {
                    if (levelToXp(level + 1).toInt() > it) break
                    level++
                }
                level
            }
        )
    }

    fun getXpToNextLevel(): LiveData<Int> {
        val xp = getXP()

        return Transformations.distinctUntilChanged(
            Transformations.map(xp) {
                var level = 1
                while (true) {
                    if (levelToXp(level + 1).toInt() > it ) break
                    level++
                }

                levelToXp(level + 1).toInt() - it
            }
        )
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