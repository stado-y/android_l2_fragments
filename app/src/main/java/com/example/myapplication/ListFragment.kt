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
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.myapplication.databinding.FragmentNavBinding


class ListFragment: Fragment() {

    private val navdata: navData by activityViewModels()

    lateinit var binding: FragmentListBinding

    var RCadapter: RCAdapter? = null

    var namelist = ArrayList<ListItem>()

    //lateinit var itemsList: ArrayList<ListItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.d("listFragment", "onviewcreated")
        namelist.addAll(
            fillArrays(
                resources.getStringArray(R.array.title),
                resources.getStringArray(R.array.text),
                getImageId(R.array.image)
            )
        )
        Log.d("listFragment", "namelist filled")

        binding.rcView.hasFixedSize()
        binding.rcView.layoutManager = LinearLayoutManager(activity)
        RCadapter = RCAdapter(namelist, binding.rcView.context)
        binding.rcView.adapter = RCadapter

        Log.d("listFragment", "rcview added")

        if (isAdded && activity != null) {
            Log.d("listFragment", "livedata passed check")
            navdata.navItemChosen.observe((activity as LifecycleOwner), {
                var lol = it
                Log.d("listFragment", "livedata value : ${ lol }")
                updateRCViewWithItemNum(lol)
            })
        }
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






    fun fillArrays(titleArray: Array<String>, textArray: Array<String>, imageArray: IntArray): ArrayList<ListItem> {

        var listItemArray = ArrayList<ListItem>()

        for (n in 0..titleArray.size-1) {

            var listItem = ListItem(imageArray[n], titleArray[n], textArray[n])
            listItemArray.add(listItem)
        }
        return listItemArray
    }

    fun getImageId(imageArrayId: Int): IntArray {

        var tArray: TypedArray = resources.obtainTypedArray(imageArrayId)
        var count = tArray.length()
        val imgIDS = IntArray(count)

        for(i in imgIDS.indices) {

            imgIDS[i] = tArray.getResourceId(i, 0)
        }
        tArray.recycle()
        return imgIDS
    }

    fun updateRCViewWithItemNum(irtemNum: Int) {
        var titleArray = resources.getStringArray(R.array.title)
        var textArray = resources.getStringArray(R.array.text)
        var imageArray = getImageId(R.array.image)

        when(irtemNum) {

            2 -> {
                titleArray = resources.getStringArray(R.array.title_2)
                textArray = resources.getStringArray(R.array.text_2)
                imageArray = getImageId(R.array.image_2)
            }
            3 -> {
                titleArray = resources.getStringArray(R.array.title_3)
                textArray = resources.getStringArray(R.array.text_3)
                imageArray = getImageId(R.array.image_3)
            }

        }
        var list = fillArrays(titleArray, textArray, imageArray)
        RCadapter?.updateAdapter(list)
        binding.rcView.smoothScrollToPosition(0)
    }
}