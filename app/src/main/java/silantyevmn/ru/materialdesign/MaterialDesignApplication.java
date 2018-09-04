package silantyevmn.ru.materialdesign;

import android.app.Application;

import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.photo.PhotoDataFile;
import silantyevmn.ru.materialdesign.model.photo.PhotoModel;
import silantyevmn.ru.materialdesign.model.theme.ModelTheme;

/**
 * Created by silan on 19.08.2018.
 */

public class MaterialDesignApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataSharedPreference.init(this);
        PhotoDataFile.init(this);
        PhotoModel.init();
        ModelTheme.init();
    }
}
