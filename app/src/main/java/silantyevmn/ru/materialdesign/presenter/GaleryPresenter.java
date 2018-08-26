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
        mainActivityView.showFragment(ModelPhoto.getInstance().getList());
    }

    public boolean onClickMenuItem(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings: {
                mainActivityView.showSetting(ModelTheme.getInstance().getList());
                return true;
            }
            default:
                return false;
        }
    }
}
