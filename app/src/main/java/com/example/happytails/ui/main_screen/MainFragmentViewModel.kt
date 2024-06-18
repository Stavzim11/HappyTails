package com.example.happytails.ui.main_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {


    private val repository = ItemRepository(application)


    val items: LiveData<List<Item>>? = repository.getItems()
    val favoriteItems: LiveData<List<Item>>? = repository.getFavoriteItems()

    private val _chosenItem = MutableLiveData<Item>()
    val chosenItem: LiveData<Item> get() = _chosenItem

    fun setItem(item: Item) {
        _chosenItem.value = item
    }

    fun insertItem(item: Item) {
        viewModelScope.launch {
            repository.insertItem(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    fun updateItem(item: Item) {
        viewModelScope.launch {
            repository.updateItem(item)
        }
    }
}