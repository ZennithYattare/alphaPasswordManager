package com.example.finals;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import net.sqlcipher.database.SQLiteDatabase;

public class RegisterPage extends AppCompatActivity {

    Button createBtn;
    TextInputEditText pin;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        getSupportActionBar().setTitle("Create PIN");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        SQLiteDatabase.loadLibs(this);
        dbHelper = new DBHelper(this);

        createBtn = (Button) findViewById(R.id.regbtnUnlock);
        pin = (TextInputEditText) findViewById(R.id.regeditTextPassword1);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PIN = pin.getText().toString().trim();
                Log.d("Reg", PIN);
                if (PIN != "") {
                    Boolean insert = dbHelper.insertPIN(PIN);
                    if (insert == true) {
                        Intent intent = new Intent(RegisterPage.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        //Can't even see the Snackbar because of the keypad...
                        //Snackbar.make(view, "Successfully registered.", Snackbar.LENGTH_LONG).show();
                    }
                }else{
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegisterPage.this);
                    alertDialog.setTitle("An error has occurred");
                    alertDialog.setMessage("PIN is required");
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