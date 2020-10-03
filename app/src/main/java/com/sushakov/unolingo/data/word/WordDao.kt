package com.sushakov.unolingo.data.word

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
abstract class WordDao {
    @Transaction
    @Query("SELECT * FROM word")
    abstract fun getAll(): LiveData<List<WordWithTranslations>>

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
        val translationId = createWord(parentWord)
        createRelation(WordCrossRef(parentId, translationId))
        createRelation(WordCrossRef(parentId = translationId, translationId = parentId))
    }

    @Query("SELECT * FROM word WHERE id=:wordId")
    abstract suspend fun getWordById(wordId: Long): Word

    @Query("SELECT * FROM word ORDER BY RANDOM() LIMIT 1")
    abstract suspend fun getRandomWordWithTranslations(): WordWithTranslations

    @Query("SELECT * FROM word WHERE id NOT IN (:ignoreList) ORDER BY RANDOM() LIMIT 1")
    abstract suspend fun getRandomWord(ignoreList: List<Long>): Word
}