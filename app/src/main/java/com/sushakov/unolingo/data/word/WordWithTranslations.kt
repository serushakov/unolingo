package com.sushakov.unolingo.data.word

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class WordWithTranslations(
    @Embedded val word: Word,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(WordCrossRef::class)
    )
    val translations: Set<Word>
)