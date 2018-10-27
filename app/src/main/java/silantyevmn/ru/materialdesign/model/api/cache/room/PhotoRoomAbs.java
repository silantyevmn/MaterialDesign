package silantyevmn.ru.materialdesign.model.api.cache.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import silantyevmn.ru.materialdesign.model.photo.Photo;


@Database(entities = {Photo.class}, version = 1, exportSchema = false)
public abstract class PhotoRoomAbs extends RoomDatabase {
    public abstract PhotoRoomDao photoDao();
}
