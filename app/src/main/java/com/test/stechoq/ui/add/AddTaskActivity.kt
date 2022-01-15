package com.test.stechoq.ui.add

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.test.stechoq.R
import com.test.stechoq.TaskApplication
import com.test.stechoq.database.task.Task
import com.test.stechoq.databinding.TaskItemDetailBinding
import com.test.stechoq.ui.TaskViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi


class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: TaskItemDetailBinding
    private val addTaskViewModel: AddTaskViewModel by viewModels {
        TaskViewModelFactory((this.application as TaskApplication).database.taskDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TaskItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.add_task)

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
            finish()
        }
    }

    private fun setCancelTaskButtonListener() {
        binding.btCancelTask.setOnClickListener {
            finish()
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
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    private fun setRootLayoutListener() {
        binding.rootLayout.setOnClickListener {
            hideSoftKeyboard()
            binding.etTaskName.clearFocus()
        }
    }
}