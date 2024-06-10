package com.example.architectureproject.ui.filter_popup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.architectureproject.R
import com.example.architectureproject.data.models.Item
import com.example.architectureproject.databinding.FilterPopupFragmentBinding
import com.example.architectureproject.ui.all_items.MainFragmentViewModel

class FilterPopupFragment : Fragment() {

    private var _binding : FilterPopupFragmentBinding? = null
    private val binding
        get() = _binding!!

    private var imageUri : Uri?  = null

    private val viewModel : MainFragmentViewModel by activityViewModels()

    private val pickLauncher : ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            it?.let {
                binding.resultImageView.setImageURI(it)
                requireActivity().contentResolver.takePersistableUriPermission(it,Intent.FLAG_GRANT_READ_URI_PERMISSION)
                imageUri = it
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FilterPopupFragmentBinding.inflate(inflater,container,false)

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
            val item = Item(binding.itemTitleEt.text.toString(),binding.itemDescriptionEt.text.toString(),imageUri.toString())
            //ItemManager.add(item)

            viewModel.addItem(item)
            findNavController().navigate(R.id.action_filterPopupFragment_to_mainFragment)
        }

        binding.pickImageBtn.setOnClickListener {
            pickLauncher.launch(arrayOf("image/*"))
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}