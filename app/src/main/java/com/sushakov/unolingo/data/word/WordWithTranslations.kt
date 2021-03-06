package com.sushakov.unolingo.data.word

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import java.io.Serializable

data class WordWithTranslations(
    @Embedded val word: Word,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            WordCrossRef::class,
            parentColumn = "parentId",
            entityColumn = "translationId"
        )
    )
    val translations: Set<Word>
) : Serializable