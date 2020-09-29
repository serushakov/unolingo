package com.sushakov.unolingo.data.word

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["parentId", "translationId"])
data class WordCrossRef(
    val parentId: Long, val translationId: Long
)