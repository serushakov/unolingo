package com.sushakov.unolingo.data.record

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Record (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo
    val wordId: Long,
    @ColumnInfo
    val result: Boolean
)