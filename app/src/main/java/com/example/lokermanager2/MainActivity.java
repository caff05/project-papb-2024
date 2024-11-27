package com.example.lokermanager2;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LokerDao lokerDao;
    private RecyclerView recyclerView;
    private LokerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lokerDao = new LokerDao();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LokerAdapter();
        recyclerView.setAdapter(adapter);

        // Mengambil semua loker dan mendengarkan perubahan secara real-time
        lokerDao.getAllLokers(new LokerDao.FirebaseCallback() {
            @Override
            public void onCallback(List<Loker> lokers) {
                // Menampilkan data loker yang sudah diupdate pada RecyclerView
                adapter.setLokers(lokers);
            }
        });
    }
}
