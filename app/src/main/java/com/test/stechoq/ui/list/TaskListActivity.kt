package com.test.stechoq.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.test.stechoq.TaskApplication
import com.test.stechoq.databinding.ActivityTaskListBinding
import com.test.stechoq.ui.TaskViewModelFactory
import com.test.stechoq.ui.add.AddTaskActivity

class TaskListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskListBinding
    private val taskListViewModel: TaskListViewModel by viewModels {
        TaskViewModelFactory((this.application as TaskApplication).database.taskDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAction()

        val taskListAdapter = TaskListAdapter()
        val recyclerView = binding.rvTaskList
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskListAdapter
        recyclerView.setHasFixedSize(true)

        // Add line separator between task item
        val itemDecor = DividerItemDecoration(this, VERTICAL)
        recyclerView.addItemDecoration(itemDecor)

        // Observe task list change
        taskListViewModel.taskList.observe(this) { taskList ->
            taskList?.let {
                taskListAdapter.submitList(it)
            }
        }

        binding.fabAddTask.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }
    }

    // Delete task item on swipe
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
                taskListViewModel.deleteTask(task)
                showToast("Task deleted")
            }

        })
        itemTouchHelper.attachToRecyclerView(binding.rvTaskList)
    }

    private fun showToast(msg: String) {
        val toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, 0, 300)
        toast.show()
    }

}