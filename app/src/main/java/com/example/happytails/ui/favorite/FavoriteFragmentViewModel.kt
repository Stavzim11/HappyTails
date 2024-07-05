package com.example.happytails.ui.favorite

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
import com.example.happytails.ui.main_screen.MainFragmentViewModel
import com.example.happytails.utils.Resource
import kotlinx.coroutines.launch


class FavoriteFragmentViewModel(
    application: Application,
    private val userRep: UserRepositoryImpl,
    private val dogRep: DogsRepositoryImpl
) : AndroidViewModel(application) {


    class FavoriteViewModelFactory(
        private val application: Application,
        private val userRepo: UserRepositoryImpl,
        private val dogRepo: DogsRepositoryImpl
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavoriteFragmentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FavoriteFragmentViewModel(application, userRepo, dogRepo) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    //     Room
    // This code should go in FavoriteDogsFragmentViewModel! Along with its observer
    val favoriteDogs: LiveData<Resource<List<Dog>>> = MutableLiveData<Resource<List<Dog>>>().apply {
        postValue(Resource.Loading())
        dogRep.getFavoriteDogs()?.observeForever { dogs ->
            if (
                dogs == null
            )
                postValue(Resource.Error("Database Error"))
            else {
                postValue(Resource.Success(dogs))
            }
        }
    }

    fun removeDogFromFavorites(dog: Dog) {
        viewModelScope.launch {
            _removeDogStatus.postValue(dogRep.deleteFavoriteDog(dog))
        }
    }
    private val _chosenDog = MutableLiveData<Resource<Dog>>()
    val chosenDog: LiveData<Resource<Dog>> = _chosenDog

    private val _removeDogStatus = MutableLiveData<Resource<Void>>()
    val removeDogStatus: LiveData<Resource<Void>> = _removeDogStatus

}