package com.example.fragmentpractice;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragmentpractice.database.RealtimeDatabase;
import com.example.fragmentpractice.payment.Bayar;
import com.example.fragmentpractice.payment.PembayaranAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends AppCompatActivity {

    private RecyclerView rvPembayaran;
    private PembayaranAdapter adapter;
    private List<Bayar> dataset;

    private RealtimeDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        database = new RealtimeDatabase();
        this.dataset = new ArrayList<Bayar>();
        this.adapter = new PembayaranAdapter(this, dataset);
        this.rvPembayaran = findViewById(R.id.rvPembayaran);
        this.rvPembayaran.setLayoutManager(new LinearLayoutManager(this));
        this.rvPembayaran.setAdapter(this.adapter);

        this.database.listenPayment(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dataset.clear();
                        for (DataSnapshot s: snapshot.getChildren()) {
                            Bayar k = s.getValue(Bayar.class);
                            dataset.add(k);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }
}