package silantyevmn.ru.materialdesign.presenter;


import android.content.Context;
import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.photo.IPhotoModel;
import silantyevmn.ru.materialdesign.model.photo.Photo;
import silantyevmn.ru.materialdesign.model.photo.PhotoModel;
import silantyevmn.ru.materialdesign.model.theme.IModelTheme;
import silantyevmn.ru.materialdesign.model.theme.ModelTheme;
import silantyevmn.ru.materialdesign.view.activity.IGaleryView;

@InjectViewState
public class GaleryPresenter extends MvpPresenter<IGaleryView> {
    private final IPhotoModel modelPhoto;
    private final IModelTheme modelTheme;

    public GaleryPresenter() {
        modelPhoto = PhotoModel.getInstance();
        modelTheme = ModelTheme.getInstance();
    }

    public void onClickMenuSetting() {
        getViewState().showSetting(modelTheme.getList());
    }

    public Uri getUriToCamera(Context context) {
        return modelPhoto.getUriToCamera(context);
    }

    public void setUriCameraToSharedPreference(String s) {
        modelPhoto.setUriCamera(s);
    }

    public void onClickImportCamera() {
        getViewState().showImportCamera();
    }

    public void onClickImportGalery() {
        getViewState().showImportGalery();
    }

    public void deleteTempFileCamera() {
        Uri uri = Uri.parse(DataSharedPreference.getInstance().getUriCamera());
        Photo photo = new Photo(uri.getLastPathSegment(), uri.toString());
        modelPhoto.delete(photo);
    }

    public void insertCamera(String uriString) {
        Photo photo = new Photo(Uri.parse(uriString).getLastPathSegment(), uriString);
        modelPhoto.insert(photo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            getViewState().updateAdapter();
                            getViewState().showLog("insertCamera", photo.getName() + " успешно добавлено");

                        }
                );
    }

    public void insertGalery(Context context, Uri uri) {
        Uri newUri = modelPhoto.getUriToGalery(context, uri);
        if(newUri==null)
            return;
        Photo photo=new Photo(newUri.getLastPathSegment(), newUri.toString());
        modelPhoto.insert(photo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    getViewState().updateAdapter();
                    getViewState().showLog("insertGalery", photo.getName() + " успешно добавлено");
                });

    }

    public void onTabSelected(int position) {
        getViewState().updateAdapter();

    }
}
