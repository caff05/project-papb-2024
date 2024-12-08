package com.example.fragmentpractice.listloker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragmentpractice.R;
import com.example.fragmentpractice.database.RealtimeDatabase;
import com.example.fragmentpractice.lokerbarang.MainActivity2;

import java.util.ArrayList;
import java.util.List;

public class ListLokerFragment extends Fragment {

    private RealtimeDatabase lokerDao;
    private RecyclerView recyclerView;
    private LokerAdapter adapter;

    private List<Loker> allLokers = new ArrayList<>(); // Menyimpan semua loker

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_loker_activity_main, container,false);
        lokerDao = new RealtimeDatabase();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new LokerAdapter();
        recyclerView.setAdapter(adapter);

        //NAMBAH DATA MANUAL
//        Loker loker = new Loker("Loker S", "Tersedia", 100, 48000, "Surabaya");
//        lokerDao.insertLoker(loker);
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
        view.findViewById(R.id.btnMalang).setOnClickListener(v -> {
            List<Loker> malangLokers = filterLokersByLocation("Malang");
            adapter.setLokers(malangLokers);
        });

        // Tombol Surabaya
        view.findViewById(R.id.btnSurabaya).setOnClickListener(v -> {
            List<Loker> surabayaLokers = filterLokersByLocation("Surabaya");
            adapter.setLokers(surabayaLokers);
        });
        adapter.setListener(new LokerAdapter.ListLokerListener() {
            @Override
            public void onItemClick(Loker loker) {
                Intent intent = new Intent(requireContext(), MainActivity2.class);
                intent.putExtra("id", loker.getId());
                intent.putExtra("nama", loker.getNama());
                intent.putExtra("status", loker.getStatus());
                intent.putExtra("capacity", loker.getCapacity());
                intent.putExtra("price", loker.getPrice());
                intent.putExtra("location", loker.getLocation());
                startActivity(intent);

            }
        });
        return view;
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
