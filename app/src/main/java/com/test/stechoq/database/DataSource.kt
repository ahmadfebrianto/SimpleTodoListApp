package com.test.stechoq.database

import com.test.stechoq.database.entity.Task

object DataSource {

    fun loadDummyTask(): List<Task> {
        val listOfTasks = arrayListOf<Task>()

        listOfTasks.add(
            Task(
                id = 1,
                name = "Task 1",
                description = "Description of Task 1"
            )
        )

        listOfTasks.add(
            Task(
                id = 2,
                name = "Task 2",
                description = "Description of Task 2"
            )
        )

        return listOfTasks
    }
}