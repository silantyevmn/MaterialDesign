package silantyevmn.ru.materialdesign.model.factoty;

import java.util.ArrayList;
import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.entity.Theme;

/**
 * Created by silan on 19.08.2018.
 */

public class FactoryTheme implements IFactory<Theme> {
    private int[] id = new int[]{R.style.AppTheme, R.style.AppThemeRed, R.style.AppThemeGreen, R.style.AppThemeOrange};
    private String[] names = new String[]{"blue", "red", "green", "orange"};
    private int[] colors = new int[]{R.color.colorPrimary, R.color.colorRedPrimary, R.color.colorGreenPrimary, R.color.colorOrangePrimary};

    @Override
    public List<Theme> getList() {
        List<Theme> list = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            list.add(new Theme(id[i], names[i], colors[i]));
        }
        return list;
    }
}
