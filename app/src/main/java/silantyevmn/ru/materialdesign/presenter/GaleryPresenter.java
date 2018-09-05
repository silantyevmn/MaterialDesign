package silantyevmn.ru.materialdesign.presenter;


import silantyevmn.ru.materialdesign.model.photo.IModelPhoto;
import silantyevmn.ru.materialdesign.model.photo.PhotoModel;
import silantyevmn.ru.materialdesign.model.theme.IModelTheme;
import silantyevmn.ru.materialdesign.model.theme.ModelTheme;
import silantyevmn.ru.materialdesign.view.activity.IGaleryView;

/**
 * Created by silan on 24.08.2018.
 */

public class GaleryPresenter {
    private final IGaleryView view;
    private final IModelPhoto modelPhoto;
    private final IModelTheme modelTheme;

    public GaleryPresenter(IGaleryView view) {
        this.view = view;
        modelPhoto= PhotoModel.getInstance();
        modelTheme=ModelTheme.getInstance();
    }

    public void onCreate() {
        view.init();
        view.showFragment(modelPhoto.getList());
    }

    public void onClickMenuSetting() {
        view.showSetting(modelTheme.getList());
    }
}
