package com.example.happytails.ui.add_dog

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.happytails.databinding.AddItemFragmentBinding
import android.Manifest
import com.example.happytails.data.models.Item
import com.example.happytails.data.models.ItemManager
import com.example.happytails.R

class AddItemFragment : Fragment() {

    private var _binding: AddItemFragmentBinding? = null
    private val binding get() = _binding!!

    private var selectedImageUri: Uri? = null

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
            val defaultPhotoUri = Uri.parse("android.resource://${requireContext().packageName}/drawable/logo1")
            val photoUri = selectedImageUri ?: defaultPhotoUri
            val photoUrls = listOf(photoUri.toString())
            val item = Item(
                    binding.itemTitle.text.toString(),
                    binding.descItem.text.toString(),
                    photoUri.toString(),
                    photoUrls)
            ItemManager.add(item)
            findNavController().navigate(R.id.action_addItemFragment_to_mainFragment)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUEST_READ_EXTERNAL_STORAGE = 100
    }

}