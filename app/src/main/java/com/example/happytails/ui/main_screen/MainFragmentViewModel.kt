package com.example.happytails.ui.main_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.happytails.data.models.Dog
import com.example.happytails.repository.DogsRepositoryImpl
import com.example.happytails.repository.FirebaseImpl.UserRepositoryImpl
import kotlinx.coroutines.launch

class MainFragmentViewModel(
    private val userRep: UserRepositoryImpl,
    private val dogRep: DogsRepositoryImpl
) : ViewModel() {

    private val _dogs = MutableLiveData<List<Dog>>()
    val dogs: LiveData<List<Dog>> get() = _dogs

    private val _favoriteDogs = MutableLiveData<List<Dog>>()
    val favoriteDogs: LiveData<List<Dog>> get() = _favoriteDogs

    private val _chosenDog = MutableLiveData<Dog>()
    val chosenDog: LiveData<Dog> get() = _chosenDog

    init {
        fetchDogs()
        fetchFavoriteDogs()
    }

    private fun fetchDogs() {
        viewModelScope.launch {
            _dogs.value = dogRep.getDogs().value
        }
    }

    private fun fetchFavoriteDogs() {
        viewModelScope.launch {
            dogRep.getFavoriteDogs()?.observeForever {
                _favoriteDogs.postValue(it)
            }
        }
    }

    fun setDog(dog: Dog) {
        _chosenDog.value = dog
    }

    fun insertDog(dog: Dog) {
        viewModelScope.launch {
            dogRep.addDog(dog)
        }
    }

    fun deleteDog(dog: Dog) {
        viewModelScope.launch {
            dogRep.deleteDog(dog)
        }
    }

    fun updateDog(dog: Dog) {
        viewModelScope.launch {
            dogRep.updateDog(dog)
        }
    }

    fun addDogToFavorites(dog: Dog) {
        viewModelScope.launch {
            dogRep.addDogToFavorites(dog)
            fetchFavoriteDogs()
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
