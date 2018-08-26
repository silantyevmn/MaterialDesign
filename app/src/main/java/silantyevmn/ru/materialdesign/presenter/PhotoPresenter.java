package silantyevmn.ru.materialdesign.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.photo.IModelPhoto;
import silantyevmn.ru.materialdesign.model.photo.ModelPhoto;
import silantyevmn.ru.materialdesign.model.photo.Photo;
import silantyevmn.ru.materialdesign.view.DialogView;
import silantyevmn.ru.materialdesign.view.fragment.PhotoFragment;
import silantyevmn.ru.materialdesign.view.recycler.PhotoAdapter;

/**
 * Created by silan on 25.08.2018.
 */

public class PhotoPresenter {
    private final PhotoFragment fragment;
    private final IModelPhoto modelPhoto;
    private PhotoAdapter adapter;

    public PhotoPresenter(PhotoFragment fragment) {
        this.fragment = fragment;
        modelPhoto = ModelPhoto.getInstance();
        adapter = new PhotoAdapter(modelPhoto.getList(), this);
    }

    private List<Photo> getPhotos() {
        return modelPhoto.getList();
    }

    public void registerForContextMenu(View itemView) {
        fragment.registerForContextMenu(itemView);
    }

    public Activity getActivity() {
        return fragment.getActivity();
    }

    public void onClickPhoto(int adapterPosition) {
        fragment.showViewPhoto(adapterPosition);
    }

    public void insertPhoto(Intent imageReturnedIntent) {
        Uri selectedImage = imageReturnedIntent.getData();
        Photo photo = new Photo(selectedImage.getHost());
        photo.setLocalUri(selectedImage);
        modelPhoto.insert(photo);
        updateAdapter();
        fragment.showLog("insert", String.valueOf(getPhotos().size()));
    }

    private void updateAdapter() {
        adapter.setPhotos(getPhotos());
    }

    public void deletePhoto(int position) {
        new DialogView(getActivity(), getActivity().getString(R.string.dialog_title_delete), () -> {
            modelPhoto.delete(getPhotos().get(position));
            updateAdapter();
            fragment.showLog("delete", String.valueOf(position));
        });
    }

    public void favouritesPhoto(int position) {
        modelPhoto.favorites(getPhotos().get(position));
        updateAdapter();
        fragment.showLog("favourites", String.valueOf(position));
    }

    public void onClickFabButton() {
        fragment.startActivityLoadPhoto();
    }

    public void onViewCreated() {
        fragment.init();
        fragment.setAdapter(adapter);
    }
}
