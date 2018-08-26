package silantyevmn.ru.materialdesign.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.io.Serializable;
import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.photo.Photo;
import silantyevmn.ru.materialdesign.model.theme.Theme;
import silantyevmn.ru.materialdesign.presenter.GaleryPresenter;
import silantyevmn.ru.materialdesign.view.fragment.PhotoFragment;

public class GaleryActivity extends AppCompatActivity implements IGaleryView {
    private final GaleryPresenter presenter;
    private final int SETTING_REQUEST = 2;

    public GaleryActivity(){
        presenter=new GaleryPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(DataSharedPreference.getInstance().getCurrentTheme());
        setContentView(R.layout.activity_main);
        presenter.onCreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return presenter.onClickMenuItem(item);
    }

    @Override
    public void showFragment(List<Photo> photos) {
        PhotoFragment mainFragment = new PhotoFragment();
        //запускаем транзакцию и добавляем фрагмент
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_details, mainFragment)
                .commit();
    }

    @Override
    public void showSetting(List<Theme> themes) {
        Intent intent = new Intent(GaleryActivity.this, SettingsActivity.class);
        intent.putExtra(SettingsActivity.LIST_THEMES, (Serializable) themes);
        startActivityForResult(intent,SETTING_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recreate();
    }
}
