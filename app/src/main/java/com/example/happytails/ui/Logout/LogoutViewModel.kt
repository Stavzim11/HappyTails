package com.example.happytails.ui.Logout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.happytails.data.models.User
import com.example.happytails.repository.UserRepository
import com.example.happytails.utils.Resource

class LogoutViewModel (private val authRep: UserRepository) : ViewModel() {
    private val _currentUser = MutableLiveData<Resource<User>>()
    val currentUser: LiveData<Resource<User>> = _currentUser

     fun signOut(){
            authRep.logout()
    }

    class LogoutViewModelFactory(private val repo: UserRepository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LogoutViewModel(repo) as T
        }
    }
}