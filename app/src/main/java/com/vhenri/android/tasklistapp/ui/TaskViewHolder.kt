package com.vhenri.android.tasklistapp.ui

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.vhenri.android.tasklistapp.data.TaskItem
import com.vhenri.android.tasklistapp.databinding.RowTaskItemBinding
import com.vhenri.android.tasklistapp.utils.getFormattedDate

class TaskViewHolder(var binding: RowTaskItemBinding): RecyclerView.ViewHolder(binding.root){
    var onItemClick: ((String) -> Unit)? = null

    fun bindData(item: TaskItem) {
        binding.taskItemTitle.text = item.title
        if (item.desc.isNullOrEmpty()){
            binding.taskItemDescription.isVisible = false
        } else {
            binding.taskItemDescription.text = item.desc
        }
        val date = item.date
        if (date != null) {
            val text = "Date: ${getFormattedDate(date.year, date.month, date.day)}"
            binding.taskItemDate.text = text
        }
        itemView.setOnClickListener {
            onItemClick?.invoke(item.id)
        }
    }
}