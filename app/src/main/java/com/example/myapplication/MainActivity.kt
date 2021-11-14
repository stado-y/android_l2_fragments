package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavFragmentClickListener {

    private var itemNum = 1

    private val navdata: navData by viewModels()
    lateinit var binding: ActivityMainBinding

//    lateinit var listFragment: ListFragment
//    lateinit var navFragment: NavFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        if (savedInstanceState == null) {
            createFragment(R.id.listFrame, ListFragment.newInstance())
            createFragment(R.id.navFrame, NavFragment.newInstance())
        }
        else {

            itemNum = savedInstanceState.getInt("itemNum", R.id.nav_1)
            navdata.navItemChosen.value = itemNum
        }


    }


    private fun createFragment (idHolder: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(idHolder, fragment)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("itemNum", itemNum)
        //navdata.navItemChosen.value = itemNum
    }

    override fun onNavItemClick(itemid: Int) {
        //Toast.makeText(this, "${ itemNum }", Toast.LENGTH_SHORT).show()

        itemNum = 1
        when(itemid)
        {
            R.id.nav_1 -> itemNum = 1
            R.id.nav_2 -> itemNum = 2
            R.id.nav_3 -> itemNum = 3
        }
        navdata.navItemChosen.value = itemNum
        //listFragment.updateRCViewWithItemNum(itemNum)

    }
}