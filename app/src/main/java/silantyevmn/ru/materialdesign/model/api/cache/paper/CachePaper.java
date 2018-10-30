package silantyevmn.ru.materialdesign.model.api.cache.paper;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import silantyevmn.ru.materialdesign.model.api.cache.ICache;
import silantyevmn.ru.materialdesign.model.photo.Photo;


public class CachePaper implements ICache {
    private final String NAME = "photos";
    private final String KEY = "photos_key";

    @Override
    public Observable<List<Photo>> getList() {
        return Observable.just(new ArrayList<Photo>())
                .map(list -> {
                    List<Photo> photos = Paper.book(NAME).read(KEY);
                    if (photos == null) {
                        photos = list;
                    }
                    return photos;
                });

    }

    @Override
    public Completable insert(Photo photo) {
        return Completable.create(e->{
            List<Photo> photos = Paper.book(NAME).read(KEY);
            if (photos == null) {
                photos = new ArrayList<>();
            }
            photos.add(photo);
            Paper.book(NAME).write(KEY, photos);
            e.onComplete();
        });
    }


    @Override
    public Completable delete(Photo photo) {
        return Completable.create(e->{
            List<Photo> photos = Paper.book(NAME).read(KEY);
            if (photos == null) {
                return;
            } else {
                photos.remove(photo);
                Paper.book(NAME).write(KEY, photos);
            }
            e.onComplete();
        });
    }

    @Override
    public Completable update(Photo photo) {
        return Completable.create(e->{
            List<Photo> photos = Paper.book(NAME).read(KEY);
            if (photos != null) {
                photos.set(photos.indexOf(photo), photo);
                Paper.book(NAME).write(KEY, photos);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<List<Photo>> getListFavorite() {
        return Observable.just(new ArrayList<Photo>())
                .map(list -> {
                    List<Photo> photos = Paper.book(NAME).read(KEY);
                    List<Photo> photosFavorite = new ArrayList<>();
                    if (photos == null) {
                        photos = list;
                    } else {
                        photosFavorite = new ArrayList<>();
                        for (Photo photo : photos) {
                            if (photo.isFavorite()) {
                                photosFavorite.add(photo);
                            }
                        }
                    }
                    return photosFavorite;
                });
    }

    @Override
    public Completable insertAll(List<Photo> photos) {
        return Completable.create(e->{
            Paper.book(NAME).write(KEY, photos);
            e.onComplete();
        });


    }

}
