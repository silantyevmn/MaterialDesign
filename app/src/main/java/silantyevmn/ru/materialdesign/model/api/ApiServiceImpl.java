package silantyevmn.ru.materialdesign.model.api;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import silantyevmn.ru.materialdesign.model.NetworkStatus;
import silantyevmn.ru.materialdesign.model.api.cache.ICache;
import silantyevmn.ru.materialdesign.model.api.cache.paper.CachePaper;
import silantyevmn.ru.materialdesign.model.api.cache.room.PhotoRoom;
import silantyevmn.ru.materialdesign.model.api.retrofit.OpenPhotoRetrofitImpl;
import silantyevmn.ru.materialdesign.model.photo.Photo;


public class ApiServiceImpl implements ApiService, ICache {
    private ICache cache;
    private ApiService api;
    private boolean isComplete = false;

    public ApiServiceImpl() {
        cache = PhotoRoom.getInstance();
        api = new OpenPhotoRetrofitImpl();
    }

    @Override
    public Observable<List<Photo>> getList() {
        if (NetworkStatus.isOnline() && !isComplete) {
            return api.getList().subscribeOn(Schedulers.io())
                    .map(list -> {
                        List<Photo> newList = cache.getList().blockingFirst();
                        if (newList != null && newList.size() > 0)
                            for (Photo photo : newList) {
                                if (list.contains(photo)) {
                                    list.set(list.indexOf(photo), photo);
                                } else {
                                    list.add(photo);
                                }
                            }
                        isComplete = true;
                        cache.insertAll(list);
                        return list;
                    });
        } else {
            return cache.getList();
        }
    }

    @Override
    public Observable insert(Photo photo) {
        return cache.insert(photo);
    }

    @Override
    public Observable delete(Photo photo) {
        return cache.delete(photo);
    }

    @Override
    public Observable update(Photo photo) {
        return cache.update(photo);
    }

    @Override
    public Observable<List<Photo>> getListFavorite() {
        return cache.getListFavorite();
    }

    @Override
    public void insertAll(List<Photo> photos) {
        //
    }

}
