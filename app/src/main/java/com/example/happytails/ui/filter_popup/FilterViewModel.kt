package com.example.happytails.ui.filter_popup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.happytails.repository.DogsRepository
class FilterViewModel(private val dogsRepo: DogsRepository) : ViewModel() {
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

class FilterViewModelFactory(private val dogsRepo: DogsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FilterViewModel(dogsRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}