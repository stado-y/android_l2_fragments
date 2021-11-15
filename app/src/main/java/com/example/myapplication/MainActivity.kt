package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val navdata: navData by viewModels()

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {

            createFragment(R.id.listFrame, ListFragment.newInstance())
            createFragment(R.id.navFrame, NavFragment.newInstance())

            navdata.setChosenNavItem(R.id.nav_1)
        }

    }


    private fun createFragment (idHolder: Int, fragment: Fragment) {

        supportFragmentManager
            .beginTransaction()
            .replace(idHolder, fragment)
            .commit()
    }

}