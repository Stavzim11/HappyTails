package com.example.happytails.ui.details

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailsFragmentViewModel : ViewModel() {

    //TODO add all dog details

    private val _name = MutableLiveData<String?>()
    val name: LiveData<String?> get() = _name

    private val _breed = MutableLiveData<String?>()
    val breed: LiveData<String?> get() = _breed

    private val _photoUri = MutableLiveData<Uri?>()
    val photoUri: LiveData<Uri?> get() = _photoUri

    private val _moreDetails = MutableLiveData<String?>()
    val moreDetails: LiveData<String?> get() = _moreDetails


    fun setData(name:String?, breed:String?, photoUri: String?, moreDetails: String?){

        _name.value = name
        _breed.value = breed
        _moreDetails.value = moreDetails
        _photoUri.value = if(!photoUri.isNullOrEmpty()){
            Uri.parse(photoUri)
        }else{
            null
        }
    }

}