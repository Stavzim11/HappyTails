package com.example.happytails.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.happytails.repository.DogsRepositoryImpl
import com.example.happytails.repository.FirebaseImpl.UserRepositoryImpl
import com.example.happytails.repository.UserRepository


class FavoriteFragmentViewModel(
    private val userRep: UserRepositoryImpl,
    private val DogRep: DogsRepositoryImpl
) : ViewModel() {

    class FavoriteViewModelFactory(
        private val userRepo: UserRepositoryImpl,
        private val dogRep: DogsRepositoryImpl
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavoriteFragmentViewModel(userRepo, dogRep) as T
        }
    }

}