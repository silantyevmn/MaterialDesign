package silantyevmn.ru.materialdesign.view.activity;

import java.util.List;

import silantyevmn.ru.materialdesign.model.photo.Photo;
import silantyevmn.ru.materialdesign.model.theme.Theme;

/**
 * Created by silan on 24.08.2018.
 */

public interface IGaleryView {
    void showSetting(List<Theme> themes);

    void showImportCamera();

    void showImportGalery();

    void showLog(String title, String value);

    void updateAdapter();

    void showFullPhoto(Photo photo);
}
