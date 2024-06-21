package com.example.happytails.ui.main_screen

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
    private val userRep: UserRepositoryImpl,
    private val dogRep: DogsRepositoryImpl
) : ViewModel() {

    // Firebase
    private val _dogs = MutableLiveData<Resource<List<Dog>>>()
    val dogs: LiveData<Resource<List<Dog>>> get() = _dogs

    private val _addDogStatus = MutableLiveData<Resource<Void>>()
    val addDogStatus:LiveData<Resource<Void>> = _addDogStatus

    private val _removeDogStatus = MutableLiveData<Resource<Void>>()
    val removeDogStatus:LiveData<Resource<Void>> = _removeDogStatus

//    // Room
//    // This code should go in FavoriteDogsFragmentViewModel! Along with its observer
//    val favoriteDogs: LiveData<List<Dog>>? = dogRep.getFavoriteDogs()

//    private val _chosenDog = MutableLiveData<Resource<Dog>>()
//    val chosenDog:LiveData<Resource<Dog>> = _chosenDog

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
        private val userRepo: UserRepositoryImpl,
        private val dogRep: DogsRepositoryImpl
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainFragmentViewModel(userRepo, dogRep) as T
        }
    }
}
