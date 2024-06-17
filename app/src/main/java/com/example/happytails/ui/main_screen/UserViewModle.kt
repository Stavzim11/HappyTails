package com.example.happytails.ui.main_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

    class UserViewModel : ViewModel() {

//        val isConected : MutableLiveData<Boolean> by lazy {
//            MutableLiveData<Boolean>(false)
//            }
        val isConected = MutableLiveData<Boolean>()
            fun setValue(value: Boolean) {
                isConected.value = value
            }
    }





