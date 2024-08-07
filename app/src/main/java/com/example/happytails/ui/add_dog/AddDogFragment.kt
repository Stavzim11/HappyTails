package com.example.happytails.ui.add_dog

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.happytails.R
import com.example.happytails.databinding.AddDogFragmentBinding
import com.example.happytails.repository.implementations.DogsRepositoryImpl
import com.example.happytails.repository.implementations.UserRepositoryImpl
import com.example.happytails.ui.main_screen.MainFragmentViewModel
import com.example.happytails.utils.Resource
import com.example.happytails.utils.autoCleared
import com.google.android.material.snackbar.Snackbar

class AddDogFragment : androidx.fragment.app.Fragment() {

    private var binding: AddDogFragmentBinding by autoCleared()

    private val viewModel: AddDogViewModel by viewModels {
        AddDogViewModel.AddDogViewModelFactory(
            requireActivity().application,
            UserRepositoryImpl(),
            DogsRepositoryImpl(requireActivity().application)
        )
    }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            viewModel.setSelectedImageUri(uri)
        }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            openGallery()
        } else {
            // Handle permission denied case (e.g., show a snackbar)
            Toast.makeText(requireContext(), "Storage permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddDogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up breed AutoCompleteTextView
        val breedAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.choose_breed,
            android.R.layout.simple_dropdown_item_1line
        )
        binding.breedItem.setAdapter(breedAdapter)

        // Set up location AutoCompleteTextView
        val locationAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.choose_location,
            android.R.layout.simple_dropdown_item_1line
        )
        binding.locationItem.setAdapter(locationAdapter)

        // Set up age Spinner
        val ageAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.choose_age,
            android.R.layout.simple_spinner_item
        )
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.ageItem.adapter = ageAdapter

        // Set up size Spinner
        val sizeAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.choose_size,
            android.R.layout.simple_spinner_item
        )
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sizeItem.adapter = sizeAdapter

        // Set up gender Spinner
        val genderAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.choose_gender,
            android.R.layout.simple_spinner_item
        )
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.genderItem.adapter = genderAdapter

        viewModel.selectedImageUri.observe(viewLifecycleOwner) { uri ->
            uri?.let {
                binding.resultImageBtn.setImageURI(it)
            }
        }

        binding.doneBtn.setOnClickListener {
            val itemTitle = binding.dogNameTitle.text.toString().trim()
            val dogBreed = binding.breedItem.text.toString().trim()
            val description = binding.moreDetailsItem.text.toString().trim()
            val location = binding.locationItem.text.toString().trim()
            val age = binding.ageItem.selectedItem.toString().trim()
            val size = binding.sizeItem.selectedItem.toString().trim()
            val gender = binding.genderItem.selectedItem.toString().trim()

            if (itemTitle.isEmpty() || dogBreed.isEmpty() || description.isEmpty() || location.isEmpty() || age.isEmpty() || size.isEmpty() || gender.isEmpty()) {
                Toast.makeText(
                    requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT ).show()
            } else {
                viewModel.setItemTitle(itemTitle)
                viewModel.setItemDescription(dogBreed)
                viewModel.setMoreDetails(description)
                viewModel.setLocation(location)
                viewModel.setAge(age)
                viewModel.setSize(size)
                viewModel.setGender(gender)

                viewModel.addDogStatus.observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Loading -> {
                            binding.progressBar.isVisible = true
                        }

                        is Resource.Success -> {
                            binding.progressBar.isVisible = false
                            Snackbar.make(binding.addDogFragment, "Dog Added!", Snackbar.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_addDogFragment_to_mainFragment)
                        }

                        is Resource.Error -> {
                            binding.progressBar.isVisible = false
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }

                    }
                }
                viewModel.insertItem()

                findNavController().navigate(R.id.action_addDogFragment_to_mainFragment)
            }
        }
        binding.pickImageBtn.setOnClickListener {
            // Check permission before launching the gallery
            if (ContextCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                openGallery()
            }
        }
    }



//        binding.pickImageBtn.setOnClickListener {
//            if (ContextCompat.checkSelfPermission(
//                    requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                ActivityCompat.requestPermissions(
//                    requireActivity(),
//                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                    REQUEST_READ_EXTERNAL_STORAGE
//                )
//            } else {
//                openGallery()
//            }
//        }
//    }
//
//    @Deprecated("Deprecated in Java")
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            openGallery()
//        }
//    }

    private fun openGallery() {
        pickImageLauncher.launch("image/*")
    }

//    companion object {
//        private const val REQUEST_READ_EXTERNAL_STORAGE = 100
//    }
}
