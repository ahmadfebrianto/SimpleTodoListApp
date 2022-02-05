package com.test.stechoq.ui.add

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.test.stechoq.TaskApplication
import com.test.stechoq.database.task.Task
import com.test.stechoq.databinding.TaskItemDetailBinding
import com.test.stechoq.ui.TaskViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi

class AddTaskFragment : Fragment() {
    private var _binding: TaskItemDetailBinding? = null
    private val binding get() = _binding!!

    private val addTaskViewModel: AddTaskViewModel by viewModels {
        TaskViewModelFactory((activity?.application as TaskApplication).database.taskDao())
    }

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
        setTaskNameListener()
        setCancelTaskButtonListener()
        setSaveTaskButtonListener()
        setRootLayoutListener()
    }

    @DelicateCoroutinesApi
    private fun setSaveTaskButtonListener() {
        binding.btSaveTask.setOnClickListener {
            val taskName = binding.etTaskName.text.toString()
            val taskDescription = binding.etTaskDesc.text?.toString() ?: ""
            val task = Task(
                name = taskName,
                description = taskDescription
            )
            addTaskViewModel.insertTask(task)
            it.findNavController().popBackStack()
        }
    }

    private fun setCancelTaskButtonListener() {
        binding.btCancelTask.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }


    private fun setTaskNameListener() {
        binding.etTaskName.addTextChangedListener {
            val text = binding.etTaskName.editableText
            binding.btSaveTask.isEnabled = text.isNotEmpty()
        }
    }

    // Hide keyboard when clicking outside edit text
    private fun Activity.hideSoftKeyboard() {
        (getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    private fun setRootLayoutListener() {
        binding.rootLayout.setOnClickListener {
            activity?.hideSoftKeyboard()
            binding.etTaskName.clearFocus()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}