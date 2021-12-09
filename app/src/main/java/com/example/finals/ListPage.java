package com.example.finals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import net.sqlcipher.Cursor;

import java.util.ArrayList;

public class ListPage extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    DBHelper dbHelper;
    ArrayList<String> id, title, username;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        getSupportActionBar().setTitle("Credentials");

        dbHelper = new DBHelper(ListPage.this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingAddButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListPage.this, AddActivity.class);
                startActivity(intent);

            }
        });

        id = new ArrayList<>();
        title = new ArrayList<>();
        username = new ArrayList<>();

        displayDatainArrays();

        customAdapter = new CustomAdapter(ListPage.this,this, id, title, username);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListPage.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            recreate();
        }
    }

    void displayDatainArrays(){
        Cursor cursor = dbHelper.readAllData();
        if (cursor.getCount() == 0){
            Snackbar.make(recyclerView, "No data to display.", Snackbar.LENGTH_LONG).show();
        }else{
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                title.add(cursor.getString(1));
                username.add(cursor.getString(2));
            }
        }
    }
}