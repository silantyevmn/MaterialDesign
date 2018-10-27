package silantyevmn.ru.materialdesign.model.photo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {Photo.class}, version = 1, exportSchema = false)
public abstract class PhotoBase extends RoomDatabase {
    public abstract PhotoDao photoDao();
}
