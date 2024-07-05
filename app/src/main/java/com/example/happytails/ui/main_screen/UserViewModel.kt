package com.example.happytails.ui.main_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.happytails.repository.implementations.UserRepositoryImpl

class UserViewModel(private val userRep: UserRepositoryImpl): ViewModel() {

    val isConected = MutableLiveData<Boolean>()
    fun setValue(value: Boolean) {
        isConected.value = value
    }
}





