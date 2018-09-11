package silantyevmn.ru.materialdesign.view.fragment;

import java.util.List;

import silantyevmn.ru.materialdesign.model.photo.Photo;

/**
 * Created by silan on 24.08.2018.
 */

public interface IPhotoFragment extends IPhotoFragmentUpdateAdapter {

    void showLog(String title, String value);

    void init(List<Photo> photos, int gridLayoutManagerSpan);

    void setAdapter(List<Photo> photos);
}
