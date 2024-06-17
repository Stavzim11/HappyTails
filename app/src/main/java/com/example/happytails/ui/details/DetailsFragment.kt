package com.example.happytails.ui.details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.happytails.databinding.DetailsFragmentBinding

class DetailsFragment : Fragment() {

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
        val title = arguments?.getString("title")
        val description = arguments?.getString("description")
        val photoUri = arguments?.getString("photo")


        binding.itemTitle1.text = title
        binding.itemDescription1.text = description

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