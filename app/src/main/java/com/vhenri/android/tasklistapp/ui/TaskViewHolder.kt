package com.vhenri.android.tasklistapp.ui

import android.view.View
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
            binding.taskItemDescription.visibility = View.GONE
        } else {
            binding.taskItemDescription.visibility = View.VISIBLE
            binding.taskItemDescription.text = item.desc
        }
        val date = item.date
        if (date != null) {
            val text = "Date: ${getFormattedDate(date.year, date.month, date.day)}"
            binding.taskItemDate.visibility = View.VISIBLE
            binding.taskItemDate.text = text
        } else {
            binding.taskItemDate.visibility = View.GONE
        }
        itemView.setOnClickListener {
            onItemClick?.invoke(item.id)
        }
    }
}