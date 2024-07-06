package com.example.happytails.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.happytails.repository.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

//main activity needs to know when user is logged in or not
class MainActivityViewModel(private val authRep:UserRepository) : ViewModel()  {
    // the user status
    var _isConected: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value=false }
    var isConected :LiveData<Boolean> =_isConected

    // check with the repository about the user status
    init {
        viewModelScope.launch {
            _isConected = _isConected.apply { authRep.isConnected() }
        }
    }

    fun setUserStatus(status: Boolean) {
        _isConected = _isConected.apply{value=status}
    }

    suspend fun updateUserStatuse(){
        _isConected=_isConected.apply { value= authRep.isConnected() }
    }

    class MainActivityViewModelFactory(private val repo: UserRepository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainActivityViewModel(repo) as T
        }
    }

}