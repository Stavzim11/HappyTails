package com.example.happytails.ui.filter_popup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
