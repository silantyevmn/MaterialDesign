package silantyevmn.ru.materialdesign.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.photo.Photo;
import silantyevmn.ru.materialdesign.model.theme.Theme;
import silantyevmn.ru.materialdesign.presenter.GaleryPresenter;
import silantyevmn.ru.materialdesign.view.fragment.CustomFragmentPagerAdapter;
import silantyevmn.ru.materialdesign.view.fragment.TabFragmentFactory;

public class GaleryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IGaleryView {
    private final GaleryPresenter presenter;
    private final int IMPORT_CAMERA_REQUEST = 1;
    private final int IMPORT_GALERY_REQUEST = 2;
    private final int SETTING_REQUEST = 3;
    private DrawerLayout drawer;
    private ViewPager mViewPager;
    private CustomFragmentPagerAdapter customFragmentPagerAdapter;

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
        Intent intent = new Intent(GaleryActivity.this, SettingActivity.class);
        //intent.putExtra(SettingActivity.LIST_THEMES, (Serializable) themes);
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

        //создаем фабрику фрагментов
        TabFragmentFactory tabFragmentFactory = new TabFragmentFactory();
        customFragmentPagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), tabFragmentFactory);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(customFragmentPagerAdapter);

        //добавляем TabLayout
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        //вешаем принудительное обновление фрагментов по вкладкам
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                presenter.onTabSelected(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
       /* //открываем сохраненный tab
        TabLayout.Tab tab = tabLayout.getTabAt(presenter.getIdFragment());
        tab.select();*/
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
       /* Snackbar mSnackbar = Snackbar.make(getCurrentFocus(), title + "-->" + value, Snackbar.LENGTH_LONG);
        mSnackbar.show();*/
        Log.i(title, "-->" + value);
    }

    @Override
    public void updateAdapter() {
        customFragmentPagerAdapter.updateAllFragmentsToAdapter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMPORT_GALERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Uri uri = data.getData();
                        showLog("onActivityResult", "Uri :" + uri.toString());
                        presenter.insertGalery(this, uri);
                    }
                }
                break;
            case IMPORT_CAMERA_REQUEST: {
                if (resultCode == RESULT_OK) {
                    presenter.insertCamera(DataSharedPreference.getInstance().getUriCamera());
                    showLog("onActivityResult", "Uri :" + DataSharedPreference.getInstance().getUriCamera());
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

    @Override
    public void showFullPhoto(Photo photo) {
        Intent intent = new Intent(this, PhotoFullActivity.class);
        intent.putExtra(PhotoFullActivity.PHOTO_FULL_ACTIVITY_KEY, photo.getUri());
        startActivity(intent);
    }
}
