package com.example.fragmentpractice.payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btSimpan;
    private EditText etNama;
    private EditText etHarga;

    private RealtimeDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_activity_main);

        this.btSimpan = findViewById(R.id.btSimpan);
        this.etNama = findViewById(R.id.etNama);
        this.etHarga = findViewById(R.id.etHarga);



        this.btSimpan.setOnClickListener(this);

        database = new RealtimeDatabase();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btSimpan){
            Bayar bayar = new Bayar();
            bayar.setNama(this.etNama.getText().toString());
            bayar.setHarga(this.etHarga.getText().toString());
            this.database.addPayment(bayar);
//            Intent paymentIntent = new Intent(MainActivity.this, com.example.fragmentpractice.payment.MainActivity.class);
//            startActivity(paymentIntent);
finish();
        }


    }
}