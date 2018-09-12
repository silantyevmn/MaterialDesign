package silantyevmn.ru.materialdesign.model.photo;

import java.util.List;

/**
 * Created by silan on 23.08.2018.
 */

public interface IPhotoEmmiter {
    List<Photo> getList();

    void insert(Photo photo);

    void delete(Photo photo);

    Photo favorites(Photo photo);

    List<Photo> getListFavorite();
}
