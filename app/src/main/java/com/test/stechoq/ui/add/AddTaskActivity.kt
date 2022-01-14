package com.test.stechoq.ui.add

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.test.stechoq.R
import com.test.stechoq.databinding.ActivityAddTaskBinding
import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager


class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.add_task)

        binding.etTaskName.addTextChangedListener {
            val text = binding.etTaskName.editableText
            binding.btSaveTask.isEnabled = text.isNotEmpty()
        }

        binding.btCancelTask.setOnClickListener {
            finish()
        }

        binding.rootLayout.setOnClickListener {
            hideSoftKeyboard()
            binding.etTaskName.clearFocus()
        }

    }

    private fun Activity.hideSoftKeyboard(){
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }
}