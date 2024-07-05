package com.example.happytails.ui.main_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.happytails.repository.implementations.UserRepositoryImpl

class UserViewModel(private val userRep: UserRepositoryImpl): ViewModel() {

    val isConected = MutableLiveData<Boolean>()
    fun setValue(value: Boolean) {
        isConected.value = value
    }

    class AllDogViewModelFactory(
        private val userRepo: UserRepositoryImpl,
    ) : ViewModelProvider.AndroidViewModelFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserViewModel(userRepo) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}




