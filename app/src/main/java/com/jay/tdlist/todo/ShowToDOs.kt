package com.jay.tdlist.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.jay.tdlist.R
import com.jay.tdlist.database.ToDo
import com.jay.tdlist.database.ToDoViewModel
import kotlinx.coroutines.launch

class ShowToDOs : AppCompatActivity() {
    private lateinit var todoRecyclerView: RecyclerView
    private lateinit var todoAdapter: ToDoAdapter
    private var todos = mutableListOf<ToDo>()
    private val toDoViewModel: ToDoViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_to_dos)
        supportActionBar?.title = "TO-DOs"

        todoRecyclerView = findViewById(R.id.recyclerViewToDo)
        val addToDoButton: ExtendedFloatingActionButton = findViewById(R.id.fabAddToDo)

        todoRecyclerView.layoutManager = LinearLayoutManager(this)

        todoAdapter = ToDoAdapter(toDoViewModel, this)
        todoRecyclerView.adapter = todoAdapter

        val taskId = intent.getIntExtra("task", 0)


        lifecycleScope.launch {
            toDoViewModel.getAllToDosForTask(taskId).collect { toDos ->
                todoAdapter.updateToDo(toDos)
            }
        }
        val nestedScrollView = findViewById<NestedScrollView>(R.id.nestedScrollView)

        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->

            if (scrollY > oldScrollY + 12 && addToDoButton.isExtended) {
                addToDoButton.shrink()
            }

            if (scrollY < oldScrollY - 12 && !addToDoButton.isExtended) {
                addToDoButton.extend()
            }
            if (scrollY == 0) {
                addToDoButton.extend()
            }
        })
        addToDoButton.setOnClickListener {
            showAddToDoDialog()
        }

    }


    private fun showAddToDoDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialogue_add_to_dos, null)
        val tILName = dialogView.findViewById<TextInputLayout>(R.id.toDoInputLayout)
        val editTextToDoTitle = tILName.editText
        val editTextToDoSubtitle = dialogView.findViewById<EditText>(R.id.etmSubtitle)
        val prioritySpinner = dialogView.findViewById<Spinner>(R.id.spPriority)

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Add ToDo")
            .setPositiveButton("Submit", null)


            .setNegativeButton("Back") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = dialogBuilder.create()

        val priorityOptions = arrayOf("High", "Medium", "Low")

        val priorityAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, priorityOptions)
            prioritySpinner.adapter = priorityAdapter
         dialog.setOnShowListener {

            val positiveButton = dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                val title = editTextToDoTitle?.text.toString().trim()
                val taskId = intent.getIntExtra("task", 0)
                val subtitle = editTextToDoSubtitle?.text.toString().trim()
                val selectedPriority = prioritySpinner.selectedItem.toString()
                val priority = getPriorityValue(selectedPriority)

                if (title.isEmpty()) {
                    tILName.error = "Please Enter Name"
                }
                else {
                    val newToDo = ToDo(
                        fkTaskId = taskId,
                        toDoName = title,
                        subtitle = subtitle,
                        priority = priority,
                    )
                    todos.add(newToDo)
                    toDoViewModel.insertToDo(newToDo, taskId)
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }
        private fun getPriorityValue(prioritySpinner: String): Int {
            return when (prioritySpinner) {
                "High" -> 3
                "Medium" -> 2
                "Low" -> 1
                else -> 0
            }
        }

}


