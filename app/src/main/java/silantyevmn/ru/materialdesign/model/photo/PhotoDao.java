package silantyevmn.ru.materialdesign.model.photo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface PhotoDao {
    @Query("SELECT * FROM photo")
    List<Photo> getList();

    @Query("SELECT * FROM photo WHERE name LIKE :name")
    Photo getPhotoByName(String name);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Photo photo);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Photo> photos);

    @Update
    void update(Photo photo);

    @Delete
    void delete(Photo photo);

    @Query("SELECT * FROM photo WHERE isFavorite")
    List<Photo> getListFavorite();
}
