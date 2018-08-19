package silantyevmn.ru.materialdesign.model.factoty;

import java.util.List;

/**
 * Created by silan on 18.08.2018.
 */

public interface IFactory<T> {
    List<T> getList();
}
