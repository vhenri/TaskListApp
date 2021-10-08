package com.vhenri.android.tasklistapp.ui

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.vhenri.android.tasklistapp.data.TaskItem
import com.vhenri.android.tasklistapp.databinding.RowTaskItemBinding

class TaskViewHolder(var binding: RowTaskItemBinding): RecyclerView.ViewHolder(binding.root){
    var onItemClick: ((String) -> Unit)? = null

    fun bindData(item: TaskItem) {
        binding.taskItemTitle.text = item.title
        if (item.desc.isNullOrEmpty()){
            binding.taskItemDescription.isVisible = false
        } else {
            binding.taskItemDescription.text = item.desc
        }
        binding.taskItemDate.text = "Date: " + item.date
        itemView.setOnClickListener {
            onItemClick?.invoke(item.id)
        }
    }
}