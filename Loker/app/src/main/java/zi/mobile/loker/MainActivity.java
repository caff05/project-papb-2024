package zi.mobile.loker;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;



        public class MainActivity extends AppCompatActivity {

            private ImageView imageView;
            private Button btnPickImage, simpanBtn;
            private Uri selectedImageUri;
            private ActivityResultLauncher<Intent> resultLauncher;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                imageView = findViewById(R.id.imageView);
                btnPickImage = findViewById(R.id.btnPickImage);
                simpanBtn = findViewById(R.id.button1);

                registerResult();

                btnPickImage.setOnClickListener(view1 -> pickImage());

                simpanBtn.setOnClickListener((_view) -> {
                    Intent pindahHomePage = new Intent(this, MainActivity2.class);
                    pindahHomePage.putExtra("gambar", selectedImageUri.toString());
                    startActivity(pindahHomePage);
                });
            }

            private void pickImage() {
                Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
                resultLauncher.launch(intent);
            }

            private void registerResult() {
                resultLauncher = registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                                    try {
                                        Uri imageUri = result.getData().getData();
                                        selectedImageUri = imageUri;
                                        imageView.setImageURI(imageUri);
                                    } catch (Exception e) {
                                        Toast.makeText(MainActivity.this, "Tidak ada gambar terpilih", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                );
            }
        }


