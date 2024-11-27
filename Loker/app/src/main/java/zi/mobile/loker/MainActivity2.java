package zi.mobile.loker;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        ImageView imageView2 = findViewById(R.id.imageView2);

        Uri selectedImage = Uri.parse(getIntent().getExtras().getString("gambar"));

        imageView2.setImageURI(selectedImage);
    }
}
