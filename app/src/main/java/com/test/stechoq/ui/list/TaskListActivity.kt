package com.test.stechoq.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.stechoq.database.DataSource
import com.test.stechoq.databinding.ActivityTaskListBinding

class TaskListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val taskList = DataSource.loadDummyTask()
        binding.rvTaskList.adapter = TaskListAdapter(taskList)
        binding.rvTaskList.setHasFixedSize(true)

    }
}