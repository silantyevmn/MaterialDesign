package silantyevmn.ru.materialdesign.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import silantyevmn.ru.materialdesign.model.SettingModel;
import silantyevmn.ru.materialdesign.view.activity.SettingActivity;
import silantyevmn.ru.materialdesign.view.activity.SettingActivityView;

@InjectViewState
public class SettingPresenter extends MvpPresenter<SettingActivityView> {
    private SettingModel model;

    public SettingPresenter(SettingActivity view) {
        this.model = new SettingModel(view);
        init();
    }

    private void init() {
        getViewState().init(model.getCurrentThemePosition(), model.getCurrentSpanPosition());
    }

    public void onClick(int themePosition, int spanPosition) {
        model.saveTheme(themePosition);
        model.saveSpan(spanPosition);
        getViewState().finish();
    }
}
