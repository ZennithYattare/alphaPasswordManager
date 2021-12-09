package com.example.finals;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import net.sqlcipher.database.SQLiteDatabase;

public class AddActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    EditText title_input, username_input, notes_input;
    TextInputEditText password_input;
    String titlecheck;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        getSupportActionBar().setTitle("Store Credentials");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SQLiteDatabase.loadLibs(this);
        dbHelper = new DBHelper(this);

        title_input = findViewById(R.id.editTitle);
        username_input = findViewById(R.id.editUserEmail);
        notes_input = findViewById(R.id.editNotesMultiline);
        password_input = findViewById(R.id.editTextPassword);

        titlecheck = title_input.getText().toString();
        Log.d("title", titlecheck + "1");

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingSaveButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titlecheck.trim() != "") {
                    Boolean insert = dbHelper.insertData(title_input.getText().toString(), username_input.getText().toString().trim(), password_input.getText().toString().trim(), notes_input.getText().toString());
                    if (insert == true) {
                        Intent intent = new Intent(AddActivity.this, ListPage.class);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddActivity.this);
                    alertDialog.setTitle("An error has occurred");
                    alertDialog.setMessage("Title field is required");
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                           //Nothing here.
                        }
                    });
                }
            }
        });
    }
}