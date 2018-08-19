package silantyevmn.ru.materialdesign;

import android.app.Application;

import silantyevmn.ru.materialdesign.model.DataSharedPreference;

/**
 * Created by silan on 19.08.2018.
 */

public class Start extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DataSharedPreference.init(this);
    }
}
