package silantyevmn.ru.materialdesign.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.io.Serializable;
import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.controler.MainController;
import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.entity.Photo;
import silantyevmn.ru.materialdesign.model.entity.Theme;
import silantyevmn.ru.materialdesign.view.fragment.MainFragment;

public class MainActivity extends AppCompatActivity implements MainController.SetViewActivity {

    private MainController controller;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(DataSharedPreference.getInstance().getCurrentTheme());
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        this.controller = new MainController(this);
    }

    @Override
    public void onShowFragmentPhotos(final List<Photo> photos) {
        //adapter.setData(photos);
        handler.post(new Runnable() {
            @Override
            public void run() {
                createFragment(photos);
            }
        });

    }

    public void onShowActivitySetting(final List<Theme> themes) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                intent.putExtra(SettingsActivity.LIST_THEMES, (Serializable) themes);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            recreate();
        }
    }

    private void createFragment(List<Photo> photos) {
        MainFragment mainFragment = MainFragment.newInstance(photos);
        //запускаем транзакцию и добавляем фрагмент
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_details, mainFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();
        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.action_settings: {
                controller.settingsSelected();
                return true;
            }
            default:
                return false;
        }
    }

    @Override
    protected void onDestroy() {
        controller.setViewActivity(null);
        super.onDestroy();
    }
}
