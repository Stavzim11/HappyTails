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
import com.example.happytails.databinding.FragmentRegisterBinding
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared


class RegisterFragment : Fragment() {

    private var binging : FragmentRegisterBinding by autoCleared()
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
        binging = FragmentRegisterBinding.inflate(inflater, container, false)

        //register
        binging.registerButton.setOnClickListener{

            userViewModel.isConected.value=true

            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }

        //go to login
        binging.goToLoginButton.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return  binging.root

    }
}