package silantyevmn.ru.materialdesign.model.photo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by silan on 16.09.2018.
 */
@Database(entities = {Photo.class}, version = 1, exportSchema = false)
public abstract class PhotoBase extends RoomDatabase {
    public abstract PhotoDao photoDao();
}
