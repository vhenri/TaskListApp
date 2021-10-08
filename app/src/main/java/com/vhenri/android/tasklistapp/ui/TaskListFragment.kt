package com.vhenri.android.tasklistapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.vhenri.android.tasklistapp.R
import com.vhenri.android.tasklistapp.databinding.FragmentTaskListBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TaskListFragment : Fragment() {
    private val viewModel: TaskViewModel by sharedViewModel()
    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private val adapter = TaskListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBindings()
        initObservers()
    }

    private fun initObservers() {
        viewModel.appState.observe(this, Observer {
            NavHostFragment.findNavController(this).navigate(
                R.id.action_taskListFragment_to_taskDetailFragment,
                it.bundle
            )
        })
    }
    private fun initBindings() {
        binding.taskListRv.adapter = adapter
        viewModel.listOfTasks.value?.let { adapter.setData(it) }
        adapter.onItemClick = { id -> handleItemClick(id) }

        binding.floatingActionButton.setOnClickListener {
            viewModel.navigateToCreateNewTaskDetail()
        }
    }

    private fun handleItemClick(id: String) {
        viewModel.getTaskDetails(id)
        viewModel.navigateToEditTaskDetail(id)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}