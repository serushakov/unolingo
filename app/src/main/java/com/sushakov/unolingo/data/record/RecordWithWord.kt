package com.sushakov.unolingo.data.record

import androidx.room.Embedded
import androidx.room.Relation
import com.sushakov.unolingo.data.word.Word

data class RecordWithWord(
    @Embedded val record: Record,
    @Relation(
        parentColumn = "wordId",
        entityColumn = "id"
    )
    val word: Word,
    val count: Int?
)
