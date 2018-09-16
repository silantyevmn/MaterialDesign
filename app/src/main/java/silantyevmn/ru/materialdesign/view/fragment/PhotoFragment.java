package silantyevmn.ru.materialdesign.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.photo.Photo;
import silantyevmn.ru.materialdesign.model.photo.PhotoAdapter;
import silantyevmn.ru.materialdesign.presenter.PhotoPresenter;

public class PhotoFragment extends Fragment implements IPhotoFragment, PhotoAdapter.OnClickAdapter {
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private FloatingActionButton fabCamera;
    private FloatingActionButton fabGalery;
    private PhotoPresenter presenter;
    private PhotoAdapter adapter;
    private boolean isFABOpen = false;
    private ImageView imageViewTest;

    public static PhotoFragment newInstance(Bundle bundle) {
        PhotoFragment currentFragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putBundle("gettedArgs", bundle);
        currentFragment.setArguments(args);
        return currentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PhotoPresenter(this);
        showLog("PhotoFragment", "onCreate");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        imageViewTest=rootView.findViewById(R.id.image_botton_fragment_test); //тестовая картинка под bottonNavView
        recyclerView = rootView.findViewById(R.id.recycler);
        fab = rootView.findViewById(R.id.fab);
        fabCamera = rootView.findViewById(R.id.fabCamera);
        fabGalery = rootView.findViewById(R.id.fabGalery);
        fab.setOnClickListener(view -> {
            if (!isFABOpen) {
                showFABMenu();
            } else {
                closeFABMenu();
            }
        });
        BottomNavigationView bnv=rootView.findViewById(R.id.bnv);
        bnv.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bnv_home:{
                    presenter.onClickBottonMenuHome();
                    break;
                }
                case R.id.bnv_load_database:{
                    presenter.onClickBottonMenuDatabase();
                    break;
                }
                case R.id.bnv_load_network:{
                    presenter.onClickBottonMenuNetwork();
                    break;
                }
            }
            return false;
        });
        return rootView;
    }

    private void showFABMenu() {
        isFABOpen = true;
        fabCamera.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabGalery.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fabCamera.setOnClickListener(view -> presenter.onClickImportCamera(getActivity()));
        fabGalery.setOnClickListener(view -> presenter.onClickImportGalery(getActivity()));
    }

    private void closeFABMenu() {
        isFABOpen = false;
        fabCamera.animate().translationY(0);
        fabGalery.animate().translationY(0);
        fabCamera.setOnClickListener(null);
        fabGalery.setOnClickListener(null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.init(getContext());
    }

    @Override
    public void init(List<Photo> photos, int span) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), span);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new PhotoAdapter(photos, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setAdapter(List<Photo> photos) {
        if (adapter != null) {
            adapter.setPhotos(photos);
        }
    }

    private void setVisibleFragment(boolean flag){
        if(flag){
            imageViewTest.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            imageViewTest.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showBottonHome() {
        //показать текущий фрагмент
        setVisibleFragment(true);
    }

    @Override
    public void showBottonDatabase() {
        //показать фрагмент DAtabase
        setVisibleFragment(false);
        imageViewTest.setImageResource(R.drawable.ic_folder_black_24dp);
    }

    @Override
    public void showBottonNetwork() {
        //показать фрагмент Network
        setVisibleFragment(false);
        imageViewTest.setImageResource(R.drawable.ic_cloud_download_black_24dp);
    }

    @Override
    public void showLog(String title, String value) {
       /* Snackbar mSnackbar = Snackbar.make(getView(), title + "-->" + value, Snackbar.LENGTH_LONG);
        mSnackbar.show();*/
        Log.i(title, "-->" + value);
    }

    @Override
    public void onClickPhoto(int position) {
        presenter.onClickPhoto(position);
    }

    @Override
    public void onClickMenuDelete(int position) {
        presenter.delete(position, getActivity());
    }

    @Override
    public void onClickMenuFavorite(int position) {
        presenter.favorite(position);
    }

    @Override
    public void updateAdapter() {
        presenter.updateAdapter();
    }
}
