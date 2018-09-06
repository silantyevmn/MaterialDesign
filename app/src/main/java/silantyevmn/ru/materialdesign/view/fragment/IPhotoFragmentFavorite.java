package silantyevmn.ru.materialdesign.view.fragment;

import android.app.Activity;

import java.util.List;

import silantyevmn.ru.materialdesign.model.photo.Photo;

/**
 * Created by silan on 24.08.2018.
 */

public interface IPhotoFragmentFavorite {
    void showFullPhoto(Photo photo);
    void showLog(String title, String value);
    void init(List<Photo> photos, int span);
    void setAdapter(List<Photo> photos);
    Activity getActivity();
}