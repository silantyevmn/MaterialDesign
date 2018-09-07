package silantyevmn.ru.materialdesign.view.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.photo.Photo;
import silantyevmn.ru.materialdesign.model.photo.PhotoAdapter;
import silantyevmn.ru.materialdesign.model.photo.PhotoDataFile;
import silantyevmn.ru.materialdesign.presenter.IPhotoPresenter;
import silantyevmn.ru.materialdesign.presenter.PhotoPresenter;
import silantyevmn.ru.materialdesign.presenter.PhotoPresenterFavorite;
import silantyevmn.ru.materialdesign.view.activity.PhotoFullActivity;

import static android.app.Activity.RESULT_OK;

public class PhotoFragmentFavorite extends Fragment implements IPhotoFragmentFavorite, PhotoAdapter.OnClickAdapter {
    private int COUNT_SPAN = 2; //количество фото в строке
    private RecyclerView recyclerView;
    private PhotoPresenterFavorite presenter;
    private PhotoAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PhotoPresenterFavorite(this);
        Log.i("PhotoFragmentFavorite","onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_photo_favorite, container, false);
        recyclerView = rootView.findViewById(R.id.recycler);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            COUNT_SPAN = 2;
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            COUNT_SPAN = 3;
        }
        presenter.onViewCreated(COUNT_SPAN);

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
        intent.putExtra("image_full", photo.getUri());
        startActivity(intent);
        showLog("showPhoto->", String.valueOf(photo.getName()));

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
