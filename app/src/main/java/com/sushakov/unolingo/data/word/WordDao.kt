package com.sushakov.unolingo.data.word

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface WordDao {
    @Transaction
    @Query("SELECT * FROM word")
    fun getAll(): LiveData<List<WordWithTranslations>>

    @Insert
    suspend fun create(word: Word)

}