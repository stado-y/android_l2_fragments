package com.example.myapplication

import android.content.Context
import android.content.res.TypedArray
import android.os.Bundle
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

    private val chosenItem: Int? = null

    private lateinit var binding: FragmentListBinding

    private var RCadapter: RCAdapter? = null

    private var namelist = ArrayList<ListItem>()

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
        RCadapter = RCAdapter(namelist, binding.rcView.context)
        binding.rcView.adapter = RCadapter

        Log.d("listFragment", "rcview added")


        navdata.navItemChosen.observe((activity as LifecycleOwner), {
            val pop = it
            Log.d("listFragment", "livedata value : ${ pop }")
            updateRCViewWithItemNum(pop)
        })

        Log.d("listFragment", "after livedata")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("listFragment", "ONATTACH")
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

    private fun updateRCViewWithItemNum(itemId: Int) {

        if (chosenItem != itemId) {

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
            val list = fillArrays(titleArray, textArray, imageArray)
            RCadapter?.updateAdapter(list)
            binding.rcView.smoothScrollToPosition(0)
        }
        else {

            Log.d("listFragment", "chosenItem : ${ chosenItem } != itemId : ${ itemId }")
        }
    }
}