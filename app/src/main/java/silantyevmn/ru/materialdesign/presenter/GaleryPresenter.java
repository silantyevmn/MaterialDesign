package silantyevmn.ru.materialdesign.presenter;


import android.content.Context;
import android.net.Uri;

import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.photo.IPhotoModel;
import silantyevmn.ru.materialdesign.model.photo.Photo;
import silantyevmn.ru.materialdesign.model.photo.PhotoModel;
import silantyevmn.ru.materialdesign.model.theme.IModelTheme;
import silantyevmn.ru.materialdesign.model.theme.ModelTheme;
import silantyevmn.ru.materialdesign.view.activity.IGaleryView;

/**
 * Created by silan on 24.08.2018.
 */

public class GaleryPresenter {
    private final IGaleryView view;
    private final IPhotoModel modelPhoto;
    private final IModelTheme modelTheme;

    public GaleryPresenter(IGaleryView view) {
        this.view = view;
        modelPhoto = PhotoModel.getInstance();
        modelTheme = ModelTheme.getInstance();
    }

    public void onClickMenuSetting() {
        view.showSetting(modelTheme.getList());
    }

    public Uri getUriToCamera(Context context) {
        return modelPhoto.getUriToCamera(context);
    }

    public void setUriCameraToSharedPreference(String s) {
        modelPhoto.setUriCamera(s);
    }

    public void onClickImportCamera() {
        view.showImportCamera();
    }

    public void onClickImportGalery() {
        view.showImportGalery();
    }

    public void deleteTempFileCamera() {
        Uri uri = Uri.parse(DataSharedPreference.getInstance().getUriCamera());
        Photo photo = new Photo(uri.getLastPathSegment(), uri.toString());
        modelPhoto.delete(photo);
    }

    public void insertCamera(String uriString) {
        Photo photo = new Photo(Uri.parse(uriString).getLastPathSegment(), uriString);
        modelPhoto.insert(photo);
        view.updateAdapter();
        view.showLog("insertCamera", photo.getName() + " успешно добавлено");

    }

    public void insertGalery(Context context, Uri uri) {
        Uri newUri = modelPhoto.getUriToGalery(context, uri);
        if (newUri == null) {
            //ошибка
        } else {
            Photo photo = new Photo(newUri.getLastPathSegment(), newUri.toString());
            modelPhoto.insert(photo);
            view.updateAdapter();
            view.showLog("insertGalery", photo.getName() + " успешно добавлено");
        }

    }

    public void onTabSelected(int position) {
        view.updateAdapter();

    }
}
