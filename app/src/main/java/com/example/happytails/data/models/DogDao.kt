package com.example.happytails.data.models

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.happytails.utils.Resource

@Dao
interface DogDao {

    @Delete
    suspend fun deleteDog(dog: Dog) : Void

    @Update
    suspend fun updateItem(dog: Dog)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDog(dog: Dog) : Void


    @Query("SELECT * from dogs WHERE isFavorite = 1")
    fun getFavoriteDogs(): LiveData<List<Dog>>

}

