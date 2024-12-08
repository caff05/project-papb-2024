package com.example.fragmentpractice.lokerbarang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragmentpractice.R;
import com.example.fragmentpractice.database.DatabaseListener;
import com.example.fragmentpractice.database.RealtimeDatabase;
import com.example.fragmentpractice.listloker.Loker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    RealtimeDatabase db;
    RecyclerView rvBarang;
    FloatingActionButton fabAdd;
    ItemAdapter adapter;
    ArrayList<Item> items = new ArrayList<>();
    Loker loker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new RealtimeDatabase();

        setContentView(R.layout.home_page);
        initializeComponents();
        initializeRecyclerView();
        Intent intentData = getIntent();
        loker = new Loker(
                intentData.getStringExtra("id"),
                intentData.getStringExtra("nama"),
                intentData.getStringExtra("status"),
                intentData.getIntExtra("capacity",0),
                intentData.getDoubleExtra("price", 0),
                intentData.getStringExtra("location")
        );

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                intent.putExtra("id", loker.getId());
                intent.putExtra("nama", loker.getNama());
                intent.putExtra("status", loker.getStatus());
                intent.putExtra("capacity", loker.getCapacity());
                intent.putExtra("price", loker.getPrice());
                intent.putExtra("location", loker.getLocation());
                startActivity(intent);
            }
        });


    }

    private void listenData(String child) {
        db.listenData(child,new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Item item = snapshot1.getValue(Item.class);
                    items.add(item);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity2.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeRecyclerView() {
        adapter = new ItemAdapter(items);
        rvBarang.setAdapter(adapter);
        rvBarang.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter.setDeleteClickListener(new RecyclerViewClickListener() {
            @Override
            public void onClickDelete(Item data) {
                db.deleteItem(loker.getId(),data, new DatabaseListener() {
                    @Override
                    public void onSuccess(Void a) {
                        listenData(loker.getId());
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(MainActivity2.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initializeComponents() {
        rvBarang = findViewById(R.id.rv_list_barang);
        fabAdd = findViewById(R.id.btn_add);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listenData(loker.getId());
    }
}

