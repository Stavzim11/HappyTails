package com.example.happytails.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.happytails.R
import com.example.happytails.databinding.MainFragmentBinding
import com.example.happytails.ui.main_screen.ItemAdapter

class FavoriteFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainFragmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.favoriteItems?.observe(viewLifecycleOwner) { items ->
            binding.recycler.adapter = ItemAdapter(items, { bundle ->
                findNavController().navigate(
                    R.id.action_favoriteFragment_to_detailsFragment, bundle
                )
            }, viewModel)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}