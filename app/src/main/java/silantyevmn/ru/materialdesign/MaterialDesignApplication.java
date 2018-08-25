package silantyevmn.ru.materialdesign;

import android.app.Application;

import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.ModelPhoto;
import silantyevmn.ru.materialdesign.model.ModelTheme;

/**
 * Created by silan on 19.08.2018.
 */

public class Start extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ModelPhoto.init();
        ModelTheme.init();
        DataSharedPreference.init(this);
    }
}
