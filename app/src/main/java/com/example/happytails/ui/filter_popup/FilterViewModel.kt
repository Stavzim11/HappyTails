package com.example.happytails.ui.filter_popup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.happytails.repository.DogsRepository
import com.example.happytails.repository.implementations.DogsRepositoryImpl
import com.example.happytails.repository.implementations.UserRepositoryImpl
import com.example.happytails.ui.favorite.FavoriteFragmentViewModel

class FilterViewModel(
    application: Application,
    private val dogsRepo: DogsRepositoryImpl
) : AndroidViewModel(application) {
//class FilterViewModel
//(private val dogsRepo: DogsRepository) : ViewModel() {
    fun setFilters(location: String?, age: String?, size: String?, gender: String?) {
        val filters = MutableLiveData<Map<String, String?>>().apply {
            value = mapOf(
                "Location" to location,
                "Age" to age,
                "Size" to size,
                "Gender" to gender
            )
        }
        dogsRepo.setFilters(filters)
    }
}

//class FilterViewModelFactory(private val dogsRepo: DogsRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(FilterViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return FilterViewModel(dogsRepo) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
class FilterViewModelFactory(
    private val application: Application,
    private val dogRepo: DogsRepositoryImpl
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FilterViewModel(application,dogRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}