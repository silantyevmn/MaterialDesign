package silantyevmn.ru.materialdesign.model.cache;

import java.util.List;
import io.reactivex.Observable;

import silantyevmn.ru.materialdesign.model.photo.Photo;


public interface ICache {
    Observable<List<Photo>> getList();

    Observable<List<Photo>> getListFavorite();

    Observable insert(Photo photo);

    Observable delete(Photo photo);

    Observable update(Photo photo);
}
