package silantyevmn.ru.materialdesign.model.theme;

import java.util.ArrayList;
import java.util.List;

import silantyevmn.ru.materialdesign.R;

/**
 * Created by silan on 19.08.2018.
 */

public class ThemeEmmiter implements IModelTheme<Theme> {
    private int[] id = new int[]{R.style.AppTheme, R.style.AppThemeRed, R.style.AppThemeGreen, R.style.AppThemeOrange};
    private String[] names = new String[]{"blue", "red", "green", "orange"};
    private int[] colors = new int[]{R.color.colorPrimary, R.color.colorRedPrimary, R.color.colorGreenPrimary, R.color.colorOrangePrimary};
    private List<Theme> arrays;

    public ThemeEmmiter() {
        init();
    }

    @Override
    public List<Theme> getList() {
        return arrays;
    }

    public void init() {
        arrays = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            arrays.add(new Theme(id[i], names[i], colors[i]));
        }
    }
}
