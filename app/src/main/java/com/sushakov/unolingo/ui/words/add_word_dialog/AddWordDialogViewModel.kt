package com.sushakov.unolingo.ui.words.add_word_dialog

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.sushakov.unolingo.data.Language
import com.sushakov.unolingo.data.Repository
import com.sushakov.unolingo.data.word.Word

class AddWordDialogViewModel(val repository: Repository, val lifecycleOwner: LifecycleOwner) :
    ViewModel() {

    val createdWordId: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }

    val wordText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val translationText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val submitButtonEnabled: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    init {
        setTextListeners()
    }

    private fun setTextListeners() {
        wordText.observe(lifecycleOwner, Observer {
            setSubmitButtonEnabledValue()
        })
        translationText.observe(lifecycleOwner, Observer {
            setSubmitButtonEnabledValue()
        })
    }

    fun setSubmitButtonEnabledValue() {
        submitButtonEnabled.value =
            wordText.value?.isNotEmpty() ?: false && translationText.value?.isNotEmpty() ?: false
    }

    suspend fun addWord() {
        val sourceWordText = wordText.value
        val translationWordText = translationText.value

        if (sourceWordText == null || translationWordText == null) return

        val sourceWord = Word(
            lang = Language.ENGLISH,
            text = sourceWordText
        )
        val translationWord = Word(
            lang = Language.SPANISH,
            text = translationWordText
        )

        val wordId = repository.addTranslation(sourceWord, translationWord)

        createdWordId.value = wordId
    }

    val handleWordAddClick = View.OnClickListener {
        lifecycleOwner.lifecycleScope.launchWhenCreated {
            addWord()
        }
    }

}