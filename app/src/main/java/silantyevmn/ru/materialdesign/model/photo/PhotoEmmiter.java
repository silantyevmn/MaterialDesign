package silantyevmn.ru.materialdesign.model.photo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import silantyevmn.ru.materialdesign.model.DataSharedPreference;

/**
 * Created by silan on 19.08.2018.
 */

public class PhotoEmmiter implements IPhotoEmmiter {
    private List<Photo> arrays;
    private PhotoDataFile fileOperation;

    public PhotoEmmiter() {
        fileOperation = PhotoDataFile.getInstance();
        init();
    }

    @Override
    public List<Photo> getList() {
        return arrays;
    }

    public void init() {
        arrays = new ArrayList<>();
        for (File file : fileOperation.getStorageDir().listFiles()) {
            Photo photo = new Photo(file.getName(), fileOperation.getUri(file).toString());
            boolean favorite = DataSharedPreference.getInstance().getFavorite(photo.getName());
            if (favorite) {
                photo.setFavorite(true);
            } else
                photo.setFavorite(false);
            arrays.add(photo);
        }
    }

    @Override
    public void delete(Photo photo) {
        arrays.remove(photo);
    }

    @Override
    public Photo favorites(Photo photo) {
        int position = arrays.indexOf(photo);
        photo.setFavorite(!photo.isFavorite());
        arrays.set(position, photo);
        return photo;
    }

    @Override
    public List<Photo> getListFavorite() {
        List<Photo> photoFavoriteList = new ArrayList<>();
        for (Photo photo : arrays) {
            if (photo.isFavorite()) {
                photoFavoriteList.add(photo);
            }
        }
        return photoFavoriteList;
    }


    @Override
    public void insert(Photo photo) {
        arrays.add(photo);
    }


}
