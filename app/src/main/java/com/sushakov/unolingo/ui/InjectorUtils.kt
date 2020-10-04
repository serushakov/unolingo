package com.sushakov.unolingo.ui

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.room.Room
import com.sushakov.unolingo.data.Database
import com.sushakov.unolingo.data.Repository
import com.sushakov.unolingo.ui.learn.LearnViewModelFactory
import com.sushakov.unolingo.ui.words.WordsTab
import com.sushakov.unolingo.ui.words.WordsTabViewModelFactory

object InjectorUtils {
    fun provideWordsTabViewModelFactory(
        context: Context,
        lifecycleOwner: LifecycleOwner
    ): WordsTabViewModelFactory {

        val quoteRepository = Repository.getInstance(
            Room.databaseBuilder(
                context,
                Database::class.java, "words"
            ).build().wordDao()
        )
        return WordsTabViewModelFactory(quoteRepository, lifecycleOwner)
    }

    fun provideLearnViewModelFactory(
        context: Context,
        lifecycleOwner: LifecycleOwner
    ): LearnViewModelFactory {

        val quoteRepository = Repository.getInstance(
            Room.databaseBuilder(
                context,
                Database::class.java, "words"
            ).build().wordDao()
        )
        return LearnViewModelFactory(
            quoteRepository,
            lifecycleOwner
        )
    }
}