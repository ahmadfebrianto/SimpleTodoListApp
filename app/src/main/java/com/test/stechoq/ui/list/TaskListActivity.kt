package com.test.stechoq.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.test.stechoq.R
import com.test.stechoq.TaskApplication
import com.test.stechoq.databinding.ActivityTaskListBinding
import com.test.stechoq.ui.TaskViewModelFactory
import com.test.stechoq.ui.add.AddTaskActivity

class TaskListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskListBinding
    private val listViewModel: TaskListViewModel by viewModels {
        TaskViewModelFactory((this.application as TaskApplication).database.taskDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAction()

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

    private fun initAction() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.RIGHT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val task = (viewHolder as TaskListAdapter.TaskListViewHolder).getTask
                listViewModel.deleteTask(task)
                Toast.makeText(applicationContext, "Task deleted", Toast.LENGTH_LONG).show()
            }

        })
        itemTouchHelper.attachToRecyclerView(binding.rvTaskList)
    }

}