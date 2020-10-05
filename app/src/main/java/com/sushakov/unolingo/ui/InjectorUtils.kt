package com.sushakov.unolingo.ui

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.room.Room
import com.sushakov.unolingo.data.Database
import com.sushakov.unolingo.data.Repository
import com.sushakov.unolingo.ui.learn.LearnViewModelFactory
import com.sushakov.unolingo.ui.words.WordsTab
import com.sushakov.unolingo.ui.words.WordsTabViewModelFactory
import com.sushakov.unolingo.ui.words.add_word_dialog.AddWordDialogViewModelFactory

object InjectorUtils {
    fun provideWordsTabViewModelFactory(
        context: Context,
        lifecycleOwner: LifecycleOwner
    ): WordsTabViewModelFactory {
        val database = Room.databaseBuilder(
            context,
            Database::class.java, "words"
        ).build()

        val repository = Repository.getInstance(
            database.wordDao(),
            database.recordDao()
        )
        return WordsTabViewModelFactory(repository, lifecycleOwner)
    }

    fun provideLearnViewModelFactory(
        context: Context,
        lifecycleOwner: LifecycleOwner
    ): LearnViewModelFactory {
        val database = Room.databaseBuilder(
            context,
            Database::class.java, "words"
        ).build()
        val repository = Repository.getInstance(
            database.wordDao(),
            database.recordDao()
        )
        return LearnViewModelFactory(
            repository,
            lifecycleOwner
        )
    }

    fun provideAddWordDialogViewModel(
        context: Context,
        lifecycleOwner: LifecycleOwner
    ): AddWordDialogViewModelFactory {
        val database = Room.databaseBuilder(
            context,
            Database::class.java, "words"
        ).build()

        val repository = Repository.getInstance(
            database.wordDao(),
            database.recordDao()
        )
        return AddWordDialogViewModelFactory(
            repository,
            lifecycleOwner
        )
    }
}