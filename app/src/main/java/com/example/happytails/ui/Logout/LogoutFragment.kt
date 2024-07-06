package com.example.happytails.ui.Logout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.happytails.R
import com.example.happytails.databinding.FragmentLogoutBinding
import com.example.happytails.repository.implementations.UserRepositoryImpl
import com.example.happytails.utils.autoCleared


class LogoutFragment : Fragment() {
    private var binding :FragmentLogoutBinding by autoCleared()
    private val viewModel : LogoutViewModel by viewModels {
        LogoutViewModel.LogoutViewModelFactory(UserRepositoryImpl())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogoutBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.signOut()
        Toast.makeText(requireContext(),"Logout successful", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_logoutFragment_to_mainFragment)
    }


}