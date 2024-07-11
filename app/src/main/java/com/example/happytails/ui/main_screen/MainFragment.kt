package com.example.happytails.ui.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.happytails.R
import com.example.happytails.data.models.Dog
import com.example.happytails.data.models.DogDatabase
import com.example.happytails.databinding.MainFragmentBinding
import com.example.happytails.repository.implementations.DogsRepositoryImpl
import com.example.happytails.repository.implementations.UserRepositoryImpl
import com.example.happytails.utils.Resource
import com.google.android.material.snackbar.Snackbar
import com.example.happytails.utils.autoCleared

class MainFragment : Fragment() {

    private var binding: MainFragmentBinding by autoCleared()
    private val viewModel: MainFragmentViewModel by viewModels {
        MainFragmentViewModel.AllDogViewModelFactory(
            requireActivity().application,
            UserRepositoryImpl()
//            DogsRepositoryImpl(requireActivity().application)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)

        binding.addItemBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addItemFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())

        binding.recycler.adapter = DogAdapter(object : DogAdapter.DogListener {

            override fun onDogDetailsClicked(dog: Dog) {
                findNavController().navigate(R.id.action_mainFragment_to_dogDetailsFragment)
            }

            override fun onFavoriteClicked(dog: Dog) {
                if (dog.isFavorite) {
                    viewModel.removeDogFromFavorites(dog)

                } else {
                    viewModel.addDogToFavorites(dog)

                }
            }
        })


        viewModel.dogs.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.addItemBtn.isEnabled = false
                }

                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    binding.addItemBtn.isEnabled = true
                    (binding.recycler.adapter as DogAdapter).setDogs(it.data!!)
                }

                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    binding.addItemBtn.isEnabled = true
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

            }
        }

        viewModel.addDogStatus.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    Snackbar.make(binding.coordinator, "Dog Added!", Snackbar.LENGTH_SHORT).show()
                }

                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

            }
        }

        viewModel.removeDogStatus.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    Snackbar.make(binding.coordinator, "Dog Removed!", Snackbar.LENGTH_SHORT)
                        .setAction("Undo") {
                            Toast.makeText(
                                requireContext(),
                                "For you to implement",
                                Toast.LENGTH_SHORT
                            ).show()
                        }.show()
                }

                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

            }
        }





//        // Chosen dog for details, navigate to details fragment
//        viewModel.chosenDog.observe(viewLifecycleOwner) {
//            when(it) {
//                is Resource.Loading -> {
//                    binding.progressBar.isVisible = true
//                }
//                is Resource.Success -> {
//                    binding.progressBar.isVisible = false
//                    findNavController().navigate(R.id.action_mainFragment_to_dogDetailsFragment)
//                }
//                is Resource.Error -> {
//                    binding.progressBar.isVisible = false
//                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
//                }
//
//            }
    }
}