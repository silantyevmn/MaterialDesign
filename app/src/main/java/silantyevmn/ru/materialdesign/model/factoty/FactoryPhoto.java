package silantyevmn.ru.materialdesign.model.factoty;

import java.util.ArrayList;
import java.util.List;

import silantyevmn.ru.materialdesign.model.entity.Photo;

/**
 * Created by silan on 19.08.2018.
 */

public class FactoryPhoto implements IFactory<Photo> {
    private int[] photoId = new int[]{1, 2, 3,4,5,6,7};
    private String[] photoName = new String[]{"фото1", "фото2", "фото3", "фото4", "фото5", "фото6", "фото7"};

    @Override
    public List<Photo> getList() {
        List<Photo> list = new ArrayList<>();
        for (int i = 0; i < photoId.length; i++) {
            list.add(new Photo(photoId[i], photoName[i]));
        }
        return list;
    }
}
