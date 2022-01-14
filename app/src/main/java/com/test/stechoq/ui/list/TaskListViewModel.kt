package com.test.stechoq.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.stechoq.database.task.Task
import com.test.stechoq.database.task.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskListViewModel(private val taskDao: TaskDao) : ViewModel() {

    private val _taskList = MutableLiveData<List<Task>>()
    fun getAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            _taskList.postValue(taskDao.getAllTasks())
        }
    }

    val taskList: LiveData<List<Task>> = _taskList


}