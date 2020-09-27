package com.sushakov.unolingo.data.word

import androidx.room.Entity

@Entity(primaryKeys = ["id", "translationId"])
data class WordCrossRef(val id: Int, val translationId: Int)