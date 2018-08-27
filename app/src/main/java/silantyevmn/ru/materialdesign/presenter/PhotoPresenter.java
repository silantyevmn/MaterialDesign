package silantyevmn.ru.materialdesign.presenter;

import android.content.Intent;
import android.net.Uri;

import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.photo.IModelPhoto;
import silantyevmn.ru.materialdesign.model.photo.ModelPhoto;
import silantyevmn.ru.materialdesign.model.photo.Photo;
import silantyevmn.ru.materialdesign.view.DialogView;
import silantyevmn.ru.materialdesign.view.fragment.IPhotoFragment;
import silantyevmn.ru.materialdesign.view.fragment.PhotoFragment;

/**
 * Created by silan on 25.08.2018.
 */

public class PhotoPresenter {
    private final IPhotoFragment photoFragment;
    private final IModelPhoto modelPhoto;

    public PhotoPresenter(PhotoFragment photoFragment) {
        this.photoFragment = photoFragment;
        modelPhoto = ModelPhoto.getInstance();
    }

    private List<Photo> getPhotos() {
        return modelPhoto.getList();
    }

    public void onClickPhoto(int adapterPosition) {
        photoFragment.showViewPhoto(adapterPosition);
    }

    public void insert(Intent imageReturnedIntent) {
        Uri uri = imageReturnedIntent.getData();
        Photo photo = new Photo(uri.getLastPathSegment());
        photo.setUri(uri);
        modelPhoto.insert(photo);
        updateAdapter();
        photoFragment.showLog("insert", String.valueOf(getPhotos().size()));
    }

    private void updateAdapter() {
       photoFragment.setAdapter(getPhotos());
    }

    public void add() {
        photoFragment.startActivityLoadPhoto();
    }

    public void delete(int position) {
        new DialogView(photoFragment.getActivity(), photoFragment.getActivity().getString(R.string.dialog_title_delete), () -> {
            modelPhoto.delete(getPhotos().get(position));
            updateAdapter();
            photoFragment.showLog("delete", String.valueOf(position));
        });
    }

    public void favorite(int position) {
        modelPhoto.favorites(getPhotos().get(position));
        updateAdapter();
        photoFragment.showLog("favourites", String.valueOf(position));
    }

    public void onViewCreated() {
        photoFragment.init(getPhotos());
    }
}
