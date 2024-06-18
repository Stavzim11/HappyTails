package com.example.happytails

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItemDao {

    @Delete
    fun deleteItem(item: Item)

    @Update
    fun updateItem(item: Item)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: Item)

    @Query("SELECT * from items")
    fun getItems() : LiveData<List<Item>>

    @Query("SELECT * from items WHERE isFavorite = 1")
    fun getFavoriteItems(): LiveData<List<Item>>

}