package com.example.happytails.data.models

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DogDao {

    @Delete
    suspend fun deleteDog(dog: Dog)

    @Update
    suspend fun updateItem(dog: Dog)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDog(dog: Dog)

    @Query("SELECT * from dogs")
    suspend fun getItems() : LiveData<List<Dog>>

    @Query("SELECT * from dogs WHERE isFavorite = 1")
    fun getFavoriteDogs(): LiveData<List<Dog>>

}