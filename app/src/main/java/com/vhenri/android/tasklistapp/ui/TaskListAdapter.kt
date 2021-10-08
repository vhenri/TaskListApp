package com.vhenri.android.tasklistapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vhenri.android.tasklistapp.data.TaskItem
import com.vhenri.android.tasklistapp.databinding.RowTaskItemBinding

class TaskListAdapter : RecyclerView.Adapter<TaskViewHolder>() {

    var dataList = ArrayList<TaskItem>()
    var onItemClick: ((String) -> Unit)? = null

    fun setData(data: ArrayList<TaskItem>) {
        this.dataList = data
        notifyDataSetChanged()
    }

    fun updateData(data: ArrayList<TaskItem>) {
        data.let { this.dataList.addAll(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val taskViewBinding = RowTaskItemBinding.inflate(layoutInflater, parent, false)
        val holder = TaskViewHolder(taskViewBinding)
        holder.onItemClick = onItemClick
        return holder
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val data = dataList[position]
        holder.bindData(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}