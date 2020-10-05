package com.sushakov.unolingo.ui.me

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sushakov.unolingo.R

class MeTab : Fragment() {

    companion object {
        fun newInstance() = MeTab()
    }

    private lateinit var viewModel: MeTabViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.me_tab_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MeTabViewModel::class.java)
        // TODO: Use the ViewModel
    }

}