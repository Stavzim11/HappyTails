package com.example.happytails.ui.details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.happytails.databinding.DetailsFragmentBinding
import com.example.happytails.databinding.FavoriteFragmentBinding
import com.example.happytails.utils.autoCleared

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

        //Get arguments from the bundle

        //TODO This is from the adapter, NEED TO ADD MORE DETAILS
        val name = arguments?.getString("title")
        val breed = arguments?.getString("description")
        val photoUri = arguments?.getString("photo")
        val moreDetails = arguments?.getString("moreDetails")

        //Set data to ViewModel
        viewModel.setData(name, breed, photoUri, moreDetails)

        //Observe the ViewModel
        viewModel.name.observe(viewLifecycleOwner, Observer {
            binding.dogName.text = it
        })

        viewModel.breed.observe(viewLifecycleOwner, Observer {
            binding.dogBreed.text = it
        })

        viewModel.moreDetails.observe(viewLifecycleOwner, Observer {
            binding.dogMoreDetails.text = it
        })

        viewModel.photoUri.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.dogImage1.setImageURI(it)
            } else {
                val defaulPhotoUri =
                    Uri.parse("android.resource://${requireContext().packageName}/drawable/logo1")
                binding.dogImage1.setImageURI(defaulPhotoUri)
            }
        })
    }

}