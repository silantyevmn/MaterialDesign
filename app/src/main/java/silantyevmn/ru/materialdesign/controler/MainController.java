package silantyevmn.ru.materialdesign.controler;

import java.util.List;

import silantyevmn.ru.materialdesign.model.MainModel;
import silantyevmn.ru.materialdesign.model.entity.Photo;
import silantyevmn.ru.materialdesign.model.entity.Theme;
import silantyevmn.ru.materialdesign.view.activity.MainActivity;

/**
 * Created by silan on 18.08.2018.
 */

public class MainController {
    private SetViewActivity viewActivity;
    private MainModel model;

    public void settingsSelected() {
        List<Theme> themes = model.getTheme();
        if (viewActivity != null) {
            viewActivity.onShowActivitySetting(themes);
        }
    }

    public interface SetViewActivity {

        void onShowFragmentPhotos(List<Photo> photos);

        void onShowActivitySetting(List<Theme> theme);

    }

    public MainController(MainActivity activity) {
        this.model = new MainModel();
        this.viewActivity = (SetViewActivity) activity;
        loadImages();
    }

    public void setViewActivity(SetViewActivity viewActivity) {
        this.viewActivity = viewActivity;
    }

    private void loadImages() {
        List<Photo> photos = model.getPhoto();
        if (viewActivity != null) {
            viewActivity.onShowFragmentPhotos(photos);
        }
    }
}
