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
import silantyevmn.ru.materialdesign.view.activity.GaleryActivity;
import silantyevmn.ru.materialdesign.view.activity.IGaleryView;
import silantyevmn.ru.materialdesign.view.fragment.IPhotoFragment;
import silantyevmn.ru.materialdesign.view.fragment.IPhotoFragmentFavorite;
import silantyevmn.ru.materialdesign.view.fragment.PhotoFragmentFavorite;


@InjectViewState
public class PhotoPresenterFavorite extends MvpPresenter<IPhotoFragmentFavorite> {
    private final IPhotoModel model;
    private final IGaleryView mainActivity;

    public PhotoPresenterFavorite(PhotoFragmentFavorite photoFragment) {
        this.mainActivity = (GaleryActivity) photoFragment.getActivity();
        model = PhotoModel.getInstance();
    }

    //создание View
    public void init(Context context) {
        getViewState().init(getPhotos(), model.getGridLayoutManagerSpan(context.getResources().getConfiguration().orientation));
    }

    private List<Photo> getPhotos() {
        return model.getListFavorite();
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
            model.delete(getPhotos().get(position));
            updateAdapter();
            getViewState().showLog("delete", String.valueOf(position));
        });

    }

    public void favorite(int position) {
        model.update(getPhotos().get(position));
        updateAdapter();
        getViewState().showLog("favourites", String.valueOf(position));
    }

    public void onClickPhoto(int adapterPosition) {
        //запускаем в главной активити
        mainActivity.showFullPhoto(getPhotos().get(adapterPosition));
    }
}
