package silantyevmn.ru.materialdesign.model.photo;

import java.util.List;

import io.reactivex.Observable;


public interface IPhotoModelBase {
    Observable<List<Photo>> getList();

    Observable insert(Photo photo);

    Observable delete(Photo photo);

    Observable update(Photo photo);

    Observable<List<Photo>> getListFavorite();
}
