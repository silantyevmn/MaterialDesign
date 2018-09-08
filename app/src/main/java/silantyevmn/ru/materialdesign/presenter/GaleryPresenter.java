package silantyevmn.ru.materialdesign.presenter;


import android.content.Context;
import android.net.Uri;

import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.photo.IModelPhoto;
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
    private final IModelPhoto modelPhoto;
    private final IModelTheme modelTheme;

    public GaleryPresenter(IGaleryView view) {
        this.view = view;
        modelPhoto = PhotoModel.getInstance();
        modelTheme = ModelTheme.getInstance();
    }

    public void onCreate() {
        selectFragmentHome();
    }

    public void onClickMenuSetting() {
        view.showSetting(modelTheme.getList());
    }

    public void selectFragmentHome() {
        view.showFragmentHome(modelPhoto.getList());
        modelPhoto.setIdFragment(0);//todo временно сохраняю id fragmenta
    }

    public void selectFragmentFavorite() {
        view.showFragmentFavorite(modelPhoto.getListFavorite());
        modelPhoto.setIdFragment(1);//todo временно сохраняю id fragmenta
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
        view.setAdapter(modelPhoto.getList());
        view.showLog("insertCamera", photo.getName() + " успешно добавлено");

    }

    public void insertGalery(Context context, Uri uri) {
        Uri newUri = modelPhoto.getUriToGalery(context, uri);
        if (newUri == null) {
            //ошибка
        } else {
            Photo photo = new Photo(newUri.getLastPathSegment(), newUri.toString());
            modelPhoto.insert(photo);
            view.setAdapter(modelPhoto.getList());
            view.showLog("insertGalery", photo.getName() + " успешно добавлено");
        }

    }

    public int getIdFragment() {
        //todo вернуть id активного фрагмента
        return modelPhoto.getIdFragment();
    }
}
