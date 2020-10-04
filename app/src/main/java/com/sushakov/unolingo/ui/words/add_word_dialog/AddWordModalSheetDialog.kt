package com.sushakov.unolingo.ui.words.add_word_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sushakov.unolingo.R
import com.sushakov.unolingo.databinding.AddWordModalBottomSheetBinding
import com.sushakov.unolingo.ui.InjectorUtils

class AddWordModalSheetDialog : BottomSheetDialogFragment() {
    private lateinit var binding: AddWordModalBottomSheetBinding
    private lateinit var viewModel: AddWordDialogViewModel

    lateinit var callback: AddWordModalCallback

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

        binding.cancelButton.setOnClickListener {
            callback.onCancelClick()
        }

        viewModel.createdWordId.observe(viewLifecycleOwner, Observer {
            callback.onWordAdded(it)
        })

        return binding.root
    }

    interface AddWordModalCallback {
        fun onWordAdded(wordId: Long)
        fun onCancelClick()
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}