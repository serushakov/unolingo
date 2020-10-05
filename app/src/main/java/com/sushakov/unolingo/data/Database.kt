package com.sushakov.unolingo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sushakov.unolingo.data.record.Record
import com.sushakov.unolingo.data.record.RecordDao
import com.sushakov.unolingo.data.word.Word
import com.sushakov.unolingo.data.word.WordCrossRef
import com.sushakov.unolingo.data.word.WordDao
import com.sushakov.unolingo.data.word.WordWithTranslations

@Database(entities = [Word::class, WordCrossRef::class, Record::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun recordDao(): RecordDao
}