package com.test.stechoq.ui.edit

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.test.stechoq.R
import com.test.stechoq.TaskApplication
import com.test.stechoq.databinding.TaskItemDetailBinding
import com.test.stechoq.ui.TaskViewModelFactory

class EditTaskActivity : AppCompatActivity() {

    private lateinit var binding: TaskItemDetailBinding
    private val editTaskViewModel: EditTaskViewModel by viewModels {
        TaskViewModelFactory((this.application as TaskApplication).database.taskDao())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TaskItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.edit_task)

        setSaveTaskButtonListener()
        setCancelTaskButtonListener()

        binding.btSaveTask.isEnabled = true

        val extras = intent.extras
        if (null != extras) {
            val taskId = extras.getInt(TASK_ID)
            editTaskViewModel.setTaskId(taskId)
            editTaskViewModel.task.observe(this, {
                if (it != null) {
                    binding.etTaskName.setText(it.name)
                    binding.etTaskDesc.setText(it.description)
                }
            })
        }

    }

    private fun setSaveTaskButtonListener() {
        binding.btSaveTask.setOnClickListener {
            val task = editTaskViewModel.task.value
            task?.apply {
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