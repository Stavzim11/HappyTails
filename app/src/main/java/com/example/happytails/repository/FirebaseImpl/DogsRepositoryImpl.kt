package com.example.happytails.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.happytails.data.models.Dog
import com.example.happytails.data.models.DogDao
import com.example.happytails.data.models.DogDatabase
import com.example.happytails.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import safeCall

class DogsRepositoryImpl(application: Application) {

    private val DogDao: DogDao?


    init {
        val db = DogDatabase.getDatabase(application.applicationContext)
        DogDao = db.dogDao()
    }


    //For favorites
    fun getFavoriteDogs() = DogDao?.getFavoriteDogs()

    suspend fun addDogToFavorites(Dog: Dog) {
        withContext(Dispatchers.IO) {
            DogDao?.insertDog(Dog)
        }
    }

    suspend fun deleteFavoriteDog(Dog: Dog) {
        withContext(Dispatchers.IO) {
            DogDao?.deleteDog(Dog)
        }
    }






    //Firebase
    private val dogRef = FirebaseFirestore.getInstance().collection("dogs")

    suspend fun addDog(dog: Dog) = withContext(Dispatchers.IO) {

        safeCall<Void> {
            val dogId = dogRef.document().id
            val addition = dogRef.document(dogId).set(dog).await()
            Resource.Success(addition)
        }
    }

    suspend fun deleteDog(dog: Dog) = withContext(Dispatchers.IO) {
        safeCall<Void> {
            val result = dogRef.document(dog.id.toString()).delete().await()
            Resource.Success(result)
        }
    }


    suspend fun getDog(id: Int) = withContext(Dispatchers.IO) {
        safeCall<Dog> {
            val result = dogRef.document(id.toString()).get().await()
            val dog = result.toObject(Dog::class.java)
            Resource.Success(dog!!)
        }
    }

    suspend fun getDogs() = withContext(Dispatchers.IO) {
        safeCall<List<Dog>> {
            val result = dogRef.get().await()
            val dogs = result.toObjects(Dog::class.java)
            Resource.Success(dogs)
        }
    }

    suspend fun updateDog(dog: Dog) = withContext(Dispatchers.IO) {
        safeCall<Void> {
            val result = dogRef.document(dog.id.toString()).set(dog).await()
            Resource.Success(result)
        }
    }
}