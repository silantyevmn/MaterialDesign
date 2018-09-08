package silantyevmn.ru.materialdesign.presenter;

import android.net.Uri;

import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.photo.IModelPhoto;
import silantyevmn.ru.materialdesign.model.photo.Photo;
import silantyevmn.ru.materialdesign.model.photo.PhotoModel;
import silantyevmn.ru.materialdesign.view.DialogView;
import silantyevmn.ru.materialdesign.view.fragment.IPhotoFragment;
import silantyevmn.ru.materialdesign.view.fragment.IPhotoFragmentFavorite;
import silantyevmn.ru.materialdesign.view.fragment.PhotoFragment;
import silantyevmn.ru.materialdesign.view.fragment.PhotoFragmentFavorite;

/**
 * Created by silan on 25.08.2018.
 */

public class PhotoPresenterFavorite {
    private final IPhotoFragmentFavorite view;
    private final IModelPhoto model;

    public PhotoPresenterFavorite(PhotoFragmentFavorite photoFragment) {
        this.view = photoFragment;
        model = PhotoModel.getInstance();
    }
    //создание View
    public void onViewCreated() {
        view.init(getPhotos());
    }

    private List<Photo> getPhotos() {
        return model.getListFavorite();
    }

    private void updateAdapter() {
        view.setAdapter(getPhotos());
    }

    public void delete(int position) {
        if(position==-1){
            Uri uri= Uri.parse(DataSharedPreference.getInstance().getUriCamera());
            Photo photo = new Photo(uri.getLastPathSegment(),uri.toString());
            model.delete(photo);
            return;
        }
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

    public void onClickPhoto(int adapterPosition) {
        view.showFullPhoto(getPhotos().get(adapterPosition));
    }

    public int getGridLayoutManagerSpan(int orientation) {
       return model.getGridLayoutManagerSpan(orientation);
    }
}
