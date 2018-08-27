package silantyevmn.ru.materialdesign.presenter;


import silantyevmn.ru.materialdesign.model.photo.ModelPhoto;
import silantyevmn.ru.materialdesign.model.theme.ModelTheme;
import silantyevmn.ru.materialdesign.view.activity.IGaleryView;

/**
 * Created by silan on 24.08.2018.
 */

public class GaleryPresenter {
    private final IGaleryView view;

    public GaleryPresenter(IGaleryView view) {
        this.view = view;
    }

    public void onCreate() {
        view.init();
        view.showFragment(ModelPhoto.getInstance().getList());
    }

    public void onClickMenuSetting() {
        view.showSetting(ModelTheme.getInstance().getList());
    }
}
