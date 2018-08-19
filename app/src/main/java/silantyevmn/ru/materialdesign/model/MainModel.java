package silantyevmn.ru.materialdesign.model;

import java.util.List;

import silantyevmn.ru.materialdesign.model.entity.Photo;
import silantyevmn.ru.materialdesign.model.entity.Theme;
import silantyevmn.ru.materialdesign.model.factoty.FactoryPhoto;
import silantyevmn.ru.materialdesign.model.factoty.FactoryTheme;

/**
 * Created by silan on 18.08.2018.
 */

public class MainModel {

    public List<Photo> getPhoto() {
        return new FactoryPhoto().getList();
    }

    public List<Theme> getTheme() {
        return new FactoryTheme().getList();
    }
}
