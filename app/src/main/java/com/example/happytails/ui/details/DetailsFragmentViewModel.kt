package com.example.happytails.ui.details

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.happytails.data.models.Dog

class DetailsFragmentViewModel : ViewModel() {

    private val _name = MutableLiveData<String?>()
    val name: LiveData<String?> get() = _name

    private val _description = MutableLiveData<String?>()
    val description: LiveData<String?> get() = _description

    private val _photoUri = MutableLiveData<Uri?>()
    val photoUri: LiveData<Uri?> get() = _photoUri

    private val _moreDetails = MutableLiveData<String?>()
    val moreDetails: LiveData<String?> get() = _moreDetails

    fun setData(name: String?, description: String?, photoUri: String?, moreDetails: String?) {
        _name.value = name
        _description.value = description
        _moreDetails.value = moreDetails
        _photoUri.value = if (!photoUri.isNullOrEmpty()) {
            Uri.parse(photoUri)
        } else {
            null
        }
    }

    // Method to set a Dog object directly
    fun setDog(dog: Dog) {
        setData(dog.name, dog.description, dog.photo, dog.moreDetails)
    }
}
