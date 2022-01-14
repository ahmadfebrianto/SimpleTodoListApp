package com.test.stechoq.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.test.stechoq.R
import com.test.stechoq.database.DataSource
import com.test.stechoq.databinding.ActivityTaskListBinding
import com.test.stechoq.ui.add.AddTaskActivity

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

}