package com.vhenri.android.tasklistapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vhenri.android.tasklistapp.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initObservers()
    }

    private fun initObservers() {

    }

}