package com.test.stechoq

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.stechoq.database.DataSource
import com.test.stechoq.databinding.ActivityMainBinding
import com.test.stechoq.ui.list.TaskListAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val taskList = DataSource.loadDummyTask()
        binding.rvTaskList.adapter = TaskListAdapter(taskList)
        binding.rvTaskList.setHasFixedSize(true)

    }
}