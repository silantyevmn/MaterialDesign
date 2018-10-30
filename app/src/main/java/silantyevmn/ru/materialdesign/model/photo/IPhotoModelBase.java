package silantyevmn.ru.materialdesign.model.photo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;


public interface IPhotoModelBase {
    Observable<List<Photo>> getList();

    Completable insert(Photo photo);

    Completable delete(Photo photo);

    Completable update(Photo photo);

    Observable<List<Photo>> getListFavorite();
}
