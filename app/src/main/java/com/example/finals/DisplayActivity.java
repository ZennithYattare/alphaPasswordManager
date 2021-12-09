package com.example.finals;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

public class DisplayActivity extends AppCompatActivity {

    EditText editText_title, editText_username, editText_notes;
    FloatingActionButton floatingActionButton;
    TextInputEditText textInputEditText_password;
    String string_id, string_title, string_username, string_password, string_notes;
    ImageButton copyUsername, copyPassword, copyNotes;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        getSupportActionBar().setTitle("View Credentials");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SQLiteDatabase.loadLibs(this);
        dbHelper = new DBHelper(this);

        editText_title = findViewById(R.id.displayeditTitle);
        editText_username = findViewById(R.id.displayeditUserEmail);
        editText_notes = findViewById(R.id.displayeditNotesMultiline);
        copyUsername = findViewById(R.id.btnCopyUserEmail);
        copyPassword = findViewById(R.id.btnCopyPassword);
        copyNotes = findViewById(R.id.btnCopyNote);
        textInputEditText_password = findViewById(R.id.displayeditTextPassword);
        floatingActionButton = findViewById(R.id.floatingEditButton);

        copyUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Username/Email", editText_username.getText().toString());
                clipboardManager.setPrimaryClip(clipData);

                Snackbar.make(view, "Username/Email copied", Snackbar.LENGTH_LONG).show();
            }
        });

        copyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Password", textInputEditText_password.getText().toString());
                clipboardManager.setPrimaryClip(clipData);

                Snackbar.make(view, "Password copied", Snackbar.LENGTH_LONG).show();
            }
        });

        copyNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Notes", editText_notes.getText().toString());
                clipboardManager.setPrimaryClip(clipData);

                Snackbar.make(view, "Password copied", Snackbar.LENGTH_LONG).show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayActivity.this, UpdateActivity.class);
                intent.putExtra("updateTitle", string_title);
                intent.putExtra("updateUsername", string_username);
                intent.putExtra("updatePassword", string_password);
                intent.putExtra("updateNotes", string_notes);
                intent.putExtra("updateID", string_id);
                startActivity(intent);
                //finish();
            }
        });
        getAndSetIntentData();
        getPasswordNotes();
        /*getNotes();*/
        /*getID();*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DisplayActivity.this);
        alertDialog.setTitle("Delete " + string_title + "?");
        alertDialog.setMessage("Are you sure you want to delete " + string_title + "?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                dbHelper.deleteTuple(string_id);
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Nothing here.
            }
        });
        alertDialog.show();
    }

/*    void getNotes(){
        Cursor cursor = dbHelper.notesCursor(string_id);
        if (cursor.getCount() == 0){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DisplayActivity.this);
            alertDialog.setTitle("An error has occurred.");
            alertDialog.setMessage("No data.");
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Nothing here.
                }
            });
            alertDialog.show();
        }else{
            cursor.moveToFirst();
            editText_notes.setText(cursor.getString(0));
            string_notes = cursor.getString(0);
        }
    }*/

    void getPasswordNotes(){
        Cursor cursor = dbHelper.passwordnotesCursor(string_id);
        if (cursor.getCount() == 0){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DisplayActivity.this);
            alertDialog.setTitle("An error has occurred.");
            alertDialog.setMessage("No data.");
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Nothing here.
                }
            });
            alertDialog.show();
        }else{
            cursor.moveToFirst();
            textInputEditText_password.setText(cursor.getString(0));
            editText_notes.setText(cursor.getString(1));
            string_password = cursor.getString(0);
            string_notes = cursor.getString(1);
            Log.d("display1", string_password);
            Log.d("display1", string_notes);
        }
    }

/*    void getID(){
        Cursor cursor = dbHelper.idCursor(string_title, string_username, string_password);
        if (cursor.getCount() == 0){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DisplayActivity.this);
            alertDialog.setTitle("An error has occurred.");
            alertDialog.setMessage("No data.");
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Nothing here.
                }
            });
            alertDialog.show();
        }else{
            cursor.moveToFirst();
            string_id = cursor.getString(0);
        }
    }*/

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("username")){
            //Getting Data from intent
            string_id = getIntent().getStringExtra("id");
            string_title = getIntent().getStringExtra("title");
            string_username = getIntent().getStringExtra("username");

            //Setting intent data
            editText_title.setText(string_title);
            editText_username.setText(string_username);
        }else{
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DisplayActivity.this);
            alertDialog.setTitle("An error has occurred.");
            alertDialog.setMessage("No data.");
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Nothing here.
                }
            });
            alertDialog.show();
        }
    }
}