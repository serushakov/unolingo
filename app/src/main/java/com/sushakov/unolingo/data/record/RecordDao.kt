package com.sushakov.unolingo.data.record

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
abstract class RecordDao {

    @Insert
    abstract suspend fun addRecord(record: Record)

    @Transaction
    @Query("SELECT * FROM record WHERE id=:id")
    abstract suspend fun getRecordWithWord(id: Long): RecordWithWord

    @Query("SELECT * FROM record ORDER BY id DESC LIMIT :amount")
    abstract suspend fun getLastResults(amount: Int = 100): List<Record>

    @Query("SELECT COUNT(*) FROM record WHERE result")
    abstract suspend fun getCorrectAnswerCount(): Int

    @Query("SELECT wordId,COUNT(result) as count  FROM record WHERE result=0 GROUP BY wordId ORDER BY count DESC LIMIT :amount")
    abstract suspend fun getWorstWords(amount: Int = 5): List<WordCount>

}