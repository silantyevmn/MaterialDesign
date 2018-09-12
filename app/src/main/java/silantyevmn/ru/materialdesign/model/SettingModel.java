package silantyevmn.ru.materialdesign.model;

import android.content.Context;

import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.theme.IModelTheme;
import silantyevmn.ru.materialdesign.model.theme.ModelTheme;
import silantyevmn.ru.materialdesign.model.theme.Theme;

/**
 * Created by silan on 11.09.2018.
 */

public class SettingModel {
    private IModelTheme modelTheme;
    private DataSharedPreference sharedPreference;
    private int[] spans;

    public SettingModel(Context context) {
        modelTheme = ModelTheme.getInstance();
        sharedPreference = DataSharedPreference.getInstance();
        initSpans(context.getResources().getStringArray(R.array.grid_span_array));
    }

    private void initSpans(String[] stringArray) {
        spans = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            spans[i] = Integer.parseInt(stringArray[i]);
        }
    }

    private List<Theme> getListTheme() {
        return modelTheme.getList();
    }

    public void saveTheme(int position) {
        sharedPreference.setSettingCurrentThemePosition(position);
        sharedPreference.setCurrentTheme(getListTheme().get(position).getId());
    }

    public void saveSpan(int spanPosition) {
        sharedPreference.setSettingSpanPosition(spanPosition);
        sharedPreference.setCurrentSpan(spans[spanPosition]);
    }

    public int getCurrentSpanPosition() {
        return sharedPreference.getSettingSpanPosition();
    }

    public int getCurrentThemePosition() {
        return sharedPreference.getSettingCurrentThemePosition();
    }
}
