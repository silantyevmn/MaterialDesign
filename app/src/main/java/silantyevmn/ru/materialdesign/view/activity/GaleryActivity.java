package silantyevmn.ru.materialdesign.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.Serializable;
import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.photo.IPhotoAdapter;
import silantyevmn.ru.materialdesign.model.photo.Photo;
import silantyevmn.ru.materialdesign.model.theme.Theme;
import silantyevmn.ru.materialdesign.presenter.GaleryPresenter;
import silantyevmn.ru.materialdesign.view.fragment.PhotoFragment;
import silantyevmn.ru.materialdesign.view.fragment.PhotoFragmentFavorite;

public class GaleryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IGaleryView {
    private final GaleryPresenter presenter;
    private final int IMPORT_CAMERA_REQUEST = 1;
    private final int IMPORT_GALERY_REQUEST = 2;
    private final int SETTING_REQUEST = 3;
    private DrawerLayout drawer;
    private static final String PHOTO_FRAGMENT_FAVORITE_TAG = "photoFragmentFavorite";
    private static final String PHOTO_FRAGMENT_HOME_TAG = "photoFragmentHome";
    private IPhotoAdapter adapter;

    public GaleryActivity() {
        presenter = new GaleryPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(DataSharedPreference.getInstance().getCurrentTheme());
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings: {
                presenter.onClickMenuSetting();
                return true;
            }
            default:
                return false;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            presenter.onClickImportCamera();
        } else if (id == R.id.nav_gallery) {
            presenter.onClickImportGalery();
        } else if (id == R.id.nav_manage) {
            presenter.onClickMenuSetting();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showSetting(List<Theme> themes) {
        Intent intent = new Intent(GaleryActivity.this, SettingsActivity.class);
        intent.putExtra(SettingsActivity.LIST_THEMES, (Serializable) themes);
        startActivityForResult(intent, SETTING_REQUEST);
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //добавляем TabLayout
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0: {
                        presenter.selectFragmentHome();
                        break;
                    }
                    case 1: {
                        presenter.selectFragmentFavorite();
                        break;
                    }
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0: {
                        presenter.selectFragmentHome();
                        break;
                    }
                    case 1: {
                        presenter.selectFragmentFavorite();
                        break;
                    }
                    default:
                        break;
                }
            }
        });
        //открываем сохраненный tab
        TabLayout.Tab tab = tabLayout.getTabAt(presenter.getIdFragment());
        tab.select();
    }

    @Override
    public void showFragmentFavorite(List<Photo> photos) {
        PhotoFragmentFavorite photoFragment = (PhotoFragmentFavorite) getSupportFragmentManager().findFragmentByTag(PHOTO_FRAGMENT_FAVORITE_TAG);
        if (photoFragment == null) {
            photoFragment = new PhotoFragmentFavorite();
            adapter = null;
            //запускаем транзакцию и добавляем фрагмент
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_photo, photoFragment, PHOTO_FRAGMENT_FAVORITE_TAG)
                    .commit();
        }
    }

    @Override
    public void showFragmentHome(List<Photo> photos) {
        PhotoFragment photoFragment = (PhotoFragment) getSupportFragmentManager().findFragmentByTag(PHOTO_FRAGMENT_HOME_TAG);
        if (photoFragment == null) {
            photoFragment = new PhotoFragment();
            adapter = (IPhotoAdapter) photoFragment;
            //запускаем транзакцию и добавляем фрагмент
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_photo, photoFragment, PHOTO_FRAGMENT_HOME_TAG)
                    .commit();
        }
    }

    @Override
    public void showImportCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            Uri photoURI = presenter.getUriToCamera(this);
            if (photoURI != null) {
                presenter.setUriCameraToSharedPreference(photoURI.toString());
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, IMPORT_CAMERA_REQUEST);
            }
        }
    }

    @Override
    public void showImportGalery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, IMPORT_GALERY_REQUEST);
    }

    @Override
    public void showLog(String title, String value) {
        Snackbar mSnackbar = Snackbar.make(getCurrentFocus(), title + "-->" + value, Snackbar.LENGTH_LONG);
        mSnackbar.show();
        Log.i(title, "-->" + value);
    }

    @Override
    public void setAdapter(List<Photo> photos) {
        if (adapter != null) {
            adapter.setAdapter(photos);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMPORT_GALERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Uri uri = data.getData();
                        Log.i("onActivityResult", "Uri :" + uri.toString());
                        presenter.insertGalery(this, uri);
                    }
                }
                break;
            case IMPORT_CAMERA_REQUEST: {
                if (resultCode == RESULT_OK) {
                    presenter.insertCamera(DataSharedPreference.getInstance().getUriCamera());
                    Log.i("onActivityResult", "Uri :" + DataSharedPreference.getInstance().getUriCamera());
                } else {
                    presenter.deleteTempFileCamera();
                }
                break;
            }
            case SETTING_REQUEST: {
                recreate();
            }
        }
    }

}
