package com.test.stechoq.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.stechoq.database.task.TaskDao
import com.test.stechoq.ui.add.AddTaskViewModel
import com.test.stechoq.ui.edit.EditTaskViewModel
import com.test.stechoq.ui.list.TaskListViewModel

class TaskViewModelFactory(private val taskDao: TaskDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskListViewModel(taskDao) as T
        }

        if (modelClass.isAssignableFrom(AddTaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddTaskViewModel(taskDao) as T
        }

        if (modelClass.isAssignableFrom(EditTaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditTaskViewModel(taskDao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }
}