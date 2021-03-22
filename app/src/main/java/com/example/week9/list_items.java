package com.example.week9;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class list_items extends MainActivity {
    RecyclerView recycler;



    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_items);
        recycler = findViewById(R.id.recycler);
        Intent intent = getIntent();
        ArrayList <String> leffat = intent.getStringArrayListExtra("leffat");
        setAdap(leffat);
    }

    public void setAdap(ArrayList<String> leffat) {
        recyclerAdapter adapter = new recyclerAdapter(leffat); // annetaa lista
        recycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);

    }
}
