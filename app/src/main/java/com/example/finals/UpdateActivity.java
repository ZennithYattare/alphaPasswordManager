package com.example.finals;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import net.sqlcipher.database.SQLiteDatabase;

public class UpdateActivity extends AppCompatActivity {

    EditText updateTitle, updateUsername, updateNotes;
    TextInputEditText updatePassword;
    FloatingActionButton updateSave;
    String id, title, username, password, notes;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        getSupportActionBar().setTitle("Edit Credentials");

        SQLiteDatabase.loadLibs(this);
        dbHelper = new DBHelper(this);

        updateTitle = findViewById(R.id.updateeditTitle);
        updateUsername = findViewById(R.id.updateeditUserEmail);
        updatePassword = findViewById(R.id.updateeditTextPassword);
        updateNotes = findViewById(R.id.updateeditNotesMultiline);
        updateSave = findViewById(R.id.floatingUpdateButton);

        id = getIntent().getStringExtra("updateID");
        title = getIntent().getStringExtra("updateTitle");
        username = getIntent().getStringExtra("updateUsername");
        password = getIntent().getStringExtra("updatePassword");
        notes = getIntent().getStringExtra("updateNotes");

        updateTitle.setText(title);
        updateUsername.setText(username);
        updatePassword.setText(password);
        updateNotes.setText(notes);

        Log.d("Update", id);
        Log.d("Update", String.valueOf(updateTitle));
        Log.d("Update", String.valueOf(updateUsername));
        Log.d("Update", String.valueOf(updatePassword));
        Log.d("Update", String.valueOf(updateNotes));

        updateSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = updateTitle.getText().toString().trim();
                username = updateUsername.getText().toString().trim();
                password = updatePassword.getText().toString().trim();
                notes = updateNotes.getText().toString().trim();
                dbHelper.updateData(id, title, username, password, notes);
                Intent intent = new Intent(UpdateActivity.this, ListPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}