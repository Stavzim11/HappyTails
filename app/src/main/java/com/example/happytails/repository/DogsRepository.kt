package com.example.happytails.repository

import androidx.lifecycle.MutableLiveData
import com.example.happytails.data.models.Dog
import com.example.happytails.utils.Resource

interface DogsRepository {

     suspend fun addDog(dog: Dog): Resource<Void>
    suspend fun deleteDog(dog: Dog): Resource<Void>
    suspend fun getDog(id: Int): Resource<Dog>
    suspend fun getDogs(): Resource<List<Dog>>
    suspend fun updateDog(dog: Dog): Resource<Void>
    //suspend fun getDogsFiltered(filter: dogFilter) : Resource<List<Dog>>

    fun getDogsLiveData(data: MutableLiveData<Resource<List<Dog>>>)
    suspend fun addDogToFavorites(dog: Dog): Resource<Void>
    suspend fun deleteFavoriteDog(dog: Dog): Resource<Void>
}