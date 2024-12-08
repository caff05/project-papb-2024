package com.example.fragmentpractice.lokerbarang;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.fragmentpractice.R;
import com.example.fragmentpractice.database.DatabaseListener;
import com.example.fragmentpractice.database.RealtimeDatabase;
import com.example.fragmentpractice.listloker.Loker;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageButton backBtn;
    private Button btnPickImage;
    private Button simpanBtn;
    private EditText namaBarangTI;
    private EditText namaPenitipTI;
    private EditText alamatPenitipTI;
    private EditText periodeMulaiTI;
    private EditText periodeSelesaiTI;
    private EditText kategoriItemTI;
    private String selectedImageBase64;

    private Loker loker;
    private ActivityResultLauncher<Intent> resultLauncher;

    RealtimeDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barang_activity_main);
        Intent intentData= getIntent();
        loker = new Loker(
                intentData.getStringExtra("id"),
                intentData.getStringExtra("nama"),
                intentData.getStringExtra("status"),
                intentData.getIntExtra("capacity",0),
                intentData.getDoubleExtra("price", 0),
                intentData.getStringExtra("location")
        );

        db = new RealtimeDatabase();
        initializeComponents();

        registerResult();

        btnPickImage.setOnClickListener(view1 -> pickImage());

        simpanBtn.setOnClickListener(onClick -> {
            Toast.makeText(MainActivity.this, "Tersimpan", Toast.LENGTH_SHORT).show();

            saveData(loker.getId());
        });
        backBtn.setOnClickListener(onClick -> {
            finish();
        });
    }

    private void saveData(String child) {
        String namaBarang = namaBarangTI.getText().toString();
        String namaPenitip = namaPenitipTI.getText().toString();
        String alamatPenitip = alamatPenitipTI.getText().toString();
        String periodeMulai = periodeMulaiTI.getText().toString();
        String periodeSelesai = periodeSelesaiTI.getText().toString();
        String kategoriItem = kategoriItemTI.getText().toString();

        Item item = new Item(
                UUID.randomUUID().toString(),
                namaBarang,
                namaPenitip,
                alamatPenitip,
                periodeMulai,
                periodeSelesai,
                kategoriItem,
                selectedImageBase64
        );
        db.writeItem(child,item, new DatabaseListener() {
            @Override
            public void onSuccess(Void a) {
                Intent paymentIntent = new Intent(MainActivity.this, com.example.fragmentpractice.payment.MainActivity.class);
                startActivity(paymentIntent);
                finish();
//                Intent pindahHomePage = new Intent(MainActivity.this, MainActivity2.class);
//                startActivity(pindahHomePage);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void initializeComponents() {
        // Initialize UI components
        namaBarangTI = findViewById(R.id.namaBarangTI);
        namaPenitipTI = findViewById(R.id.namaPenitipTI);
        alamatPenitipTI = findViewById(R.id.alamatPenitipTI);
        periodeMulaiTI = findViewById(R.id.periodeMulaiTI);
        periodeSelesaiTI = findViewById(R.id.periodeSelesaiTI);
        kategoriItemTI = findViewById(R.id.kategoriItemTI);
        imageView = findViewById(R.id.imageView);
        btnPickImage = findViewById(R.id.btnPickImage);
        simpanBtn = findViewById(R.id.save_btn);
        backBtn = findViewById(R.id.back_button);
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }

    private void registerResult() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        final InputStream imageStream;
                        try {
                            imageStream = getContentResolver().openInputStream(imageUri);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        String encodedImage = encodeImage(selectedImage);
                        selectedImageBase64 = encodedImage;
                        Glide.with(this)
                                .asBitmap()
                                .load(Base64.decode(encodedImage,Base64.DEFAULT))
                                .placeholder(R.drawable.boneka)
                                .into(imageView);
                    }
                }
        );
    }
    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }
}


