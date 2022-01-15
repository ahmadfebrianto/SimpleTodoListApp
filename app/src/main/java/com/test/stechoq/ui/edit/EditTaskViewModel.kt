package com.test.stechoq.ui.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.test.stechoq.database.task.Task
import com.test.stechoq.database.task.TaskDao
import kotlinx.coroutines.launch


class EditTaskViewModel(private val taskDao: TaskDao) : ViewModel() {

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskDao.updateTask(task)
        }
    }

    fun getTaskById(taskId: Int): LiveData<Task> {
        return taskDao.getTaskById(taskId).asLiveData()
    }
}