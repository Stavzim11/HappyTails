package com.example.happytails.repository

import androidx.lifecycle.MutableLiveData
import com.example.happytails.data.models.Item
import com.example.happytails.utils.Resource

interface DogsRepository {

    suspend fun addDog(dog:Item) : Resource<Void>
    suspend fun deleteDog(dog: Item): Resource<Void>
    suspend fun getDog(id:Int) : Resource<Item>
    suspend fun getDogs() : Resource<List<Item>>
    suspend fun updateDog(dog:Item) : Resource<Void>
    //suspend fun getDogsFiltered(filter: dogFilter) : Resource<List<Item>>

    fun getDogsLiveData(data : MutableLiveData<Resource<List<Item>>>)

}