package com.example.happytails

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.happytails.databinding.AddItemFragmentBinding
import com.example.happytails.databinding.MainFragmentBinding
import android.Manifest
import android.widget.Toast
import androidx.fragment.app.activityViewModels

class AddItemFragment : Fragment() {

    private var binding: AddItemFragmentBinding by autoCleared()

    private var selectedImageUri: Uri? = null

    private val viewModel : MainFragmentViewModel by activityViewModels()

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                selectedImageUri = it
                binding.resultImageBtn.setImageURI(it)

            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddItemFragmentBinding.inflate(inflater, container, false)

        binding.doneBtn.setOnClickListener {

            //Checks if the user inputted correctly
            val itemTitle = binding.itemTitle.text.toString().trim()
            val itemDescription = binding.breedItem.text.toString().trim()


            if (itemTitle.isEmpty() || itemDescription.isEmpty()) {

                Toast.makeText(
                    requireContext(),
                    "Please enter your dog's name and breed",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                val defaultPhotoUri =
                    Uri.parse("android.resource://${requireContext().packageName}/drawable/logo1")
                val photoUri = selectedImageUri ?: defaultPhotoUri
                val photoUrls = listOf(photoUri.toString())
                val item = Item(
                    binding.itemTitle.text.toString(),
                    binding.breedItem.text.toString(),
                    binding.moreDetailsItem.text.toString(),
                    photoUri.toString(),
                    photoUrls
                )

                viewModel.insertItem(item)
                //ItemManager.add(item)

                val bundle = Bundle().apply {
                    putString("title", itemTitle)
                    putString("description", itemDescription)
                    putString("photo", photoUri.toString())


                }
                findNavController().navigate(R.id.action_addItemFragment_to_mainFragment)
            }
        }

        binding.pickImageBtn.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_READ_EXTERNAL_STORAGE
                )
            } else {
                openGallery()
            }
        }
        return binding.root

    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery()
        }
    }

    private fun openGallery() {
        pickImageLauncher.launch("image/*")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    companion object {
        private const val REQUEST_READ_EXTERNAL_STORAGE = 100
    }

}
