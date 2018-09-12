package silantyevmn.ru.materialdesign.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.DataSharedPreference;

public class PhotoFullActivity extends AppCompatActivity {
    public static final String PHOTO_FULL_ACTIVITY_KEY = "image_full";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(DataSharedPreference.getInstance().getCurrentTheme());
        setContentView(R.layout.activity_photo_full);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //добавляем в АппБар кнопку назад
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView imageView = findViewById(R.id.image_full);
        Uri uri = Uri.parse((getIntent().getStringExtra(PHOTO_FULL_ACTIVITY_KEY)));
        imageView.setImageURI(uri);
    }
}
