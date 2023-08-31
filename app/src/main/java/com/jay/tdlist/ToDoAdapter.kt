package com.jay.tdlist


import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jay.tdlist.database.ToDo
import com.jay.tdlist.database.ToDoViewModel


class ToDoAdapter(private val toDoViewModel: ToDoViewModel,val context: Context):
RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>(){
    private var todos= mutableListOf<ToDo>()
    inner class ToDoViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
        private val todoNameTextView:TextView=itemView.findViewById(R.id.tvToDoName)
        private val todoSubtitleTextView:TextView=itemView.findViewById(R.id.tvSubtitle)
        private val todoPriorityTextView:TextView=itemView.findViewById(R.id.tvPriority)
        private val delete:Button=itemView.findViewById(R.id.btDelete)


        fun bind(todo:ToDo){
            todoNameTextView.text=todo.toDoName
            todoSubtitleTextView.text=todo.subtitle

            val priorityString = when (todo.priority) {
                3 -> "High"
                2 -> "Medium"
                1 -> "Low"
                else -> "Unknown"
            }
            todoPriorityTextView.text = priorityString

            delete.setOnClickListener {
                val name=todo.toDoName
                val builder= AlertDialog.Builder(context)
                builder.setMessage("Are you want to delete $name?")
                    .setCancelable(false)
                    .setPositiveButton("Yes"){ _, _ ->
                        toDoViewModel.deleteToDo(todo)
                    }
                    .setNegativeButton("No"){dialog,_ ->
                        dialog.dismiss()
                    }
                val alert=builder.create()
                alert.show()
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val view=
            LayoutInflater.from(parent.context).inflate(R.layout.item_to_do,parent,false)
        return ToDoViewHolder(view)

    }

    override fun onBindViewHolder(holder: ToDoAdapter.ToDoViewHolder, position: Int) {
        val todo=todos[position]
        holder.bind(todo)

    }

    override fun getItemCount(): Int =todos.size

    fun updateToDo(newToDos: List<ToDo>) {
        todos.clear()
        todos.addAll(newToDos)
        notifyDataSetChanged()

    }


}
