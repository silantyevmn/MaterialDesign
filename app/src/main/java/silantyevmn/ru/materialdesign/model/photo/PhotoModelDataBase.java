package silantyevmn.ru.materialdesign.model.photo;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

/**
 * Created by silan on 16.09.2018.
 */

public class PhotoModelDataBase implements IPhotoEmmiter {
    private static PhotoModelDataBase instance;
    private final String DATABASE_NAME = "photos.db";
    private PhotoBase photoBase;

    private PhotoModelDataBase(Context context) {
        this.photoBase = Room.databaseBuilder(context, PhotoBase.class, DATABASE_NAME).allowMainThreadQueries().build();
    }

    @Override
    public List<Photo> getList() {
        return photoBase.photoDao().getList();
    }

    @Override
    public void insert(Photo photo) {
        photoBase.photoDao().insert(photo);
    }

    @Override
    public void delete(Photo photo) {
        photoBase.photoDao().delete(photo);
    }

    @Override
    public Photo favorites(Photo photo) {
        photo.setFavorite(!photo.isFavorite());
        photoBase.photoDao().update(photo);
        return photoBase.photoDao().getPhotoByName(photo.getName());
    }

    @Override
    public List<Photo> getListFavorite() {
        return photoBase.photoDao().getListFavorite();
    }

    public static PhotoModelDataBase getInstance() {
        return instance;
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new PhotoModelDataBase(context);
        }
    }


}
