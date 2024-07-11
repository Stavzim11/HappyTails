package com.example.happytails.ui.add_dog

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.happytails.data.models.Dog
import com.example.happytails.repository.implementations.DogsRepositoryImpl
import com.example.happytails.repository.implementations.UserRepositoryImpl
import com.example.happytails.ui.main_screen.MainFragmentViewModel

//import com.example.happytails.data.models.ItemManager

class AddDogViewModel(
    application: Application,
    private val userRep: UserRepositoryImpl,
    private val dogRep: DogsRepositoryImpl
) : AndroidViewModel(application) {
    private val _itemTitle = MutableLiveData<String>()
    val itemTitle: LiveData<String> get() = _itemTitle

    private val _itemDescription = MutableLiveData<String>()
    val itemDescription: LiveData<String> get() = _itemDescription

    private val _moreDetails = MutableLiveData<String>()
    val moreDetails: LiveData<String> get() = _moreDetails

    private val _selectedImageUri = MutableLiveData<Uri?>()
    val selectedImageUri: LiveData<Uri?> get() = _selectedImageUri

    private val _location = MutableLiveData<String>()
    val location: LiveData<String> get() = _location

    private val _age = MutableLiveData<String>()
    val age: LiveData<String> get() = _age

    private val _size = MutableLiveData<String>()
    val size: LiveData<String> get() = _size

    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String> get() = _gender

    fun setItemTitle(title: String) {
        _itemTitle.value = title
    }

    fun setItemDescription(description: String) {
        _itemDescription.value = description
    }

    fun setMoreDetails(details: String) {
        _moreDetails.value = details
    }

    fun setLocation(location: String) {
        _location.value = location
    }

    fun setAge(age: String) {
        _age.value = age
    }

    fun setSize(size: String) {
        _size.value = size
    }

    fun setGender(gender: String) {
        _gender.value = gender
    }

    fun setSelectedImageUri(uri: Uri?) {
        _selectedImageUri.value = uri
    }

    fun insertItem(){
        val dog = Dog(
            name = _itemTitle.value ?: "",
            description = _itemDescription.value ?: "",
            moreDetails = _moreDetails.value ?: "",
            photo = _selectedImageUri.value?.toString(),
            photoUrls = listOf(_selectedImageUri.value?.toString() ?: ""),
            isFavorite = false,
            location = _location.value,
            age = _age.value,
            size = _size.value,
            gender = _gender.value
        )
//        ItemManager.add(dog)

    }
    class AddDogViewModelFactory(
        private val application: Application,
        private val userRepo: UserRepositoryImpl,
        private val dogRepo: DogsRepositoryImpl
    ) : ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddDogViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddDogViewModel(application, userRepo, dogRepo) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

