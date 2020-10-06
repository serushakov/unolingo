package com.sushakov.unolingo

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.room.Room
import com.sushakov.unolingo.data.Database
import com.sushakov.unolingo.data.Repository
import com.sushakov.unolingo.ui.MainActivityViewModel
import com.sushakov.unolingo.ui.MainActivityViewModelFactory
import com.sushakov.unolingo.ui.learn.LearnViewModelFactory
import com.sushakov.unolingo.ui.learn.MeTabViewModelFactory
import com.sushakov.unolingo.ui.words.WordsTabViewModelFactory
import com.sushakov.unolingo.ui.words.add_word_dialog.AddWordDialogViewModelFactory

object InjectorUtils {
    private fun getRepository(context: Context): Repository {
        val database = Room.databaseBuilder(
            context,
            Database::class.java, "words"
        ).build()

        return Repository.getInstance(
            database.wordDao(),
            database.recordDao()
        )
    }

    fun provideWordsTabViewModelFactory(
        context: Context,
        lifecycleOwner: LifecycleOwner
    ): WordsTabViewModelFactory {

        return WordsTabViewModelFactory(
            getRepository(
                context
            ), lifecycleOwner)
    }

    fun provideLearnViewModelFactory(
        context: Context,
        lifecycleOwner: LifecycleOwner
    ): LearnViewModelFactory {

        return LearnViewModelFactory(
            getRepository(context),
            lifecycleOwner
        )
    }

    fun provideAddWordDialogViewModel(
        context: Context,
        lifecycleOwner: LifecycleOwner
    ): AddWordDialogViewModelFactory {

        return AddWordDialogViewModelFactory(
            getRepository(context),
            lifecycleOwner
        )
    }

    fun provideMeTabViewModelFactory(
        context: Context,
        lifecycleOwner: LifecycleOwner
    ): MeTabViewModelFactory {
        return MeTabViewModelFactory(
            getRepository(context),
            lifecycleOwner
        )
    }

    fun provideMainActivityViewModel(
        context: Context,
        lifecycleOwner: LifecycleOwner
    ): MainActivityViewModelFactory {
        return MainActivityViewModelFactory(
            getRepository(context),
            lifecycleOwner
        )
    }
}