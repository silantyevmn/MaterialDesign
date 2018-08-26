package silantyevmn.ru.materialdesign.presenter;


import android.view.MenuItem;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.photo.ModelPhoto;
import silantyevmn.ru.materialdesign.model.theme.ModelTheme;
import silantyevmn.ru.materialdesign.view.activity.IGaleryView;

/**
 * Created by silan on 24.08.2018.
 */

public class GaleryPresenter {
    private final IGaleryView mainActivityView;

    public GaleryPresenter(IGaleryView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    public void onCreate() {
        mainActivityView.init();
        mainActivityView.showFragment(ModelPhoto.getInstance().getList());
    }

    public void onClickFab() {
        //todo заглушка
    }

    public void onClickMenuSetting() {
        mainActivityView.showSetting(ModelTheme.getInstance().getList());
    }
}
