package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskListener {

    private static final int ADD_TASK_REQUEST = 1;
    private static final int EDIT_TASK_REQUEST = 2;

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private ArrayList<Task> taskList;
    private FloatingActionButton fabAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the task list
        taskList = new ArrayList<>();

        // Add some sample tasks
        taskList.add(new Task("Complete Android assignment", "Finish the RecyclerView implementation"));
        taskList.add(new Task("Buy groceries", "Milk, eggs, bread"));
        taskList.add(new Task("Call mom", "Don't forget to wish her happy birthday"));

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter
        taskAdapter = new TaskAdapter(taskList, this);
        recyclerView.setAdapter(taskAdapter);

        // Set up FAB for adding new tasks
        fabAddTask = findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, ADD_TASK_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TASK_REQUEST && resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");

            Task newTask = new Task(title, description);
            taskList.add(newTask);
            taskAdapter.notifyItemInserted(taskList.size() - 1);

            Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_TASK_REQUEST && resultCode == RESULT_OK && data != null) {
            int position = data.getIntExtra("position", -1);

            if (position != -1) {
                String title = data.getStringExtra("title");
                String description = data.getStringExtra("description");

                Task updatedTask = new Task(title, description);
                taskList.set(position, updatedTask);
                taskAdapter.notifyItemChanged(position);

                Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onTaskClick(int position) {
        Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("title", taskList.get(position).getTitle());
        intent.putExtra("description", taskList.get(position).getDescription());
        startActivityForResult(intent, EDIT_TASK_REQUEST);
    }

    @Override
    public void onDeleteClick(int position) {
        taskList.remove(position);
        taskAdapter.notifyItemRemoved(position);
        Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
    }
}
