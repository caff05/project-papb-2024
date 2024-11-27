package zi.mobile.loker;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class ImagePickerFragment extends Fragment {

    private ImageView imageView;
    private Uri selectedImageUri;
    private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Menangani hasil pemilihan gambar
        registerResult();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Menginflate layout fragment
        View rootView = inflater.inflate(R.layout.fragment_image_picker, container, false);

        imageView = rootView.findViewById(R.id.imageView);

        // Button untuk memilih gambar
        Button btnPickImage = rootView.findViewById(R.id.btnPickImage);
        btnPickImage.setOnClickListener(view -> pickImage());

        return rootView;
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
                                Toast.makeText(getActivity(), "Tidak ada gambar terpilih", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );
    }

    public Uri getSelectedImageUri() {
        return selectedImageUri;
    }
}