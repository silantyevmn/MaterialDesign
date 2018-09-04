package silantyevmn.ru.materialdesign.presenter;

import android.net.Uri;

import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.photo.IModelPhoto;
import silantyevmn.ru.materialdesign.model.photo.PhotoModel;
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
        model = PhotoModel.getInstance();
    }
    //создание View
    public void onViewCreated(int span) {
        view.init(getPhotos(),span);
    }

    private List<Photo> getPhotos() {
        return model.getList();
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

    public void insertCamera(String uriString) {
        Photo photo = new Photo(Uri.parse(uriString).getLastPathSegment(),uriString);
        model.insert(photo);
        view.showLog("insertCamera", String.valueOf(getPhotos().size()));
        updateAdapter();
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

    public void insertGalery(String uriString) {
        Photo photo=new Photo(Uri.parse(uriString).getLastPathSegment(),uriString);
        model.insert(photo);
        view.showLog("insertGalery", String.valueOf(getPhotos().size()));
        updateAdapter();
    }
}
