package com.jay.tdlist

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jay.tdlist.database.Task
import com.jay.tdlist.database.TaskViewModel


class TaskAdapter(private val listener:OnTaskClickListener,private val taskViewModel: TaskViewModel, val context: Context):RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
        private var tasks= mutableListOf<Task>()

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewI: TextView = itemView.findViewById(R.id.textViewI)!!
        private val delete:Button=itemView.findViewById(R.id.taskDelete)
        fun bind(task: Task) {
            textViewI.text = task.name

            delete.setOnClickListener {
                val name=task.name
                val builder=AlertDialog.Builder(context)
                builder.setMessage("Are you want to delete $name?")
                    .setCancelable(false)
                    .setPositiveButton("Yes"){ _, _ ->
                        taskViewModel.deleteTask(task)
                    }
                    .setNegativeButton("No"){dialog,_ ->
                        dialog.dismiss()
                    }
                val alert=builder.create()
                alert.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskAdapter.TaskViewHolder, position: Int) {
        holder.bind(tasks[position])

        holder.itemView.setOnClickListener {
            listener.onTaskClick(tasks[position])
        }
    }

    override fun getItemCount(): Int = tasks.size
    fun updateData(newTasks: List<Task>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }

    interface OnTaskClickListener {
        fun onTaskClick(task: Task)
    }
}


