package com.sushakov.unolingo.data.record

import androidx.room.*

@Dao
abstract class RecordDao {

    @Insert
    abstract suspend fun addRecord(record: Record)

    @Transaction
    @Query("SELECT * FROM record WHERE id=:id")
    abstract suspend fun getRecordWithWord(id: Long): RecordWithWord

}