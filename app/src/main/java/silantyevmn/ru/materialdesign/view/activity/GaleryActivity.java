package silantyevmn.ru.materialdesign.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import silantyevmn.ru.materialdesign.view.fragment.PhotoFragmentFavorite;

public class GaleryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IGaleryView {
    private final GaleryPresenter presenter;
    private final int SETTING_REQUEST = 2;
    private DrawerLayout drawer;
    private static final String PHOTO_FRAGMENT_FAVORITE_TAG ="photoFragmentFavorite";
    private static final String PHOTO_FRAGMENT_HOME_TAG ="photoFragmentHome";

    public GaleryActivity() {
        presenter = new GaleryPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(DataSharedPreference.getInstance().getCurrentTheme());
        setContentView(R.layout.activity_main);
        presenter.onCreate();
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
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            presenter.onClickMenuSetting();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recreate();
    }

    @Override
    public void init() {
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
                int position=tab.getPosition();
                switch (position){
                    case 0:{
                        presenter.selectFragmentHome();
                        break;
                    }
                    case 1:{
                        presenter.selectFragmentFavorite();
                        break;
                    }
                    default: break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
               //
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //Snackbar.make(tabLayout.getRootView(),"onTabReselected",Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void showFragmentFavorite(List<Photo> photos) {
        PhotoFragmentFavorite photoFragment = (PhotoFragmentFavorite) getSupportFragmentManager().findFragmentByTag(PHOTO_FRAGMENT_FAVORITE_TAG);
        if(photoFragment==null) {
            photoFragment = new PhotoFragmentFavorite();
            //запускаем транзакцию и добавляем фрагмент
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_photo, photoFragment, PHOTO_FRAGMENT_FAVORITE_TAG)
                    .commit();
        }
    }

    @Override
    public void showFragmentHome(List<Photo> photos) {
        PhotoFragment photoFragment = (PhotoFragment) getSupportFragmentManager().findFragmentByTag(PHOTO_FRAGMENT_HOME_TAG);
        if(photoFragment==null) {
            photoFragment = new PhotoFragment();
            //запускаем транзакцию и добавляем фрагмент
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_photo, photoFragment, PHOTO_FRAGMENT_HOME_TAG)
                    .commit();
        }
    }
}
