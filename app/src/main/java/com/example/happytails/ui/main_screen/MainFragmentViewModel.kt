package com.example.happytails.ui.main_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.happytails.data.models.Dog
import com.example.happytails.repository.implementations.DogsRepositoryImpl
import com.example.happytails.repository.implementations.UserRepositoryImpl
import com.example.happytails.utils.Resource
import kotlinx.coroutines.launch

class MainFragmentViewModel(
    application: Application,
    private val userRep: UserRepositoryImpl,
    private val dogRep: DogsRepositoryImpl
) : AndroidViewModel(application) {

    // Firebase
    private val _dogs = MutableLiveData<Resource<List<Dog>>>()
    val dogs: LiveData<Resource<List<Dog>>> get() = _dogs

    private val _addDogStatus = MutableLiveData<Resource<Void>>()
    val addDogStatus:LiveData<Resource<Void>> = _addDogStatus

    private val _removeDogStatus = MutableLiveData<Resource<Void>>()
    val removeDogStatus:LiveData<Resource<Void>> = _removeDogStatus



    init {
        dogRep.getDogsLiveData(_dogs)
    }

//    fun setDog(dog: Dog) {
//        _chosenDog.value = dog
//    }
//
//    fun insertDog(dog: Dog) {
//        viewModelScope.launch {
//            dogRep.addDog(dog)
//        }
//    }
//
//    fun deleteDog(dog: Dog) {
//        viewModelScope.launch {
//            dogRep.deleteDog(dog)
//        }
//    }
//
//    fun updateDog(dog: Dog) {
//        viewModelScope.launch {
//            dogRep.updateDog(dog)
//        }
//    }


    fun addDogToFavorites(dog: Dog) {
        viewModelScope.launch {
            _addDogStatus.postValue(dogRep.addDogToFavorites(dog))
        }
    }

    fun removeDogFromFavorites(dog: Dog) {
        viewModelScope.launch {
            _removeDogStatus.postValue(dogRep.deleteFavoriteDog(dog))
        }
    }

    class AllDogViewModelFactory(
        private val application: Application,
        private val userRepo: UserRepositoryImpl,
        private val dogRepo: DogsRepositoryImpl
    ) : ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainFragmentViewModel(application, userRepo, dogRepo) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
