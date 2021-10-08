package com.vhenri.android.tasklistapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.vhenri.android.tasklistapp.databinding.FragmentTaskDetailBinding
import com.vhenri.android.tasklistapp.enums.AppState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TaskDetailFragment : Fragment() {

    companion object {
        const val TASK_DETAIL_ID = "taskDetailId"
    }
    private val viewModel: TaskViewModel by sharedViewModel()

    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!

    private var isEditMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val taskId = arguments?.getString(TASK_DETAIL_ID, null)
        if (taskId != null){
            isEditMode = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskDetailBinding.inflate(LayoutInflater.from(context))
        initTextInputValues()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initOnClick()
    }

    private fun initObservers() {
        // Handle Navigation
        viewModel.appState.observe(this, Observer {
            if (it.appState == AppState.TASK_VIEW){
                NavHostFragment.findNavController(this).popBackStack()
            }
        })

        handleInputs()
    }

    private fun initOnClick() {
        binding.saveButton.setOnClickListener {
            if (isEditMode) {
                viewModel.updateTask()
            } else {
                viewModel.addTaskToList()
            }
        }
    }

    private fun initTextInputValues() {
        if (isEditMode && viewModel.currentEditedTaskData.value != null){
            val taskData = viewModel.currentEditedTaskData.value
            binding.titleEditText.setText(taskData?.title)
            binding.descEditText.setText(taskData?.desc)
            binding.dateEditText.setText(taskData?.date)
        }
    }

    private fun handleInputs() {
        // Handle form inputs
        binding.titleEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Fires right as the text is being changed (even supplies the range of text)
                viewModel.currentInputTaskTitle.value = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Fires right before text is changing
            }

            override fun afterTextChanged(s: Editable) {
                // Fires right after the text has changed
            }
        })

        binding.descEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Fires right as the text is being changed (even supplies the range of text)
                viewModel.currentInputTaskDesc.value = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Fires right before text is changing
            }

            override fun afterTextChanged(s: Editable) {
                // Fires right after the text has changed
            }
        })

        binding.dateEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Fires right as the text is being changed (even supplies the range of text)
                viewModel.currentInputTaskDate.value = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Fires right before text is changing
            }

            override fun afterTextChanged(s: Editable) {
                // Fires right after the text has changed
            }
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}