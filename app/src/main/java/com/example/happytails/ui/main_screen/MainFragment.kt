package com.example.happytails.ui.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.happytails.R
import com.example.happytails.data.models.Item
import com.example.happytails.databinding.MainFragmentBinding
import com.example.happytails.repository.FirebaseImpl.AuthRepositoryImpl
import com.example.happytails.repository.FirebaseImpl.DogsRepositoryImpl
import il.co.syntax.firebasemvvm.model.Task
import il.co.syntax.firebasemvvm.ui.all_tasks.AllTasksViewModel
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared

class MainFragment : Fragment() {

    private var binding: MainFragmentBinding by autoCleared()

    private val viewModel: MainFragmentViewModel by viewModels {
        AllTasksViewModel.AllTaskViewModelFactory(AuthRepositoryImpl(), DogsRepositoryImpl())
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
        viewModel.dogs.observe(viewLifecycleOwner) {

            binding.recycler.adapter = ItemAdapter(it, object : ItemAdapter.ItemListener {
                override fun onItemDetailsClicked(item: Item) {
                    viewModel.setItem(item)
                    findNavController().navigate(R.id.action_mainFragment_to_detailsFragment)
                }

                override fun onFavoriteClicked(item: Item) {

                }

            })
        }

//        ItemTouchHelper(object : ItemTouchHelper.Callback() {
//            override fun getMovementFlags(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder
//            ) = makeFlag(
//                ItemTouchHelper.ACTION_STATE_SWIPE,
//                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//            )
//
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                // ItemManager.remove(viewHolder.adapterPosition)
//
//                val item =
//                    (binding.recycler.adapter as ItemAdapter).itemAt(viewHolder.adapterPosition)
//                viewModel.deleteItem(item)
//                binding.recycler.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
//            }
//        }).attachToRecyclerView(binding.recycler)
    }


}