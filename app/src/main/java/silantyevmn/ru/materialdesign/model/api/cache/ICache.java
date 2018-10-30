package silantyevmn.ru.materialdesign.model.api.cache;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import silantyevmn.ru.materialdesign.model.photo.Photo;


public interface ICache {
    Observable<List<Photo>> getList();

    Completable insert(Photo photo);

    Completable delete(Photo photo);

    Completable update(Photo photo);

    Observable<List<Photo>> getListFavorite();

    Completable insertAll(List<Photo> photos);

}
