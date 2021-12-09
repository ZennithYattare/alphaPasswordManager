package com.example.finals;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import net.sqlcipher.database.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {

    Button verifyBtn;
    TextInputEditText pin;
    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Verify PIN title
        getSupportActionBar().setTitle("Verify PIN");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        SQLiteDatabase.loadLibs(this);

        verifyBtn = (Button) findViewById(R.id.btnUnlock);
        pin = (TextInputEditText) findViewById(R.id.editTextPassword1);

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PIN = pin.getText().toString();
                Boolean insert = dbHelper.verifyPIN(PIN);
                if (insert == true){
                    Intent intent = new Intent(MainActivity.this, ListPage.class);
                    startActivity(intent);
                    finish();
                    //Can't even see the Snackbar because of the keypad...
                    //Snackbar.make(view, "Verification successful.", Snackbar.LENGTH_LONG).show();
                }
                else{
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                    alertDialog.setTitle("An error has occurred.");
                    alertDialog.setMessage("Invalid PIN, please try again.");
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Nothing here.
                        }
                    });
                    alertDialog.show();
                }
            }
        });
        pin.setOnEditorActionListener(editorActionListener);
    }
    private TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_DONE) {
                String PIN = pin.getText().toString();
                Boolean insert = dbHelper.verifyPIN(PIN);
                if (insert == true){
                    Intent intent = new Intent(MainActivity.this, ListPage.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                    alertDialog.setTitle("An error has occurred.");
                    alertDialog.setMessage("Invalid PIN, please try again.");
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Nothing here.
                        }
                    });
                    alertDialog.show();
                }
            }
            return false;
        }
    };
}