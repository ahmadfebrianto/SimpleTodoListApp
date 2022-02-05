package com.test.stechoq.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.stechoq.TaskApplication
import com.test.stechoq.databinding.FragmentTaskListBinding
import com.test.stechoq.ui.TaskViewModelFactory

class TaskListFragment : Fragment() {
    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView

    private val taskListViewModel: TaskListViewModel by activityViewModels {
        TaskViewModelFactory((activity?.application as TaskApplication).database.taskDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAction()

        val taskListAdapter = TaskListAdapter()
        recyclerView = binding.rvTaskList
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = taskListAdapter
        recyclerView.setHasFixedSize(true)

        // Add line separator between task item
        val itemDecor = DividerItemDecoration(context, RecyclerView.VERTICAL)
        recyclerView.addItemDecoration(itemDecor)

        // Observe task list change
        taskListViewModel.taskList.observe(this) { taskList ->
            taskList?.let {
                taskListAdapter.submitList(it)
            }
        }

        binding.fabAddTask.setOnClickListener {
            val action = TaskListFragmentDirections.actionTaskListFragmentToAddTaskFragment()
            it.findNavController().navigate(action)
        }
    }

    // Delete task item on swipe
    private fun initAction() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.RIGHT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val task = (viewHolder as TaskListAdapter.TaskListViewHolder).getTask
                taskListViewModel.deleteTask(task)
                showToast("Task deleted")
            }

        })
        itemTouchHelper.attachToRecyclerView(binding.rvTaskList)
    }

    private fun showToast(msg: String) {
        val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, 0, 300)
        toast.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}