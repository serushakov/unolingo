package com.sushakov.unolingo.ui.words

import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sushakov.unolingo.R
import com.sushakov.unolingo.databinding.FragmentWordsTabBinding
import com.sushakov.unolingo.InjectorUtils
import com.sushakov.unolingo.ui.words.add_word_dialog.AddWordModalSheetDialog
import kotlin.math.roundToInt


class WordsTab : Fragment() {
    companion object {
        fun newInstance() = WordsTab()
    }

    private lateinit var viewModel: WordsTabViewModel
    private lateinit var binding: FragmentWordsTabBinding
    private lateinit var wordsAdapter: WordsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_words_tab, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        val factory =
            InjectorUtils.provideWordsTabViewModelFactory(requireContext(), viewLifecycleOwner)
        viewModel = ViewModelProvider(this, factory)
            .get(WordsTabViewModel::class.java)

        wordsAdapter = WordsAdapter()

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        binding.collapsingToolbarLayout.setExpandedTitleColor(Color.BLACK);

        binding.recyclerView.adapter = wordsAdapter

        viewModel.words.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("words changed", "${it.size}")
                wordsAdapter.words = it
            }
        })

        binding.addWordFab.setOnClickListener {
            openAddWordBottomSheetDialog()
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)


        return binding.root
    }

    private val itemTouchHelperCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                val background = ColorDrawable(requireContext().getColor(R.color.errorColor))
                val icon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_round_delete_24)

                val itemView = viewHolder.itemView

                background.setBounds(
                    itemView.right + dX.toInt(),
                    itemView.top,
                    itemView.width,
                    itemView.bottom
                );

                background.draw(c)

                if (icon != null) {
                    val iconSize = icon.intrinsicHeight
                    val halfIcon: Int = iconSize / 2
                    val iconHorizontalMargin = convertDpToPx(16)
                    val top =
                        itemView.top + ((itemView.bottom - itemView.top) / 2 - halfIcon)
                    val imgLeft = viewHolder.itemView.right - iconHorizontalMargin - halfIcon * 2
                    icon.setBounds(
                        imgLeft,
                        top,
                        itemView.right - iconHorizontalMargin,
                        top + iconSize
                    );
                    icon.setTint(ContextCompat.getColor(requireContext(), R.color.primaryTextColor))

                    icon.draw(c)
                }

            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteWord(wordsAdapter.getItemAt(viewHolder.adapterPosition).word.id)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.item_removed_toast),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private fun convertDpToPx(dp: Int) =
        (dp * (resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()

    private fun openAddWordBottomSheetDialog() {
        val dialog =
            AddWordModalSheetDialog()

        dialog.callback = object : AddWordModalSheetDialog.AddWordModalCallback {
            override fun onWordAdded(wordId: Long) {
                dialog.dismiss()

                Toast.makeText(requireContext(), R.string.item_added_toast, Toast.LENGTH_SHORT)
            }

            override fun onCancelClick() {
                dialog.dismiss()
            }
        }


        dialog.show(childFragmentManager, AddWordModalSheetDialog.TAG)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WordsTabViewModel::class.java)
        // TODO: Use the ViewModel
    }

}