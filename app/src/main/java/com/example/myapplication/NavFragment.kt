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

interface NavFragmentClickListener {
    fun onNavItemClick(itemid: Int)
}


class NavFragment: Fragment() {

    private val navdata: navData by activityViewModels()

    lateinit var binding: FragmentNavBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNavBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        navdata.navItemChosen.observe(activity as LifecycleOwner, {
//
//            binding.botNavView.selectedItemId = it
//        })


        //тут слушать нажотия
        binding.botNavView.setOnNavigationItemSelectedListener {


            (activity as? NavFragmentClickListener)?.onNavItemClick(it.itemId)
            true
        }
    }



    companion object {
        @JvmStatic
        fun newInstance() = NavFragment()
    }
}