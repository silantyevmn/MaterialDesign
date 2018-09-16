package silantyevmn.ru.materialdesign.model.photo;

import java.util.List;


public interface IPhotoModelBase {
    List<Photo> getList();

    void insert(Photo photo);

    void delete(Photo photo);

    void update(Photo photo);

    List<Photo> getListFavorite();
}
