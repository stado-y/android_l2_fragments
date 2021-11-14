package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.myapplication.databinding.FragmentNavBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

interface NavFragmentClickListener {
    fun onNavItemClick(itemid: Int)
}


class NavFragment: Fragment(), NavigationView.OnNavigationItemSelectedListener {

    private val navdata: navData by activityViewModels()

    lateinit var binding: FragmentNavBinding

    private var savedItem = R.id.nav_1

    private var stateRestored: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNavBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("navFragment", "ONVIEW CREATED")


        Log.d("navFragment", "onstart item : ${ savedItem }")


            navdata.navItemChosen.observe(activity as LifecycleOwner, {
                Log.d("navFragment", "saved item get")
                when (it) {

                    2 -> savedItem = R.id.nav_2
                    3 -> savedItem = R.id.nav_3
                }
                Log.d("navFragment", "after get item : ${savedItem}")
                if (!stateRestored) {
                    binding.botNavView?.setSelectedItemId(savedItem)
                    binding.navView?.setCheckedItem(savedItem)
                    stateRestored = true;
                }
            })


        //тут слушать нажотия
        binding.botNavView?.setOnNavigationItemSelectedListener {

            onNavigationItemSelected(it)
        }
        binding.navView?.setNavigationItemSelectedListener(this)

    }

    companion object {
        @JvmStatic
        fun newInstance() = NavFragment()
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        (activity as? NavFragmentClickListener)?.onNavItemClick(item.itemId)
        return true
    }

}