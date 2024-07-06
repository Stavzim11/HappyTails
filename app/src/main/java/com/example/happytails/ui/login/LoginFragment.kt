package com.example.happytails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.happytails.R
import com.example.happytails.databinding.FragmentLoginBinding
import com.example.happytails.repository.UserRepository
import com.example.happytails.repository.implementations.UserRepositoryImpl
import com.example.happytails.utils.Resource
import com.example.happytails.utils.autoCleared


class LoginFragment : Fragment() {

    private var binding :FragmentLoginBinding by autoCleared()
    private val viewModel: LoginViewModel by viewModels{
        LoginViewModel.LoginViewModelFactory(UserRepositoryImpl())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)

        binding.goToRegisterButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.loginButton.setOnClickListener {

            viewModel.signInUser(binding.email.text.toString(),
            binding.password.text.toString())
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userSignInStatus.observe(viewLifecycleOwner) {

            when(it) {
                is Resource.Loading -> {
                    binding.loginProgressBar.isVisible = true
                    binding.loginButton.isEnabled = false
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(),"Login successful", Toast.LENGTH_SHORT).show()
                    //ActivityVM.setUserStatus(true)
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
                is Resource.Error -> {
                    binding.loginProgressBar.isVisible = false
                    binding.loginButton.isEnabled = true
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                }
            }
        }
/*
        viewModel.currentUser.observe(viewLifecycleOwner) {

            when(it) {
                is Resource.Loading -> {
                    binding.loginProgressBar.isVisible = true
                    binding.loginButton.isEnabled = false
                }
                is Resource.Success -> {
//                    ActivityVM.setUserStatus(true)
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)

                }
                is Resource.Error -> {
                    binding.loginProgressBar.isVisible = false
                    binding.loginButton.isEnabled = true
                }
            }
        }
        
 */
    }
}