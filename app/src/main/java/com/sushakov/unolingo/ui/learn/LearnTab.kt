package com.sushakov.unolingo.ui.learn

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sushakov.unolingo.R
import com.sushakov.unolingo.data.Language
import com.sushakov.unolingo.databinding.FragmentLearnTabBinding
import com.sushakov.unolingo.ui.InjectorUtils
import com.sushakov.unolingo.ui.learn.wordcard.WordCard

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LearnTab.newInstance] factory method to
 * create an instance of this fragment.
 */
class LearnTab : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLearnTabBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_learn_tab, container, false)

        val factory =
            InjectorUtils.provideLearnViewModelFactory(requireContext(), viewLifecycleOwner)
        val viewModel = ViewModelProvider(this, factory)
            .get(LearnViewModel::class.java)

        binding.fetchbutton.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                viewModel.init()
            }
        }

        binding.createButton.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                val wordWithTranslations = viewModel.getWord()

                val fragment = WordCard.newInstance(
                    wordWithTranslations,
                    viewModel.getWordOptions(wordWithTranslations.word),
                    Language.SPANISH
                )

                Log.i("debug", wordWithTranslations.word.text)

                val fragmentTransaction = childFragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.cardContainer, fragment)
                fragmentTransaction.commit();
            }
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LearnTab.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LearnTab().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}