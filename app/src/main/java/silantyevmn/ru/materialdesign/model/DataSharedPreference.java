package silantyevmn.ru.materialdesign.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import silantyevmn.ru.materialdesign.R;

/**
 * Created by silan on 19.08.2018.
 */

public class DataSharedPreference {
    private final String SETTING_THEME_POSITION_KEY = "setting_theme_position_key";
    private final int SETTING_THEME_POSITION_DEFAULT = 0;
    private final String SETTING_SPAN_POSITION_KEY = "setting_span_position_key";
    private final int SETTING_SPAN_POSITION_DEFAULT = 0;
    private final String SPAN_CURRENT_KEY = "span_current_key";
    private final int SPAN_CURRENT_DEFAULT = 2;
    private final String FRAGMENT_ID_KEY = "fragment_id";
    private final int FRAGMENT_ID_DEFAULT = 0;
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

    public void deleteFavorite(String nameKey) {
        sharedSettings.edit()
                .remove(nameKey)
                .apply();
    }


    public void setIdFragment(int idFragment) {
        sharedSettings.edit()
                .putInt(FRAGMENT_ID_KEY, idFragment)
                .apply();
    }

    public int getIdFragment() {
        return sharedSettings.getInt(FRAGMENT_ID_KEY, FRAGMENT_ID_DEFAULT);
    }

    public void setCurrentSpan(int span) {
        sharedSettings.edit()
                .putInt(SPAN_CURRENT_KEY, span)
                .apply();
    }

    public int getCurrentSpan() {
        return sharedSettings.getInt(SPAN_CURRENT_KEY, SPAN_CURRENT_DEFAULT);
    }

    public void setSettingSpanPosition(int spanPosition) {
        sharedSettings.edit()
                .putInt(SETTING_SPAN_POSITION_KEY, spanPosition)
                .apply();
    }

    public int getSettingSpanPosition() {
        return sharedSettings.getInt(SETTING_SPAN_POSITION_KEY, SETTING_SPAN_POSITION_DEFAULT);
    }

    public void setSettingCurrentThemePosition(int position) {
        sharedSettings.edit()
                .putInt(SETTING_THEME_POSITION_KEY, position)
                .apply();
    }

    public int getSettingCurrentThemePosition() {
        return sharedSettings.getInt(SETTING_THEME_POSITION_KEY, SETTING_THEME_POSITION_DEFAULT);
    }
}
