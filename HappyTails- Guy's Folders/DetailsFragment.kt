package com.example.happytails

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.happytails.databinding.DetailsFragmentBinding

class DetailsFragment : Fragment() {

//    private val binding: DetailsFragmentBinding by autoCleared()
//    private val viewModel: MainFragmentViewModel by activityViewModels()
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        viewModel.chosenItem.observe(viewLifecycleOwner) {
//            binding.itemName.text = it.title
//            binding.itemBreed.text = it.description
//            binding.itemMoreDetails.text = it.moreDetails
//        }
//    }
//}

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = arguments?.getString("title")
        val breed = arguments?.getString("description")
        val photoUri = arguments?.getString("photo")
        val moreDetails = arguments?.getString("moreDetails")


        binding.itemName.text = name
        binding.itemBreed.text = breed
        binding.itemMoreDetails.text = moreDetails

        if (!photoUri.isNullOrEmpty()) {
            binding.itemImage1.setImageURI(Uri.parse(photoUri))
        } else {
            val defaultPhotoUri =
                Uri.parse("android.resource://${requireContext().packageName}/drawable/logo1")
            binding.itemImage1.setImageURI(defaultPhotoUri)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}