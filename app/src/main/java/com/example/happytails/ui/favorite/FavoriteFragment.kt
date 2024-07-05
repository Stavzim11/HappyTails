package com.example.happytails.ui.favorite

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.happytails.R
import com.example.happytails.data.models.Dog
import com.example.happytails.databinding.FavoriteFragmentBinding
import com.example.happytails.databinding.MainFragmentBinding
import com.example.happytails.repository.implementations.DogsRepositoryImpl
import com.example.happytails.repository.implementations.UserRepositoryImpl
import com.example.happytails.ui.main_screen.DogAdapter
import com.example.happytails.ui.main_screen.MainFragmentViewModel
import com.example.happytails.utils.Resource
import com.example.happytails.utils.autoCleared
import com.google.android.material.snackbar.Snackbar

class FavoriteFragment : Fragment() {

    private var binding: FavoriteFragmentBinding by autoCleared()
    private val viewModel: FavoriteFragmentViewModel by viewModels {
        FavoriteFragmentViewModel.FavoriteViewModelFactory(
            requireActivity().application,
            UserRepositoryImpl(),
            DogsRepositoryImpl(requireActivity().application)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavoriteFragmentBinding.inflate(inflater, container, false)
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
                viewModel.removeDogFromFavorites(dog)
            }
        })


        viewModel.removeDogStatus.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    Snackbar.make(binding.favCoordinator, "Dog Removed!", Snackbar.LENGTH_SHORT)
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


        viewModel.favoriteDogs?.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    (binding.recycler.adapter as DogAdapter).setDogs(it.data!!)
                }

                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

            }
        }

    }
}

