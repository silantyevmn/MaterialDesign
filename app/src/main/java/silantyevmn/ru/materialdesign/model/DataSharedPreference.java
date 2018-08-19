package silantyevmn.ru.materialdesign.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import silantyevmn.ru.materialdesign.R;

/**
 * Created by silan on 19.08.2018.
 */

public class DataSharedPreference {
    private final String SHARED_PREFERENCES_NAME = "setting";
    private final String THEME_ID_KEY = "theme_id_key";
    private final int THEME_ID_DEFAULT = R.style.AppTheme;
    private static DataSharedPreference dataSharedPreference;
    private SharedPreferences sharedSettings;

    private DataSharedPreference(Context context) {
        sharedSettings = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
    }

    public static void init(Context context) {
        if (dataSharedPreference == null) {
            dataSharedPreference = new DataSharedPreference(context);
        }
    }

    public static DataSharedPreference getInstance() {
        return dataSharedPreference;
    }

    public int getCurrentTheme() {
        return sharedSettings.getInt(THEME_ID_KEY, THEME_ID_DEFAULT);
    }

    public void setCurrentTheme(int styleId) {
        sharedSettings.edit()
                .putInt(THEME_ID_KEY, styleId)
                .apply();
    }


}
