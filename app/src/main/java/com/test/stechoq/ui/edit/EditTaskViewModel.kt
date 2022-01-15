package com.test.stechoq.ui.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.stechoq.database.task.Task
import com.test.stechoq.database.task.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EditTaskViewModel(private val taskDao: TaskDao) : ViewModel() {

    private val _task = MutableLiveData<Task>()
    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.updateTask(task)
        }
    }

    fun setTaskId(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _task.postValue(taskDao.getTaskById(taskId))
        }
    }

    val task: LiveData<Task> = _task
}