package silantyevmn.ru.materialdesign;

import android.app.Application;

import io.paperdb.Paper;
import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.photo.PhotoDataFile;
import silantyevmn.ru.materialdesign.model.photo.PhotoModel;
import silantyevmn.ru.materialdesign.model.api.cache.room.PhotoRoom;
import silantyevmn.ru.materialdesign.model.theme.ModelTheme;

/**
 * Created by silan on 19.08.2018.
 */

public class MaterialDesignApplication extends Application {
    private static MaterialDesignApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Paper.init(this);
        DataSharedPreference.init(this);
        PhotoRoom.init(this);
        PhotoDataFile.init(this);
        PhotoModel.init();
        ModelTheme.init();
    }

    public static MaterialDesignApplication getInstance() {
        return application;
    }

}
