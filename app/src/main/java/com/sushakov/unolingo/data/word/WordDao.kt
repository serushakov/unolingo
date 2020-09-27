package com.sushakov.unolingo.data.word

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordDao {
    @Query("SELECT * FROM word")
    fun getAll(): LiveData<List<Word>>

    @Insert
    suspend fun create(word: Word)

}