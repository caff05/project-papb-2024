package com.example.lokermanager2;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LokerDao {

    private DatabaseReference dbReference;

    // Constructor untuk mendapatkan referensi ke Firebase Realtime Database
    public LokerDao() {
        dbReference = FirebaseDatabase.getInstance().getReference("lokers");
    }

    // Menambahkan loker ke Firebase
    public void insertLoker(Loker loker) {
        String id = dbReference.push().getKey();
        if (id != null) {
            loker.setId(id);
            dbReference.child(id).setValue(loker).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("LokerDao", "Data berhasil disimpan: " + loker.getNama());
                } else {
                    Log.e("LokerDao", "Gagal menyimpan data: " + task.getException());
                }
            });
        } else {
            Log.e("LokerDao", "ID untuk loker tidak valid");
        }
    }

    // Mengambil semua loker dari Firebase secara real-time dengan listener
    public void getAllLokers(final FirebaseCallback callback) {
        // Menggunakan ValueEventListener untuk mendengarkan perubahan data secara real-time
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Loker> lokers = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Loker loker = snapshot.getValue(Loker.class);
                    if (loker != null) {
                        lokers.add(loker);
                    }
                }
                // Kirim data loker yang sudah diperbarui ke callback
                callback.onCallback(lokers);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("LokerDao", "Gagal mengambil data: " + databaseError.getMessage());
            }
        });
    }

    // Callback interface untuk mengambil data loker dari Firebase
    public interface FirebaseCallback {
        void onCallback(List<Loker> lokers);  // Method untuk mengirimkan data loker
    }
}
