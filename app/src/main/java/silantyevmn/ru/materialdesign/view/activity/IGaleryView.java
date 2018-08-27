package silantyevmn.ru.materialdesign.view.activity;

import java.util.List;

import silantyevmn.ru.materialdesign.model.photo.Photo;
import silantyevmn.ru.materialdesign.model.theme.Theme;

/**
 * Created by silan on 24.08.2018.
 */

public interface IGaleryView {
    void showFragment(List<Photo> photos);
    void showSetting(List<Theme> themes);
    void init();
}
