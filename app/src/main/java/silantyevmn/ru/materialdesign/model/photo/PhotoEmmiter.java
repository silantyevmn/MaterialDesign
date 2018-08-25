package silantyevmn.ru.materialdesign.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silan on 19.08.2018.
 */

public class PhotoEmmiter implements IPhotoEmmiter<Photo> {
    private String[] photoName = new String[]{"фото1", "фото2", "фото3", "фото4", "фото5", "фото6", "фото7"};
    private List<Photo> arrays;

    public PhotoEmmiter(){
        init();
    }

    @Override
    public List<Photo> getList() {
        return arrays;
    }

    public void init() {
        arrays = new ArrayList<>();
        for (int i = 0; i < photoName.length; i++) {
            arrays.add(new Photo(photoName[i]));
        }
    }

    @Override
    public void delete(Photo photo) {
        arrays.remove(photo);
    }

    @Override
    public void favorites(Photo photo) {
        int position=arrays.indexOf(photo);
        photo.setFavorites(!photo.isFavorites());
        arrays.set(position,photo);
    }


    @Override
    public void insert(Photo photo) {
        arrays.add(photo);
    }


}
