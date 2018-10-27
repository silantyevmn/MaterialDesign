package silantyevmn.ru.materialdesign.model.api.cache;

import java.util.List;

import io.reactivex.Observable;
import silantyevmn.ru.materialdesign.model.photo.Photo;


public interface ICache {
    Observable<List<Photo>> getList();

    Observable insert(Photo photo);

    Observable delete(Photo photo);

    Observable update(Photo photo);

    Observable<List<Photo>> getListFavorite();

    void insertAll(List<Photo> photos);

}
