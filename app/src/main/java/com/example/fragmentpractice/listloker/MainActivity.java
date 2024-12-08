package com.example.fragmentpractice.listloker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragmentpractice.R;
import com.example.fragmentpractice.database.RealtimeDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RealtimeDatabase lokerDao;
    private RecyclerView recyclerView;
    private LokerAdapter adapter;

    private List<Loker> allLokers = new ArrayList<>(); // Menyimpan semua loker

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_loker_activity_main);

        lokerDao = new RealtimeDatabase();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LokerAdapter();
        recyclerView.setAdapter(adapter);

        //NAMBAH DATA MANUAL
        //Loker loker = new Loker("Loker S", "Tersedia", 100, 48000, "Malang");
        //lokerDao.insertLoker(loker);
        //locatian hanya surabaya malang


        // Mengambil semua loker dan mendengarkan perubahan secara real-time
        lokerDao.getAllLokers(new RealtimeDatabase.FirebaseCallback() {
            @Override
            public void onCallback(List<Loker> lokers) {
                allLokers = lokers;  // Menyimpan semua loker
                adapter.setLokers(lokers); // Menampilkan semua loker
            }
        });

        // Tombol Malang
        findViewById(R.id.btnMalang).setOnClickListener(v -> {
            List<Loker> malangLokers = filterLokersByLocation("Malang");
            adapter.setLokers(malangLokers);
        });

        // Tombol Surabaya
        findViewById(R.id.btnSurabaya).setOnClickListener(v -> {
            List<Loker> surabayaLokers = filterLokersByLocation("Surabaya");
            adapter.setLokers(surabayaLokers);
        });
    }

    // Fungsi untuk memfilter loker berdasarkan lokasi
    private List<Loker> filterLokersByLocation(String location) {
        List<Loker> filteredLokers = new ArrayList<>();
        for (Loker loker : allLokers) {
            if (loker.getLocation().equals(location)) {
                filteredLokers.add(loker);
            }
        }
        return filteredLokers;
    }
}
