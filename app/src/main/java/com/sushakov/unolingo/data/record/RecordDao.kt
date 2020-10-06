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
    abstract fun getLastResults(amount: Int = 100): LiveData<List<Record>>

    @Query("SELECT COUNT(*) FROM record WHERE result")
    abstract fun getCorrectAnswerCount(): LiveData<Int>

}