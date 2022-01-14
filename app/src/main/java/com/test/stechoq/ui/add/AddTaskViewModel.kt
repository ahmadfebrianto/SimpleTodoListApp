package com.test.stechoq.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.stechoq.database.task.Task
import com.test.stechoq.database.task.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTaskViewModel(private val taskDao: TaskDao): ViewModel() {
    fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.insertTask(task)
        }
    }

    fun updateTask(task: Task) {
        return taskDao.updateTask(task)
    }

    fun deleteTask(task: Task) {
        return taskDao.deleteTask(task)
    }
}