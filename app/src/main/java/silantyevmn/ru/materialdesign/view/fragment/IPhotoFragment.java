package silantyevmn.ru.materialdesign.view.fragment;

import android.app.Activity;

import java.util.List;

import silantyevmn.ru.materialdesign.model.photo.Photo;
import silantyevmn.ru.materialdesign.view.recycler.PhotoAdapter;

/**
 * Created by silan on 24.08.2018.
 */

public interface IPhotoFragment {
    void showViewPhoto(int adapterPosition);
    void showLog(String title,String value);
    void startActivityLoadPhoto();
    void init(List<Photo> photos);
    void setAdapter(List<Photo> photos);
    Activity getActivity();

}
