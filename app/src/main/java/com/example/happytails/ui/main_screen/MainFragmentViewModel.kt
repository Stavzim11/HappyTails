package com.example.happytails.ui.main_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happytails.data.models.Item
import com.example.happytails.data.models.ItemRepository
import com.example.happytails.repository.AuthRepository
import com.example.happytails.utils.Resource
import il.co.syntax.firebasemvvm.model.Task
import kotlinx.coroutines.launch

class MainFragmentViewModel(private val authRep: AuthRepository, private val itemRep:ItemRepository) : ViewModel() {

//    //With LiveData
//    private val _tasksStatus : MutableLiveData<Resource<List<Task>>> = MutableLiveData()
//    val taskStatus: LiveData<Resource<List<Task>>> = _tasksStatus
//

    val items: LiveData<List<Item>>? = itemRep.getItems()
    val favoriteItems: LiveData<List<Item>>? = itemRep.getFavoriteItems()

    private val _chosenItem = MutableLiveData<Item>()
    val chosenItem: LiveData<Item> get() = _chosenItem

    fun setItem(item: Item) {
        _chosenItem.value = item
    }

    fun insertItem(item: Item) {
        viewModelScope.launch {
            itemRep.insertItem(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemRep.deleteItem(item)
        }
    }

    fun updateItem(item: Item) {
        viewModelScope.launch {
            itemRep.updateItem(item)
        }
    }
}