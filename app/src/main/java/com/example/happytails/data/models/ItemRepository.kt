package com.example.happytails.data.models

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ItemRepository(application: Application) {

    private val itemDao: ItemDao?


    init{
        val db = ItemDatabase.getDatabase(application.applicationContext)
        itemDao = db.itemDao()
    }

    fun getItems() = itemDao?.getItems()

    fun getFavoriteItems() = itemDao?.getFavoriteItems()

    suspend fun insertItem(item: Item){
        withContext(Dispatchers.IO){
            itemDao?.insertItem(item)
        }
    }

    suspend fun deleteItem(item: Item){
        withContext(Dispatchers.IO){
            itemDao?.deleteItem(item)
        }
    }

    suspend fun updateItem(item: Item){
        withContext(Dispatchers.IO){
            itemDao?.updateItem(item)
        }
    }
}