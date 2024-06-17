package com.example.happytails.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.happytails.R
import com.example.happytails.ui.main_screen.UserViewModel
import com.example.happytails.databinding.FragmentLoginBinding
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared

class LoginFragment : Fragment() {

    private var binging : FragmentLoginBinding by autoCleared()
    private lateinit var userViewModel: UserViewModel

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            userViewModel= ViewModelProvider(requireActivity())[UserViewModel::class.java]


    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binging = FragmentLoginBinding.inflate(inflater, container, false)

        //login
        binging.loginButton.setOnClickListener{

            userViewModel.isConected.value=true
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
        //go to register
        binging.goToRegisterButton.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return  binging.root

    }
}