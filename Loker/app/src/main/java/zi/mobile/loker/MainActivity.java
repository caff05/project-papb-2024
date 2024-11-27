package zi.mobile.loker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private Button simpanBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simpanBtn = findViewById(R.id.button1);

        // Memuat Fragment ImagePicker ke dalam Activity
        if (savedInstanceState == null) {
            ImagePickerFragment imagePickerFragment = new ImagePickerFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, imagePickerFragment);
            transaction.commit();
        }

        // Menyimpan gambar yang dipilih ke Activity berikutnya
        simpanBtn.setOnClickListener(v -> {
            ImagePickerFragment fragment = (ImagePickerFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_container);
            Uri selectedImageUri = fragment.getSelectedImageUri();

            if (selectedImageUri != null) {
                Intent pindahHomePage = new Intent(this, MainActivity2.class);
                pindahHomePage.putExtra("gambar", selectedImageUri.toString());
                startActivity(pindahHomePage);
            } else {
                Toast.makeText(MainActivity.this, "Pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
