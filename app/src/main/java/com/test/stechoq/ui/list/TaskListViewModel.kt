package com.test.stechoq.ui.list

import androidx.lifecycle.*
import com.test.stechoq.database.task.Task
import com.test.stechoq.database.task.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskListViewModel(private val taskDao: TaskDao) : ViewModel() {

    val taskList : LiveData<List<Task>> = taskDao.getAllTasks().asLiveData()

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }
}