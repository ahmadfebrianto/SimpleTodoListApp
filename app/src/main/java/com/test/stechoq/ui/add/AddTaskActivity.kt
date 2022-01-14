package com.test.stechoq.ui.add

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.stechoq.databinding.ActivityAddTaskBinding

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btCancelTask.setOnClickListener {
            finish()
        }
    }
}