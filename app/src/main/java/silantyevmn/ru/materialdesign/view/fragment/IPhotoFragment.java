package silantyevmn.ru.materialdesign.view.fragment;

import silantyevmn.ru.materialdesign.view.recycler.PhotoAdapter;

/**
 * Created by silan on 24.08.2018.
 */

public interface IPhotoFragment {
    void showViewPhoto(int adapterPosition);
    void showLog(String title,String value);
    void startActivityLoadPhoto();
    void init();
    void setAdapter(PhotoAdapter adapter);
}
