package com.sushakov.unolingo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sushakov.unolingo.InjectorUtils
import com.sushakov.unolingo.R
import com.sushakov.unolingo.databinding.ActivityMainBinding
import com.sushakov.unolingo.ui.learn.LearnTab
import com.sushakov.unolingo.ui.me.MeTab
import com.sushakov.unolingo.ui.me.MeTabViewModel
import com.sushakov.unolingo.ui.onboarding.OnboardingFragment
import com.sushakov.unolingo.ui.words.WordsTab

class MainActivity : AppCompatActivity(), OnboardingFragment.Callback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    private var onboardingFragment: OnboardingFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)


        val factory =
            InjectorUtils.provideMainActivityViewModel(
                this,
                this
            )

        viewModel = ViewModelProvider(viewModelStore, factory)
            .get(MainActivityViewModel::class.java)

        setContentView(binding.root)

        setBottomNavigationItemSelectedListener()
        showOnboarding()
    }

    private fun openInitialTab() {
        // This will trigger onItemSelectedListener, which will initialize the tab
        binding.bottomNavigation.selectedItemId =
            R.id.tab_words

    }

    private fun showOnboarding() {
        val fragmentTransaction = supportFragmentManager.beginTransaction();

        val fragment = OnboardingFragment.newInstance()

        fragment.registerCallback(this)



        fragmentTransaction.setCustomAnimations(
            R.anim.fragment_fade_enter,
            R.anim.slide_out_top
        )

        onboardingFragment = fragment

        fragmentTransaction.replace(R.id.onboardingContainer, fragment)
        fragmentTransaction.commit()
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
        binding.onboardingContainer.animate().translationY(-2000f).withLayer().withEndAction {
            (binding.onboardingContainer.parent as ViewGroup).removeView(binding.onboardingContainer)
        }
    }

    override fun onContinueClick(name: String) {
        openInitialTab()
        hideOnboarding()
    }
}