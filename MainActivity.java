package ap.mobile.ponbuk;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final
        String firebaseURL = "https://pam-d-cb9d6-default-rtdb.firebaseio.com/";
    private Button btSimpan;
    private RecyclerView rvPembayaran;
    private EditText etNama;
    private EditText etHarga;

    private PonBukAdapter adapter;
    private List<Kontak> dataset;
    private FirebaseDatabase db;
    private DatabaseReference appDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btSimpan = findViewById(R.id.btSimpan);
        this.rvPembayaran = findViewById(R.id.rvPembayaran);
        this.etNama = findViewById(R.id.etNama);
        this.etHarga = findViewById(R.id.etHarga);
        this.dataset = new ArrayList<Kontak>();
        this.adapter = new PonBukAdapter(this, this.dataset);

        this.rvPembayaran.setLayoutManager(new LinearLayoutManager(this));
        this.rvPembayaran.setAdapter(this.adapter);

        this.btSimpan.setOnClickListener(this);

        this.db = FirebaseDatabase.getInstance(firebaseURL);
        this.appDb = this.db.getReference("ponbuk");

        this.appDb.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dataset.clear();
                        for (DataSnapshot s: snapshot.getChildren()) {
                            Kontak k = s.getValue(Kontak.class);
                            dataset.add(k);
                        }
                        adapter.notifyDataSetChanged();
                    }
//saat ada perubahan data, method onDataChange  ini akan langdung digunakan dan akan masuk ke snapshot

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btSimpan){
            String id = this.appDb.push().getKey();
            Kontak kontak = new Kontak();
            kontak.setId(id);
            kontak.setNama(this.etNama.getText().toString());
            kontak.setHarga(this.etHarga.getText().toString());
            this.appDb.child(id).setValue(kontak);
        }


    }
}