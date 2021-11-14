package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class navData: ViewModel() {

    val navItemChosen: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

}