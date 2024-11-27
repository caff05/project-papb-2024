package zi.mobile.loker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.UUID;

import zi.mobile.loker.firebase.DatabaseListener;
import zi.mobile.loker.firebase.RealtimeDatabase;


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
    private Uri selectedImageUri;
    private ActivityResultLauncher<Intent> resultLauncher;

    RealtimeDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new RealtimeDatabase();
        initializeComponents();

        registerResult();

        btnPickImage.setOnClickListener(view1 -> pickImage());

        simpanBtn.setOnClickListener(onClick -> saveData());
        backBtn.setOnClickListener(onClick -> {
            finish();
        });
    }

    private void saveData() {
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
                selectedImageUri.toString()
        );
        db.writeItem(item, new DatabaseListener() {
            @Override
            public void onSuccess(Void a) {
                Intent pindahHomePage = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(pindahHomePage);
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
                        selectedImageUri = imageUri;
                        Glide.with(this)
                                .load(imageUri)
                                .into(imageView);
                    }
                }
        );
    }
}


