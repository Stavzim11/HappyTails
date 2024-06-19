package com.example.happytails.ui.main_screen

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.happytails.R
import com.example.happytails.data.models.Item
import com.example.happytails.data.models.ItemRepository
import com.example.happytails.repository.AuthRepository
import com.example.happytails.repository.DogsRepository
import com.example.happytails.utils.Resource
import il.co.syntax.firebasemvvm.model.Task
import kotlinx.coroutines.launch

class MainFragmentViewModel(private val authRep: AuthRepository, private val dogRep:DogsRepository) : ViewModel() {

//    //With LiveData
//    private val _tasksStatus : MutableLiveData<Resource<List<Task>>> = MutableLiveData()
//    val taskStatus: LiveData<Resource<List<Task>>> = _tasksStatus
//

    val _dogs: MutableLiveData<Resource<List<Item>>> = MutableLiveData()
    val dogs :LiveData<Resource<List<Item>>> = _dogs
    //val favoriteItems: LiveData<List<Item>>? = dogRep.getFavoriteItems()

    private val _chosenItem = MutableLiveData<Item>()
    val chosenItem: LiveData<Item> get() = _chosenItem

    init {
        dogRep.getDogsLiveData(_dogs)
    }

    fun setItem(item: Item) {
        _chosenItem.value = item
    }





    
    
}