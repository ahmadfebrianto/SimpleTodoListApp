package com.test.stechoq.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.stechoq.database.task.Task
import com.test.stechoq.databinding.TaskItemBinding

class TaskListAdapter(
    private val taskList: List<Task>
) :
    RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder>() {
    class TaskListViewHolder(private val binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            with(binding) {
                this.tvTaskName.text = task.name
                this.tvTaskDescription.text = task.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val binding = TaskItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TaskListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val task = taskList[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}