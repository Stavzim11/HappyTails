package com.example.architectureproject.ui.filter_popup

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
//import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.happytails.FilterViewModel
import com.example.happytails.R
import com.example.happytails.databinding.FilterPopupFragmentBinding
//import com.example.happytails.MainFragmentViewModel

class FilterPopupFragment : Fragment() {

    private var _binding : FilterPopupFragmentBinding? = null
    private val binding
        get() = _binding!!

    private var imageUri : Uri?  = null

    private val viewModel : FilterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FilterPopupFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** drop-down menus */
        // Locations
        val locations = resources.getStringArray(R.array.choose_location)
        val locationsAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, locations)
        binding.autoCompleteLocations.setAdapter(locationsAdapter)
        // Ages
        val ages = resources.getStringArray(R.array.choose_age)
        val agesAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, ages)
        binding.autoCompleteAges.setAdapter(agesAdapter)
        // Sizes
        val sizes = resources.getStringArray(R.array.choose_size)
        val sizesAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, sizes)
        binding.autoCompleteSizes.setAdapter(sizesAdapter)
        // Genders
        val genders = resources.getStringArray(R.array.choose_gender)
        val gendersAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, genders)
        binding.autoCompleteGenders.setAdapter(gendersAdapter)


        /** Finish Button */
        binding.finishBtn.setOnClickListener {
            val location = binding.autoCompleteLocations.text.toString()
            val age = binding.autoCompleteAges.text.toString()
            val size = binding.autoCompleteSizes.text.toString()
            val gender = binding.autoCompleteGenders.text.toString()
            viewModel.setFilters(location, age, size, gender)

            findNavController().navigate(R.id.action_filterPopupFragment_to_mainFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}