package silantyevmn.ru.materialdesign.presenter;

import silantyevmn.ru.materialdesign.model.SettingModel;
import silantyevmn.ru.materialdesign.view.activity.SettingsActivity;

/**
 * Created by silan on 11.09.2018.
 */

public class SettingPresenter {
    private SettingModel model;
    private SettingsActivity view;

    public SettingPresenter(SettingsActivity view) {
        this.view=view;
        this.model= new SettingModel(view);
        initSetting();
    }

    private void initSetting() {
        view.initSetting(model.getCurrentThemePosition(),model.getCurrentSpanPosition());
    }

    public void onClick(int themePosition, int spanPosition) {
        model.saveTheme(themePosition);
        model.saveSpan(spanPosition);
        view.recreateSetting();
    }
}
