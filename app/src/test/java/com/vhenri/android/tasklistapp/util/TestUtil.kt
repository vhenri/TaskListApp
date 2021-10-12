package com.vhenri.android.tasklistapp.util

import com.vhenri.android.tasklistapp.data.TaskItem
import com.vhenri.android.tasklistapp.enums.AppState
import com.vhenri.android.tasklistapp.enums.NavDestinationData

object TestUtil {
    fun defaultTaskItem() = TaskItem(
            "testId",
            "Untitled Task",
            null,
            null
    )
    fun updatedTaskItem() = TaskItem(
            "testId",
            "Test Task",
            "Test desc",
            null
    )
    fun navDestination_TaskDetail() = NavDestinationData(AppState.TASK_DETAIL)
    fun navDestination_TaskView() = NavDestinationData(AppState.TASK_VIEW)
    fun formattedDate() = "Date: Jan 01 2021"
}