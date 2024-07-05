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
import com.google.firebase.firestore.Filter

class DogsRepositoryImpl(application: Application) : DogsRepository {

    private val dogDao: DogDao?

    private val filters: MutableLiveData<Map<String, String?>> = MutableLiveData()

    // For Favorites
    private var chosenDog: MutableLiveData<Dog> = MutableLiveData()

    init {
        val db = DogDatabase.getDatabase(application.applicationContext)
        dogDao = db.dogDao()
        val filters = MutableLiveData<Map<String, String?>>().apply {
            value = mapOf(
                "Location" to null,
                "Age" to null,
                "Size" to null,
                "Gender" to null
            )
        }
        setFilters(filters)
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
        val query = dogRef
            .where(Filter.and
                (Filter.or(Filter.equalTo("location", filters.value?.get("Location")), Filter.equalTo("location", null))
                ,Filter.or(Filter.equalTo("age", filters.value?.get("Age")), Filter.equalTo("age", null))
                ,Filter.or(Filter.equalTo("size", filters.value?.get("Size")), Filter.equalTo("size", null))
                ,Filter.or(Filter.equalTo("gender", filters.value?.get("Gender")), Filter.equalTo("gender", null))
                        )
            )
            .orderBy("id")

        query.addSnapshotListener {snapshot, e ->
            if(e != null) {
                data.postValue(Resource.Error(e.localizedMessage!!))
            }
            if(snapshot != null && !snapshot.isEmpty) {
                data.postValue(Resource.Success(snapshot.toObjects(Dog::class.java)))
            }
            else {
                data.postValue(Resource.Error("No Data"))
            }
        }
    }


    override fun setFilters(filters: MutableLiveData<Map<String, String?>>) {
        this.filters.postValue(filters.value)
    }

    override suspend fun updateDog(dog: Dog) = withContext(Dispatchers.IO) {
        safeCall {
            val result = dogRef.document(dog.id.toString()).set(dog).await()
            Resource.Success(result)
        }
    }
}