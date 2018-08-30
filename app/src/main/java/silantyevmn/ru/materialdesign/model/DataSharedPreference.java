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
    private final String CAMERA_URI_KEY = "photo_camera_key";
    private final String CAMERA_URI_DEFAULT = "photo_camera_default";
    private final boolean FAVORITE_DEFAULT = false;
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

    public String getUriCamera() {
        return sharedSettings.getString(CAMERA_URI_KEY, CAMERA_URI_DEFAULT);
    }

    public void setUriCamera(String uriCamera) {
        sharedSettings.edit()
                .putString(CAMERA_URI_KEY, uriCamera)
                .apply();
    }

    public void setFavorite(String nameKey, boolean favorite) {
        sharedSettings.edit()
                .putBoolean(nameKey, favorite)
                .apply();
    }

    public boolean getFavorite(String nameKey) {
        return sharedSettings.getBoolean(nameKey, FAVORITE_DEFAULT);
    }

    public void deleteFavorite(String nameKey){
        sharedSettings.edit()
        .remove(nameKey)
        .apply();
    }


}
