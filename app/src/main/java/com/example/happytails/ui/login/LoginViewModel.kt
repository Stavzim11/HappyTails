package com.example.happytails.ui

import androidx.lifecycle.*
import com.example.happytails.data.models.User
import com.example.happytails.repository.UserRepository
import com.example.happytails.utils.Resource
import kotlinx.coroutines.launch


class LoginViewModel(private val authRep:UserRepository) : ViewModel() {

    private val _userSignInStatus = MutableLiveData<Resource<User>>()
    val userSignInStatus: LiveData<Resource<User>> = _userSignInStatus

    private val _currentUser = MutableLiveData<Resource<User>>()
    val currentUser:LiveData<Resource<User>> = _currentUser

    init {
        viewModelScope.launch {
            _currentUser.postValue(Resource.Loading())
            _currentUser.postValue(authRep.currentUser())
        }
    }

    fun signInUser(userEmail:String, userPass:String) {
        if(userEmail.isEmpty() || userPass.isEmpty())
            _userSignInStatus.postValue(Resource.Error("Empty email or password"))
        else{
            _userSignInStatus.postValue(Resource.Loading())
            viewModelScope.launch {
                val loginResult = authRep.login(userEmail,userPass)
                _userSignInStatus.postValue(loginResult)
            }
        }
    }


    class LoginViewModelFactory(private val repo: UserRepository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(repo) as T
        }
    }
}