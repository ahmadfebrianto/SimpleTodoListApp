package com.test.stechoq.ui.edit

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.test.stechoq.R
import com.test.stechoq.TaskApplication
import com.test.stechoq.database.task.Task
import com.test.stechoq.databinding.TaskItemDetailBinding
import com.test.stechoq.ui.TaskViewModelFactory

class EditTaskActivity : AppCompatActivity() {

    private lateinit var binding: TaskItemDetailBinding
    private val editTaskViewModel: EditTaskViewModel by viewModels {
        TaskViewModelFactory((this.application as TaskApplication).database.taskDao())
    }

    private lateinit var task: Task

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TaskItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.edit_task)

        val extras = intent.extras
        if (null != extras) {
            val taskId = extras.getInt(TASK_ID)
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
            finish()
        }
    }

    private fun setCancelTaskButtonListener() {
        binding.btCancelTask.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val TASK_ID = "task_id"
    }
}