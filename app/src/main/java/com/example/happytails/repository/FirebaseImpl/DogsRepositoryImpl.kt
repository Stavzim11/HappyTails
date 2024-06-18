package com.example.happytails.repository.FirebaseImpl

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.example.happytails.data.models.Item
import com.example.happytails.repository.DogsRepository
import com.example.happytails.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import safeCall

class DogsRepositoryImpl : DogsRepository {

    private val dogRef = FirebaseFirestore.getInstance().collection("dogs")

    override suspend fun addDog(dog: Item) = withContext(Dispatchers.IO) {

        safeCall<Void> {
            val dogId = dogRef.document().id
            val addition = dogRef.document(dogId).set(dog).await()
            Resource.Success(addition)
        }
    }

    override suspend fun deleteDog(dog: Item) = withContext(Dispatchers.IO) {
        safeCall<Void> {
            val result  = dogRef.document(dog.id.toString()).delete().await()
            Resource.Success(result)
        }
    }


    override suspend fun getDog(id: Int) = withContext(Dispatchers.IO) {
        safeCall<Item> {
            val result = dogRef.document(id.toString()).get().await()
            val dog  = result.toObject(Item::class.java)
            Resource.Success(dog!!)
        }
    }

    override suspend fun getDogs()= withContext(Dispatchers.IO) {
        safeCall<List<Item>> {
            val result  = dogRef.get().await()
            val dogs = result.toObjects(Item::class.java)
            Resource.Success(dogs)
        }
    }

    override suspend fun updateDog(dog: Item) = withContext(Dispatchers.IO) {
        safeCall<Void> {
            val result = dogRef.document(dog.id.toString()).set(dog).await()
            Resource.Success(result)
        }
    }


    /*
    override fun getTasksLiveData(data: MutableLiveData<Resource<List<Task>>>) {

        data.postValue(Resource.Loading())

        taskRef.orderBy("title").addSnapshotListener {snapshot, e ->
            if(e != null) {
                data.postValue(Resource.Error(e.localizedMessage))
            }
            if(snapshot != null && !snapshot.isEmpty) {
                data.postValue(Resource.Success(snapshot.toObjects(Task::class.java)))
            }
            else {
                data.postValue(Resource.Error("No Data"))
            }
        }
    }

     */
}