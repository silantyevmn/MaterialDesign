package silantyevmn.ru.materialdesign.presenter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.photo.IPhotoModel;
import silantyevmn.ru.materialdesign.model.photo.Photo;
import silantyevmn.ru.materialdesign.model.photo.PhotoModel;
import silantyevmn.ru.materialdesign.view.DialogView;
import silantyevmn.ru.materialdesign.view.activity.IGaleryView;
import silantyevmn.ru.materialdesign.view.fragment.IPhotoFragment;
import silantyevmn.ru.materialdesign.view.fragment.PhotoFragment;

@InjectViewState
public class PhotoPresenter extends MvpPresenter<IPhotoFragment> {
    private final IPhotoModel model;
    private final IGaleryView mainActivity;

    public PhotoPresenter(PhotoFragment photoFragment) {
        this.mainActivity = (IGaleryView) photoFragment.getActivity();
        model = PhotoModel.getInstance();
    }

    public void init(Context context) {
        getViewState().init(getPhotos(), model.getGridLayoutManagerSpan(context.getResources().getConfiguration().orientation));
    }

    private List<Photo> getPhotos() {
        return model.getList();
    }

    public void updateAdapter() {
        getViewState().setAdapter(getPhotos());
    }

    public void delete(int position, Activity activity) {
        if (position == -1) {
            Uri uri = Uri.parse(DataSharedPreference.getInstance().getUriCamera());
            Photo photo = new Photo(uri.getLastPathSegment(), uri.toString());
            model.delete(photo);
            return;
        }
        new DialogView(activity, activity.getString(R.string.dialog_title_delete), () -> {
            Photo photo = getPhotos().get(position);
            model.delete(photo);
            updateAdapter();
            getViewState().showLog("delete", photo.getName() + " удалено из базы.");
        });
    }

    public void favorite(int position) {
        model.update(getPhotos().get(position));
        updateAdapter();
        Photo newPhoto = getPhotos().get(position);
        if (newPhoto.isFavorite()) {
            getViewState().showLog("favourites", newPhoto.getName() + " добавлено в избранное.");
        } else {
            getViewState().showLog("favourites", newPhoto.getName() + " удалено из избранного.");
        }
    }

    public void onClickImportCamera(Activity activity) {
        //todo test import camera
        mainActivity.showImportCamera();
    }

    public void onClickPhoto(int adapterPosition) {
        mainActivity.showFullPhoto(getPhotos().get(adapterPosition));
    }

    public int getGridLayoutManagerSpan(int orientation) {
        return model.getGridLayoutManagerSpan(orientation);
    }

    public void onClickImportGalery(Activity activity) {
        //todo test import galery
        mainActivity.showImportGalery();
    }

    public void onClickBottonMenuHome() {
        getViewState().showBottonHome();
    }

    public void onClickBottonMenuDatabase() {
        getViewState().showBottonDatabase();
    }

    public void onClickBottonMenuNetwork() {
        getViewState().showBottonNetwork();
    }
}
