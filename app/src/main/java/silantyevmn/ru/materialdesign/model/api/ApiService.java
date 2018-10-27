package silantyevmn.ru.materialdesign.model.api;

import java.util.List;

import io.reactivex.Observable;
import silantyevmn.ru.materialdesign.model.photo.Photo;


public interface ApiService {
    Observable<List<Photo>> getList();
}
