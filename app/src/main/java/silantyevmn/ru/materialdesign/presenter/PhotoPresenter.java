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

    public PhotoPresenter(PhotoFragment fragment) {
        this.fragment = fragment;
        modelPhoto = ModelPhoto.getInstance();
    }

    private List<Photo> getPhotos() {
        return modelPhoto.getList();
    }

    public Activity getActivity() {
        return fragment.getActivity();
    }

    public void onClickPhoto(int adapterPosition) {
        fragment.showViewPhoto(adapterPosition);
    }

    public void insert(Intent imageReturnedIntent) {
        Uri uri = imageReturnedIntent.getData();
        Photo photo = new Photo(uri.getLastPathSegment());
        photo.setUri(uri);
        modelPhoto.insert(photo);
        updateAdapter();
        fragment.showLog("insert", String.valueOf(getPhotos().size()));
    }

    private void updateAdapter() {
       fragment.setAdapter(getPhotos());
    }

    public void add() {
        fragment.startActivityLoadPhoto();
    }

    public void delete(int position) {
        new DialogView(getActivity(), getActivity().getString(R.string.dialog_title_delete), () -> {
            modelPhoto.delete(getPhotos().get(position));
            updateAdapter();
            fragment.showLog("delete", String.valueOf(position));
        });
    }

    public void favorite(int position) {
        modelPhoto.favorites(getPhotos().get(position));
        updateAdapter();
        fragment.showLog("favourites", String.valueOf(position));
    }

    public void onViewCreated() {
        fragment.init(getPhotos());
    }
}
