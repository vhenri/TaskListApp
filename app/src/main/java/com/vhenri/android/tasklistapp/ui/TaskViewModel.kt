package com.vhenri.android.tasklistapp.ui

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.vhenri.android.tasklistapp.core.lifecycle.SingleLiveEvent
import com.vhenri.android.tasklistapp.data.TaskItem
import com.vhenri.android.tasklistapp.enums.AppState
import com.vhenri.android.tasklistapp.enums.NavDestinationData
import com.vhenri.android.tasklistapp.ui.TaskDetailFragment.Companion.TASK_DETAIL_ID
import org.koin.core.component.KoinComponent
import java.util.UUID
import kotlin.collections.ArrayList

class TaskViewModel() : ViewModel(), KoinComponent {

    val appState = SingleLiveEvent<NavDestinationData>()
    val listOfTasks = SingleLiveEvent<ArrayList<TaskItem>>()

    val currentInputTaskTitle = SingleLiveEvent<String?>()
    val currentInputTaskDesc = SingleLiveEvent<String?>()
    val currentInputTaskDate = SingleLiveEvent<String?>()

    private val currentEditedTaskIndex = SingleLiveEvent<Int>()
    val currentEditedTaskData = SingleLiveEvent<TaskItem>()

    fun addTaskToList(){
        val taskList = listOfTasks.value ?: arrayListOf<TaskItem>()
        val newTask = TaskItem(
            UUID.randomUUID().toString(),
            currentInputTaskTitle.value ?: "Untitled Task",
            currentInputTaskDesc.value ?: "Description",
            currentInputTaskDate.value ?: "Date here"
        )

        resetInputValues()
        taskList.add(newTask)
        listOfTasks.postValue(taskList)
        appState.postValue(NavDestinationData(AppState.TASK_VIEW))
    }

    fun getTaskDetails(taskId: String): TaskItem? {
        val taskList = listOfTasks.value ?: arrayListOf()
        val taskIndex = taskList.indexOfFirst{ taskItem -> taskItem.id == taskId }
        val task = taskIndex.let { taskList[it] }

        currentEditedTaskData.postValue(task)
        currentEditedTaskIndex.postValue(taskIndex)

        currentInputTaskTitle.value = task.title
        currentInputTaskDesc.value = task.desc
        currentInputTaskDate.value = task.date

        return task
    }

    fun updateTask(){
        val taskList = listOfTasks.value ?: arrayListOf<TaskItem>()
        val taskIndex = currentEditedTaskIndex.value ?: -1
        var updatedTask = currentEditedTaskData.value
        updatedTask?.title = currentInputTaskTitle.value ?: ""
        updatedTask?.desc = currentInputTaskDesc.value ?: ""
        updatedTask?.date = currentInputTaskDate.value ?: ""

        if (updatedTask != null) {
            taskList[taskIndex] = updatedTask
            resetInputValues()
        }
        appState.postValue(NavDestinationData(AppState.TASK_VIEW))
    }

    fun navigateToCreateNewTaskDetail() {
        appState.postValue(NavDestinationData(AppState.TASK_DETAIL))
    }

    fun navigateToEditTaskDetail(id: String) {
        val b = Bundle()
        b.putString(TASK_DETAIL_ID, id)
        appState.postValue(NavDestinationData(AppState.TASK_DETAIL, b))
    }

    fun resetInputValues(){
        currentInputTaskTitle.value = null
        currentInputTaskDesc.value = null
        currentInputTaskDate.value = null
    }

}
