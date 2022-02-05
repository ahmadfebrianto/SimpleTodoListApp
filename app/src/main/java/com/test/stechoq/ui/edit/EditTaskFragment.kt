package com.test.stechoq.ui.edit

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.stechoq.TaskApplication
import com.test.stechoq.database.task.Task
import com.test.stechoq.databinding.TaskItemDetailBinding
import com.test.stechoq.ui.TaskViewModelFactory

class EditTaskFragment : Fragment() {
    private var _binding: TaskItemDetailBinding? = null
    private val binding get() = _binding!!

    private val editTaskViewModel: EditTaskViewModel by viewModels {
        TaskViewModelFactory((activity?.application as TaskApplication).database.taskDao())
    }

    private lateinit var task: Task

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TaskItemDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val taskId = it.getInt(TASK_ID)
            editTaskViewModel.getTaskById(taskId).observe(this) { _task ->
                task = _task
                populateFields(task)
                setFieldsTextListener()
                setSaveTaskButtonListener()
                setCancelTaskButtonListener()
            }
        }
    }

    private fun setFieldsTextListener() {
        binding.etTaskName.addTextChangedListener {
            val text = binding.etTaskName.editableText
            binding.btSaveTask.isEnabled = text.isNotEmpty() && text.toString() != task.name
        }

        binding.etTaskDesc.addTextChangedListener {
            val text = binding.etTaskDesc.editableText
            binding.btSaveTask.isEnabled = text.isNotEmpty() && text.toString() != task.name
        }
    }

    // Populate edit text fields with clicked task
    private fun populateFields(task: Task) {
        binding.etTaskName.setText(task.name)
        binding.etTaskDesc.setText(task.description)

    }

    private fun setSaveTaskButtonListener() {
        binding.btSaveTask.setOnClickListener {
            task.apply {
                this.name = binding.etTaskName.text.toString()
                this.description = binding.etTaskDesc.text.toString()
                editTaskViewModel.updateTask(this)
            }
            this.findNavController().popBackStack()
        }
    }

    private fun setCancelTaskButtonListener() {
        binding.btCancelTask.setOnClickListener {
            this.findNavController().popBackStack()
        }
    }

    companion object {
        const val TASK_ID = "task_id"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}