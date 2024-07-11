package com.example.happytails.ui.details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.happytails.data.models.Dog
import com.example.happytails.databinding.DetailsFragmentBinding
import com.example.happytails.utils.autoCleared
import android.content.pm.PackageManager

class DetailsFragment : Fragment() {
    private var binding: DetailsFragmentBinding by autoCleared()
    private val viewModel: DetailsFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get arguments from the bundle and create a Dog object
        val name = arguments?.getString("name") ?: ""
        val description = arguments?.getString("description") ?: ""
        val photo = arguments?.getString("photo")
        val moreDetails = arguments?.getString("moreDetails") ?: ""
        val dog = Dog(name, description, moreDetails, photo)

        // Set data to ViewModel
        viewModel.setDog(dog)

        // Observe the ViewModel
        viewModel.name.observe(viewLifecycleOwner, Observer {
            binding.dogName.text = it
        })

        viewModel.description.observe(viewLifecycleOwner, Observer {
            binding.dogBreed.text = it
        })

        viewModel.moreDetails.observe(viewLifecycleOwner, Observer {
            binding.dogMoreDetails.text = it
        })

        viewModel.photoUri.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                loadImage(binding.dogImage1, it)
            } else {
                // Load a default image if no image is provided
                val defaultPhotoUri =
                    Uri.parse("android.resource://${requireContext().packageName}/drawable/logo1")
                binding.dogImage1.setImageURI(defaultPhotoUri)
            }
        })
    }

    private fun loadImage(imageView: ImageView, uri: Uri) {
        // Check for READ_EXTERNAL_STORAGE permission before setting the URI
        val permission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (permission == PackageManager.PERMISSION_GRANTED) {
            imageView.setImageURI(uri)
        }

    }
}
