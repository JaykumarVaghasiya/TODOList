package com.jay.tdlist.task


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.google.android.material.transition.MaterialFade
import com.jay.tdlist.R
import com.jay.tdlist.database.Task
import com.jay.tdlist.database.TaskViewModel
import com.jay.tdlist.todo.ShowToDOs
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), TaskAdapter.OnTaskClickListener {

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskRecyclerView: RecyclerView
    private var tasks = mutableListOf<Task>()
    private val taskViewModel: TaskViewModel by viewModels()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Task"


        val taskTextView: MaterialTextView? = findViewById(R.id.textViewI)
        taskRecyclerView = findViewById(R.id.taskRecyclerView)
        taskRecyclerView.layoutManager = LinearLayoutManager(this)
        taskTextView?.text = R.string.task.toString()

        taskAdapter = TaskAdapter(this, taskViewModel, this)
        taskRecyclerView.adapter = taskAdapter

        lifecycleScope.launch {
            taskViewModel.allTasks.collect { task ->
                taskAdapter.updateData(task)

            }
        }

        val addTaskButton: ExtendedFloatingActionButton = findViewById(R.id.addTaskButton)
        val nestedScrollView = findViewById<NestedScrollView>(R.id.nestedScrollView)

        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->

            if (scrollY > oldScrollY + 12 && addTaskButton.isExtended) {
                addTaskButton.shrink()
            }

            // the delay of the extension of the FAB is set for 12 items
            if (scrollY < oldScrollY - 12 && !addTaskButton.isExtended) {
                addTaskButton.extend()
            }

            // if the nestedScrollView is at the first item of the list then the
            // extended floating action should be in extended state
            if (scrollY == 0) {
                addTaskButton.extend()
            }
        })
        addTaskButton.setOnClickListener {
            showAddTaskDialog()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun showAddTaskDialog() {



        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialogue_add_task, null)
        val textInputLayout = dialogView.findViewById<TextInputLayout>(R.id.textInputLayout)
        val editTextTaskName = textInputLayout.editText

        val dialogBuilder = MaterialAlertDialogBuilder(this)
            .setView(dialogView)
            .setTitle("Add Task")
            .setPositiveButton("Add", null)
            .setNegativeButton("Back") { dialog, _ ->
                dialog.dismiss()
            }


        // Set the enter and exit transitions for the dialog

        val dialog = dialogBuilder.create()
        // Set custom click listener for positive button after creating the dialog
        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                val taskName = editTextTaskName?.text.toString().trim()

                if (taskName.isEmpty()) {
                    textInputLayout.error = "Please Enter Task"
                } else {
                    val newTask = Task(name = taskName)
                    tasks.add(newTask)
                    taskViewModel.insertTask(newTask)
                    dialog.dismiss()  // Dismiss the dialog if a valid task name is entered
                }
            }
        }

        dialog.show()
    }

    override fun onTaskClick(task: Task) {

        val intent = Intent(this@MainActivity, ShowToDOs::class.java)
        intent.putExtra("task", task.taskId)
        startActivity(intent)

    }


}