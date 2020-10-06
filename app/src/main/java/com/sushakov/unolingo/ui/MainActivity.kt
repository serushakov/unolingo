package com.sushakov.unolingo.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sushakov.unolingo.InjectorUtils
import com.sushakov.unolingo.R
import com.sushakov.unolingo.databinding.ActivityMainBinding
import com.sushakov.unolingo.ui.learn.LearnTab
import com.sushakov.unolingo.ui.me.MeTab
import com.sushakov.unolingo.ui.onboarding.OnboardingFragment
import com.sushakov.unolingo.ui.words.WordsTab

class MainActivity : AppCompatActivity(), OnboardingFragment.Callback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        setBottomNavigationItemSelectedListener()
        showOnboardingIfNeeded()
        loadWords()
    }

    private fun openInitialTab() {
        // This will trigger onItemSelectedListener, which will initialize the tab
        binding.bottomNavigation.selectedItemId =
            R.id.tab_learn

    }

    private fun initViewModel() {
        val factory =
            InjectorUtils.provideMainActivityViewModel(
                this,
                this
            )

        viewModel = ViewModelProvider(viewModelStore, factory)
            .get(MainActivityViewModel::class.java)
    }

    private fun showOnboardingIfNeeded() {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return

        val name = sharedPref.getString(getString(R.string.preference_name), null)

        Log.d("name", name ?: "noname")

        // Only show onboarding when name is not present
        if (name != null) {
            openInitialTab()
            return
        }

        val fragmentTransaction = supportFragmentManager.beginTransaction();

        val fragment = OnboardingFragment.newInstance()

        fragment.registerCallback(this)

        fragmentTransaction.replace(R.id.onboardingContainer, fragment)

        fragmentTransaction.commit()

    }

    private fun loadWords() {
        val context = this

        lifecycleScope.launchWhenCreated {
            val exception = viewModel.loadWords()

            if (exception != null) {
                runOnUiThread {
                    Toast.makeText(
                        context,
                        "Failed to fetch words! Please ensure network connectivity! The app will not work properly",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun setBottomNavigationItemSelectedListener() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            val fragmentTransaction = supportFragmentManager.beginTransaction();

            val fragment = when (it.itemId) {
                R.id.tab_learn -> LearnTab.newInstance()
                R.id.tab_words -> WordsTab.newInstance()
                R.id.tab_me -> MeTab.newInstance()
                else -> null
            }

            if (fragment != null) {
                fragmentTransaction.replace(R.id.fragmentContainer, fragment)
                fragmentTransaction.commit();
            }

            true
        }
    }

    private fun hideOnboarding() {
        binding.onboardingContainer
            .animate()
            .translationY(-2000f)
            .withLayer()
            .withEndAction {
                (binding.onboardingContainer.parent as ViewGroup).removeView(binding.onboardingContainer)
            }
    }

    override fun onNameEnter(name: String) {

        openInitialTab()

        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return

        with(sharedPref.edit()) {
            putString(getString(R.string.preference_name), name)
            apply()
        }
    }

    override fun onContinueClick() {
        hideOnboarding()
    }
}