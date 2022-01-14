package com.test.stechoq.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.test.stechoq.R
import com.test.stechoq.TaskApplication
import com.test.stechoq.databinding.ActivityTaskListBinding
import com.test.stechoq.ui.add.AddTaskActivity
import com.test.stechoq.ui.TaskViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskListBinding
    private val listViewModel: TaskListViewModel by viewModels {
        TaskViewModelFactory((this.application as TaskApplication).database.taskDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val taskListAdapter = TaskListAdapter()
        binding.rvTaskList.setHasFixedSize(true)
        val itemDecor = DividerItemDecoration(this, VERTICAL)
        binding.rvTaskList.addItemDecoration(itemDecor)

        listViewModel.getAllTasks()
        listViewModel.taskList.observe(this, {
            taskListAdapter.submitList(listViewModel.taskList.value as ArrayList)
            binding.rvTaskList.adapter = taskListAdapter
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_add_task -> {
                startActivity(Intent(this, AddTaskActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        listViewModel.getAllTasks()
    }

}