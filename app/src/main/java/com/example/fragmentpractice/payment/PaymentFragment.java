package com.example.fragmentpractice.payment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragmentpractice.R;
import com.example.fragmentpractice.database.RealtimeDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PaymentFragment extends Fragment implements View.OnClickListener {

    private Button btSimpan;
    private EditText etNama;
    private EditText etHarga;

    private RealtimeDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_activity_main, container,false);
        this.btSimpan = view.findViewById(R.id.btSimpan);
        this.etNama = view.findViewById(R.id.etNama);
        this.etHarga = view.findViewById(R.id.etHarga);



        this.btSimpan.setOnClickListener(this);

        database = new RealtimeDatabase();


        return view;
    }



    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btSimpan){
            Bayar bayar = new Bayar();
            bayar.setNama(this.etNama.getText().toString());
            bayar.setHarga(this.etHarga.getText().toString());
            this.database.addPayment(bayar);
        }


    }
}