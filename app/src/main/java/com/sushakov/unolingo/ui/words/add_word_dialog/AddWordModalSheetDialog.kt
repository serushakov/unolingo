package com.sushakov.unolingo.ui.words.add_word_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sushakov.unolingo.R
import com.sushakov.unolingo.databinding.AddWordModalBottomSheetBinding
import com.sushakov.unolingo.ui.InjectorUtils
import com.sushakov.unolingo.ui.words.WordsTabViewModel

class AddWordModalSheetDialog : BottomSheetDialogFragment() {
    lateinit var binding: AddWordModalBottomSheetBinding
    lateinit var viewModel: AddWordDialogViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.add_word_modal_bottom_sheet,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner


        val factory =
            InjectorUtils.provideAddWordDialogViewModel(requireContext(), viewLifecycleOwner)
        viewModel = ViewModelProvider(this, factory)
            .get(AddWordDialogViewModel::class.java)

        binding.viewModel = viewModel

        return binding.root
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}