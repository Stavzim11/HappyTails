package com.example.happytails.repository.implementations

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.happytails.data.models.Dog
import com.example.happytails.data.models.DogDao
import com.example.happytails.data.models.DogDatabase
import com.example.happytails.repository.DogsRepository
import com.example.happytails.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import com.example.happytails.utils.safeCall

class DogsRepositoryImpl(application: Application) : DogsRepository {

    private val dogDao: DogDao?

    // For navigation to details fragment
    private var chosenDog: MutableLiveData<Dog> = MutableLiveData()

    init {
        val db = DogDatabase.getDatabase(application.applicationContext)
        dogDao = db.dogDao()
    }


    //For favorites
    fun getFavoriteDogs() = dogDao?.getFavoriteDogs()

    override suspend fun addDogToFavorites(dog: Dog) = withContext(Dispatchers.IO) {
        safeCall {
            Resource.Success(dogDao?.insertDog(dog))
        }
    }

    override suspend fun deleteFavoriteDog(dog: Dog) = withContext(Dispatchers.IO) {
        safeCall {
            Resource.Success(dogDao?.deleteDog(dog))
        }
    }







    //Firebase
    private val dogRef = FirebaseFirestore.getInstance().collection("dogs")

    override suspend fun addDog(dog: Dog) = withContext(Dispatchers.IO) {

        safeCall {
            val dogId = dogRef.document().id
            val addition = dogRef.document(dogId).set(dog).await()
            Resource.Success(addition)
        }
    }

    override suspend fun deleteDog(dog: Dog) = withContext(Dispatchers.IO) {
        safeCall {
            val result = dogRef.document(dog.id.toString()).delete().await()
            Resource.Success(result)
        }
    }


    override suspend fun getDog(id: Int) = withContext(Dispatchers.IO) {
        safeCall {
            val result = dogRef.document(id.toString()).get().await()
            val dog = result.toObject(Dog::class.java)
            Resource.Success(dog!!)
        }
    }

    override suspend fun getDogs() = withContext(Dispatchers.IO) {
        safeCall {
            val result = dogRef.get().await()
            val dogs = result.toObjects(Dog::class.java)
            Resource.Success(dogs)
        }
    }

    override fun getDogsLiveData(data: MutableLiveData<Resource<List<Dog>>>) {

        data.postValue(Resource.Loading())

        dogRef.orderBy("id").addSnapshotListener {snapshot, e ->
            if(e != null) {
                data.postValue(Resource.Error(e.localizedMessage))
            }
            if(snapshot != null && !snapshot.isEmpty) {
                data.postValue(Resource.Success(snapshot.toObjects(Dog::class.java)))
            }
            else {
                data.postValue(Resource.Error("No Data"))
            }
        }
    }

    override suspend fun updateDog(dog: Dog) = withContext(Dispatchers.IO) {
        safeCall {
            val result = dogRef.document(dog.id.toString()).set(dog).await()
            Resource.Success(result)
        }
    }
}