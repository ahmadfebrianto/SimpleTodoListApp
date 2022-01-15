package com.test.stechoq.ui.list

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.stechoq.database.task.Task
import com.test.stechoq.databinding.TaskItemBinding
import com.test.stechoq.ui.edit.EditTaskActivity
import com.test.stechoq.ui.edit.EditTaskActivity.Companion.TASK_ID

class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder>() {

    private val taskList = arrayListOf<Task>()

    class TaskListViewHolder(private val binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var getTask: Task

        fun bind(task: Task) {
            getTask = task
            with(binding) {
                tvTaskName.text = task.name
                tvTaskDescription.text = task.description
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, EditTaskActivity::class.java)
                    intent.putExtra(TASK_ID, task.id)
                    itemView.context.startActivity(intent)
                }

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

    fun submitList(newList: List<Task>) {
        taskList.clear()
        taskList.addAll(newList)
//        notifyItemInserted(itemCount)
        notifyDataSetChanged()
    }
}