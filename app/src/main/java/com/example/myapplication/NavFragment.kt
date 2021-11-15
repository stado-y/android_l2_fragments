package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.myapplication.databinding.FragmentNavBinding
import com.google.android.material.navigation.NavigationView

class NavFragment: Fragment(), NavigationView.OnNavigationItemSelectedListener {

    private val navdata: navData by activityViewModels()

    private lateinit var binding: FragmentNavBinding

    private var savedItem = R.id.nav_1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNavBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("navFragment", "ONVIEW CREATED")

        //тут слушать нажотия
        binding.botNavView?.setOnNavigationItemSelectedListener {

            onNavigationItemSelected(it)
        }

        binding.navView?.setNavigationItemSelectedListener(this)

    }

    override fun onStart() {
        super.onStart()
        Log.d("navFragment", "onstart item : ${ savedItem }")


        savedItem = navdata.getChosenNavItem() ?: R.id.nav_1
        Log.d("navFragment", "after get item : ${ savedItem }")

        val choosenItem = binding.botNavView?.selectedItemId ?: binding.navView?.checkedItem

        if (choosenItem != savedItem) {

            binding.botNavView?.setSelectedItemId(savedItem)
            binding.navView?.setCheckedItem(savedItem)
            Log.d("navFragment", "Changed selection from : ${ choosenItem } to ${ savedItem }")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NavFragment()
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        navdata.setChosenNavItem(item.itemId)
        return true
    }

}