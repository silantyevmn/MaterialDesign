package silantyevmn.ru.materialdesign.model.photo;

import java.util.List;

/**
 * Created by silan on 23.08.2018.
 */

public interface IModelPhoto {
    List<Photo> getList();
    void insert(Photo photo);
    void delete(Photo photo);
    void favorites(Photo photo);

    List<Photo> getListFavorite();

    int getGridLayoutManagerSpan(int orientation);
}
