package silantyevmn.ru.materialdesign.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.photo.IPhotoAdapter;
import silantyevmn.ru.materialdesign.model.photo.Photo;
import silantyevmn.ru.materialdesign.model.photo.PhotoAdapter;
import silantyevmn.ru.materialdesign.presenter.PhotoPresenter;
import silantyevmn.ru.materialdesign.view.activity.PhotoFullActivity;

public class PhotoFragment extends Fragment implements IPhotoFragment, PhotoAdapter.OnClickAdapter, IPhotoAdapter {
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private FloatingActionButton fabCamera;
    private FloatingActionButton fabGalery;
    private PhotoPresenter presenter;
    private PhotoAdapter adapter;
    private boolean isFABOpen = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PhotoPresenter(this);
        Log.i("PhotoFragment", "onCreate");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
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
        presenter.onViewCreated();
    }

    @Override
    public void init(List<Photo> photos) {
        int span = presenter.getGridLayoutManagerSpan(getResources().getConfiguration().orientation);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), span);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new PhotoAdapter(photos, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setAdapter(List<Photo> photos) {
        adapter.setPhotos(photos);
    }

    @Override
    public void showLog(String title, String value) {
        Snackbar mSnackbar = Snackbar.make(getView(), title + "-->" + value, Snackbar.LENGTH_LONG);
        mSnackbar.show();
        Log.i(title, "-->" + value);
    }

    @Override
    public void showFullPhoto(Photo photo) {
        //заглушка
        Intent intent = new Intent(getActivity(), PhotoFullActivity.class);
        intent.putExtra(PhotoFullActivity.PHOTO_FULL_ACTIVITY_TAG, photo.getUri());
        startActivity(intent);
    }

    @Override
    public void onClickPhoto(int position) {
        presenter.onClickPhoto(position);
    }

    @Override
    public void onClickMenuDelete(int position) {
        presenter.delete(position);
    }

    @Override
    public void onClickMenuFavorite(int position) {
        presenter.favorite(position);
    }
}
