package com.vhenri.android.tasklistapp.data

data class TaskItem(
    val id: String,
    var title: String,
    var desc: String?,
    var date: TaskDate?
)

data class TaskDate (
    var year: Int,
    var month: Int,
    var day: Int
)
