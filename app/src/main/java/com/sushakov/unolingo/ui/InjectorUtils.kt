package com.sushakov.unolingo.ui

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.room.Room
import com.sushakov.unolingo.data.Database
import com.sushakov.unolingo.data.Repository

object InjectorUtils {
    fun provideWordsViewModelFactory(context: Context, lifecycleOwner: LifecycleOwner): WordsViewModelFactory {

        val quoteRepository = Repository.getInstance(
            Room.databaseBuilder(
                context,
                Database::class.java, "words"
            ).build().wordDao()
        )
        return WordsViewModelFactory(quoteRepository, lifecycleOwner)
    }
}