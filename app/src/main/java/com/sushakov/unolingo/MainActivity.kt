package com.sushakov.unolingo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sushakov.unolingo.databinding.ActivityMainBinding
import com.sushakov.unolingo.ui.learn.LearnTab
import com.sushakov.unolingo.ui.words.WordsTab

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavigationItemSelectedListener()
        openInitialTab()
    }

    private fun openInitialTab() {
        // This will trigger onItemSelectedListener, which will initialize the tab
        binding.bottomNavigation.selectedItemId = R.id.tab_learn
    }

    private fun setBottomNavigationItemSelectedListener() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            val fragmentTransaction = supportFragmentManager.beginTransaction();

            val fragment = when (it.itemId) {
                R.id.tab_learn -> LearnTab.newInstance()
                R.id.tab_words -> WordsTab.newInstance()
                else -> null
            }

            if (fragment != null) {
                fragmentTransaction.setCustomAnimations(
                    R.anim.fragment_fade_enter,
                    R.anim.fragment_fade_exit
                )
                fragmentTransaction.replace(R.id.fragmentContainer, fragment)
                fragmentTransaction.commit();
            }

            true
        }
    }
}