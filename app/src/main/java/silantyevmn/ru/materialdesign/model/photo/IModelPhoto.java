package silantyevmn.ru.materialdesign.model.photo;

import java.util.List;

/**
 * Created by silan on 23.08.2018.
 */

public interface IModelPhoto<T> {
    List<T> getList();
    void insert(T t);
    void delete(T t);
    void favorites(T t);
}
