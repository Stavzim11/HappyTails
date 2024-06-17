package com.example.happytails.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.happytails.databinding.DetailsFragmentBinding
import com.example.happytails.databinding.FragmentUpdateBinding
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared

class UpdateFragment : Fragment() {

    private  var binding :FragmentUpdateBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString("title")
        val description = arguments?.getString("description")
        val photoUri = arguments?.getString("photo")

        binding.textInputTitle.setText(title)
        binding.textInputDesc.setText(description)
        binding.resultImage.setImageURI(Uri.parse(photoUri))

        binding.doneBtn.setOnClickListener{

        }
    }

}