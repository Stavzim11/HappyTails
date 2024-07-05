package com.example.happytails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.happytails.R
import com.example.happytails.databinding.FragmentRegisterBinding
import com.example.happytails.utils.Resource
import com.example.happytails.utils.autoCleared

class RegisterFragment : Fragment(){

    private var binding : FragmentRegisterBinding by autoCleared()
    private val viewModel : RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)

        binding.registerButton.setOnClickListener {

            viewModel.createUser(binding.name.text.toString(),
             binding.email.text.toString(),
             binding.phone.text.toString(),
             binding.password.text.toString())
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userRegistrationStatus.observe(viewLifecycleOwner) {

            when(it) {
                is Resource.Loading -> {
                    binding.RegisterProgressBar.isVisible = true
                    binding.registerButton.isEnabled = false
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(),"Registration successful",Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                }
                is Resource.Error -> {
                    binding.RegisterProgressBar.isVisible = false
                    binding.registerButton.isEnabled = true
                }
            }
        }

    }
}