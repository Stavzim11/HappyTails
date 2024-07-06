package com.example.happytails.ui

import android.util.Patterns
import androidx.lifecycle.*
import com.example.happytails.data.models.User
import com.example.happytails.repository.UserRepository
import com.example.happytails.ui.favorite.FavoriteFragmentViewModel
import com.example.happytails.utils.Resource
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {

    private val _userRegistrationStatus = MutableLiveData<Resource<User>>()
    val userRegistrationStatus: LiveData<Resource<User>> = _userRegistrationStatus

    fun createUser(userName: String, userEmail: String, userPhone: String, userPass: String) {
        val error =
            if (userEmail.isEmpty() || userName.isEmpty() || userPass.isEmpty() || userPhone.isEmpty())
                "Empty Strings"
            else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                "Not a valid email"
            } else if(userPass.length<=6) {
            "Not valid password"
            } else null
        error?.let {
            _userRegistrationStatus.postValue(Resource.Error(it))
        }
        _userRegistrationStatus.value = Resource.Loading()
        viewModelScope.launch {
            val registrationResult = repository.createUser(userName, userEmail, userPhone, userPass)
            _userRegistrationStatus.postValue(registrationResult)
}

}

class RegisterViewModelFactory(private val repo: UserRepository) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RegisterViewModel(repo) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}