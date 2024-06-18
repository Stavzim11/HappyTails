package com.example.happytails.ui.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.happytails.R
import com.example.happytails.databinding.MainFragmentBinding
import com.example.happytails.repository.FirebaseImpl.AuthRepositoryImpl
import com.example.happytails.repository.FirebaseImpl.DogsRepositoryImpl
import com.example.happytails.ui.details.ItemAdapter
import il.co.syntax.firebasemvvm.model.Task
import il.co.syntax.firebasemvvm.ui.all_tasks.AllTasksViewModel
import il.co.syntax.firebasemvvm.ui.all_tasks.TasksAdapter
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared

class MainFragment : Fragment() {

    private var binding: MainFragmentBinding by autoCleared()

    private val viewModel: MainFragmentViewModel by viewModels {
        AllTasksViewModel.AllTaskViewModelFactory(AuthRepositoryImpl(),DogsRepositoryImpl())
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

        binding.recycler.adapter = ItemAdapter(object : ItemAdapter.ItemListener {

            override fun onTaskClicked(task: Task) {
                viewModel.setCompleted(task.id,!task.finished)
            }

            override fun onTaskLongClicked(task: Task) {
                viewModel.deleteTask(task.id)
            }
        })

        /*
        viewModel.items?.observe(viewLifecycleOwner) { items ->
            binding.recycler.adapter = ItemAdapter(items, { bundle ->
                findNavController().navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
            }, viewModel)
            //binding.recycler.adapter = ItemAdapter(ItemManager.items)


         */
        }

        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) = makeFlag(
                ItemTouchHelper.ACTION_STATE_SWIPE,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            )

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // ItemManager.remove(viewHolder.adapterPosition)

                val item =
                    (binding.recycler.adapter as ItemAdapter).itemAt(viewHolder.adapterPosition)
                viewModel.deleteItem(item)
                binding.recycler.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(binding.recycler)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}