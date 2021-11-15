package com.example.myapplication

import android.content.Context
import android.content.res.TypedArray
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentListBinding
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner


class ListFragment: Fragment() {

    private val navdata: navData by activityViewModels()

    private var chosenItem: Int? = null

    private lateinit var binding: FragmentListBinding

    private var RCadapter: RCAdapter? = null

    private var namelist = ArrayList<ListItem>()

    private lateinit var savedLayout: Parcelable

    private var restoredLayout: Parcelable? = null

    //lateinit var itemsList: ArrayList<ListItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

            binding = FragmentListBinding.inflate(inflater)

            return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("listFragment", "onviewcreated")

            binding.rcView.hasFixedSize()
            binding.rcView.layoutManager = LinearLayoutManager(activity)
        if (savedInstanceState == null) {
            RCadapter = RCAdapter(namelist, binding.rcView.context)
            binding.rcView.adapter = RCadapter

            Log.d("listFragment", "rcview added")
        }

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.d("listFragment", "ONATTACH")

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        Log.d("listFragment", "onViewStateRestored")

        if (savedInstanceState != null) {
            restoredLayout = savedInstanceState?.getParcelable("RcLayoutState")

            if (this.restoredLayout != null) {

                var restoredNavItem = navdata.getChosenNavItem() ?: R.id.nav_1

                namelist = getListFromId(restoredNavItem)

                RCadapter = RCAdapter(namelist, binding.rcView.context)
                binding.rcView.adapter = RCadapter

                binding.rcView.layoutManager?.onRestoreInstanceState(restoredLayout)
            }

        }

    }

    override fun onStart() {
        super.onStart()

        navdata.navItemChosen.observe((activity as LifecycleOwner), {
            val pop = it
            Log.d("listFragment", "livedata value : ${pop}")

            updateRCViewWithItemNum(pop,true)
        })
        Log.d("listFragment", "after livedata")
    }

    override fun onPause() {
        super.onPause()

        savedLayout = binding.rcView.layoutManager?.onSaveInstanceState()!!

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable("RcLayoutState", savedLayout)

    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }

    private fun fillArrays(
        titleArray: Array<String>,
        textArray: Array<String>,
        imageArray: IntArray
    ): ArrayList<ListItem> {

        val listItemArray = ArrayList<ListItem>()

        for (n in 0 until titleArray.size) {

            val listItem = ListItem(imageArray[n], titleArray[n], textArray[n])
            listItemArray.add(listItem)
        }
        return listItemArray
    }

    private fun getImageId(imageArrayId: Int): IntArray {

        val tArray: TypedArray = resources.obtainTypedArray(imageArrayId)
        val count = tArray.length()
        val imgIDS = IntArray(count)

        for(i in imgIDS.indices) {

            imgIDS[i] = tArray.getResourceId(i, 0)
        }
        tArray.recycle()
        return imgIDS
    }

    private fun updateRCViewWithItemNum(itemId: Int, resetPosition: Boolean) {

        val newList = getListFromId(itemId)

        if (newList != namelist) {

            RCadapter?.updateAdapter(newList)

            if (resetPosition) {
                binding.rcView.smoothScrollToPosition(0)
                Log.d("listFragment", "RESET RC POSITION!!!!")
            }

        }

    }

    private fun getListFromId (itemId: Int): ArrayList<ListItem> {
        val list: ArrayList<ListItem>
        if (chosenItem != itemId) {

            chosenItem = itemId

            var titleArray = resources.getStringArray(R.array.title)
            var textArray = resources.getStringArray(R.array.text)
            var imageArray = getImageId(R.array.image)

            when (itemId) {

                R.id.nav_2 -> {
                    titleArray = resources.getStringArray(R.array.title_2)
                    textArray = resources.getStringArray(R.array.text_2)
                    imageArray = getImageId(R.array.image_2)
                }
                R.id.nav_3 -> {
                    titleArray = resources.getStringArray(R.array.title_3)
                    textArray = resources.getStringArray(R.array.text_3)
                    imageArray = getImageId(R.array.image_3)
                }

            }
            list = fillArrays(titleArray, textArray, imageArray)

        }
        else {

            list = namelist
            Log.d("listFragment", "chosenItem : ${ chosenItem } == itemId : ${ itemId }")

        }
        return list

    }

}