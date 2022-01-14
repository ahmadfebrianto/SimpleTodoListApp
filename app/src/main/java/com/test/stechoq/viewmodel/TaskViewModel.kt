package com.test.stechoq.viewmodel

import androidx.lifecycle.ViewModel
import com.test.stechoq.database.task.Task
import com.test.stechoq.database.task.TaskDao

class TaskViewModel(private val taskDao: TaskDao) : ViewModel() {
    fun getAllTasks(): List<Task> {
        return taskDao.getAllTasks()
    }

    fun insertTask(task: Task) {
        return taskDao.insertTask(task)
    }

    fun updateTask(task: Task) {
        return taskDao.updateTask(task)
    }

    fun deleteTask(task: Task) {
        return taskDao.deleteTask(task)
    }
}