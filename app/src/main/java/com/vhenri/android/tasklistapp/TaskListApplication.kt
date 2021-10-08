package com.vhenri.android.tasklistapp

import android.app.Application
import com.vhenri.android.tasklistapp.ui.TaskViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class TaskListApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TaskListApplication)
            modules(appModules)
        }
    }
}

val appModules = module {
    viewModel { TaskViewModel() }
}