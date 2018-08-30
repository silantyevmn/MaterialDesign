package silantyevmn.ru.materialdesign.presenter;

import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.io.IOException;
import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.photo.FileOperation;
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
    private final IPhotoFragment view;
    private final IModelPhoto model;

    public PhotoPresenter(PhotoFragment photoFragment) {
        this.view = photoFragment;
        model = ModelPhoto.getInstance();
    }

    private List<Photo> getPhotos() {
        return model.getList();
    }

    private void updateAdapter() {
        view.setAdapter(getPhotos());
    }

    public void addPhoto() {
        view.showGalery();
    }

    public void delete(int position) {
        new DialogView(view.getActivity(), view.getActivity().getString(R.string.dialog_title_delete), () -> {
            model.delete(getPhotos().get(position));
            updateAdapter();
            view.showLog("delete", String.valueOf(position));
        });
    }

    public void favorite(int position) {
        model.favorites(getPhotos().get(position));
        updateAdapter();
        view.showLog("favourites", String.valueOf(position));
    }

    public void insert(String uriString) {
        Photo photo = new Photo(Uri.parse(uriString).getLastPathSegment(),uriString);
        model.insert(photo);
        view.showLog("insert", String.valueOf(getPhotos().size()));
        updateAdapter();
    }

    public void onViewCreated() {
        view.init(getPhotos());
    }

    public void onClickCamera() {
        view.showCamera();
    }

    public void onClickGalery() {
        view.showGalery();
    }

    public void onClickPhoto(int adapterPosition) {
        view.showFullPhoto(getPhotos().get(adapterPosition));
    }

    public void insertCamera(String s) {
        //todo нужно сохранить в директорию, а как вытащить имя файла пока не знаю...
        Photo photo=new Photo(Uri.parse(s).getLastPathSegment(),s);
        model.insert(photo);
        view.showLog("insert", String.valueOf(getPhotos().size()));
        updateAdapter();
    }
}
