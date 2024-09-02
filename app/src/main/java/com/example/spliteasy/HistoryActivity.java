package com.example.spliteasy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.history_recyclerview);


        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<HistoryItem> historyList = dbHelper.getAllItems();
        MyAdapter adapter = new MyAdapter(this, historyList, dbHelper);
        recyclerView.setAdapter(adapter);


        // Fetch all items from the database
        List<HistoryItem> list = dbHelper.getAllItems();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(getApplicationContext(),list,dbHelper);
        recyclerView.setAdapter(adapter);
    }
}
