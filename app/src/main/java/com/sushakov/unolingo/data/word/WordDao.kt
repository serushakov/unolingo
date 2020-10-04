package com.sushakov.unolingo.data.word

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sushakov.unolingo.data.Language

@Dao
abstract class WordDao {
    @Transaction
    @Query("SELECT * FROM word ORDER BY text")
    abstract fun getAll(): LiveData<List<WordWithTranslations>>

    @Transaction
    @Query("SELECT * FROM word WHERE lang=:language ORDER BY text")
    abstract fun getAll(language: String): LiveData<List<WordWithTranslations>>

    @Query("SELECT * FROM WordCrossRef")
    abstract fun getRelations(): LiveData<List<WordCrossRef>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun createWord(word: Word): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun createRelation(wordCrossRef: WordCrossRef)

    @Transaction
    open suspend fun addTranslationPairs(pairs: List<Pair<Word, Word>>) {
        for (pair in pairs) {
            addTranslation(pair.first, pair.second)
        }
    }

    @Transaction
    open suspend fun addTranslation(parentWord: Word, translationWord: Word) {
        val parentId = createWord(parentWord)
        val translationId = createWord(translationWord)
        createRelation(WordCrossRef(parentId, translationId))
        createRelation(WordCrossRef(parentId = translationId, translationId = parentId))
    }

    @Query("SELECT * FROM word WHERE id=:wordId")
    abstract suspend fun getWordById(wordId: Long): Word

    @Transaction
    @Query("SELECT * FROM word WHERE lang=:language ORDER BY RANDOM() LIMIT 1")
    abstract suspend fun getRandomWordWithTranslations(language: String): WordWithTranslations

    @Query("SELECT * FROM word WHERE id NOT IN (:ignoreList) AND lang=:language ORDER BY RANDOM() LIMIT 1")
    abstract suspend fun getRandomWord(ignoreList: List<Long>, language: String): Word
}