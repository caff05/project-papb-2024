package com.example.fragmentpractice.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.fragmentpractice.listloker.Loker;
import com.example.fragmentpractice.lokerbarang.Item;
import com.example.fragmentpractice.payment.Bayar;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RealtimeDatabase {

    private final FirebaseDatabase instance;
    private final DatabaseReference mLoker;
    private final DatabaseReference mPayment;
    private static final String barangObjectName = "barang";
    private static final String lokerObjectName = "lokers";
    private static final String paymentObjectName = "payment";

    public RealtimeDatabase() {
        instance = FirebaseDatabase.getInstance();
        mLoker = instance.getReference(lokerObjectName);
        mPayment = instance.getReference(paymentObjectName);
    }


    /**
     * BARANG
     */
    public void listenData(String child,ValueEventListener listener) {
        mLoker.child(child).child(barangObjectName).addValueEventListener(listener);
    }

    public void writeItem(String child,Item dataItem, DatabaseListener dbListener) {
        mLoker.child(child).child(barangObjectName).child(UUID.randomUUID().toString()).setValue(dataItem).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dbListener.onSuccess(unused);
            }
        }).addOnFailureListener(dbListener::onError);
    }

    public void deleteItem(String child,Item dataItem, DatabaseListener dbListener) {
        mLoker.child(child).child(barangObjectName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String uid = snapshot1.child("uid").getValue(String.class);
                    if (dataItem.getUid().equals(uid)) {
                        // Delete the item
                        snapshot1.getRef().removeValue()
                                .addOnSuccessListener(dbListener::onSuccess)
                                .addOnFailureListener(dbListener::onError);
                        break; // Exit the loop once the item is found
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error reading data", error.toException());
            }
        });

    }


    /**
     * LOKER
     */
    // Menambahkan loker ke Firebase
    public void insertLoker(Loker loker) {
        String id = mLoker.push().getKey();
        if (id != null) {
            loker.setId(id);
            mLoker.child(id).setValue(loker).addOnCompleteListener(task -> {
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
        mLoker.addValueEventListener(new ValueEventListener() {
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


    /**
     *PAYMENTS
     */

    public void listenPayment(ValueEventListener listener){
        mPayment.addValueEventListener(listener);
    }

    public void addPayment(Bayar bayar){
        String id = mPayment.push().getKey();
        Bayar bayarWithId = new Bayar(
                id,
                bayar.getNama(),
                bayar.getHarga()
        );
        mPayment.child(id).setValue(bayarWithId);
    }

    public void deletePayment(String id){
        mPayment.child(id).removeValue();
    }
}
