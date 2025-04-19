package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditTaskActivity extends AppCompatActivity {

    private EditText etTaskTitle, etTaskDescription;
    private Button btnUpdateTask;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        etTaskTitle = findViewById(R.id.etTaskTitle);
        etTaskDescription = findViewById(R.id.etTaskDescription);
        btnUpdateTask = findViewById(R.id.btnUpdateTask);

        Intent intent = getIntent();
        if (intent != null) {
            position = intent.getIntExtra("position", -1);
            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");

            etTaskTitle.setText(title);
            etTaskDescription.setText(description);
        }

        btnUpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTask();
            }
        });
    }

    private void updateTask() {
        String title = etTaskTitle.getText().toString().trim();
        String description = etTaskDescription.getText().toString().trim();

        if (title.isEmpty()) {
            etTaskTitle.setError("Title is required");
            etTaskTitle.requestFocus();
            return;
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("position", position);
        resultIntent.putExtra("title", title);
        resultIntent.putExtra("description", description);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
