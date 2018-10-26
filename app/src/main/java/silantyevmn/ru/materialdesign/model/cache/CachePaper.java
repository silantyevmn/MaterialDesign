package silantyevmn.ru.materialdesign.model.cache;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import silantyevmn.ru.materialdesign.model.photo.Photo;


public class CachePaper implements ICache {
    private final String NAME = "photos";
    private final String KEY = "photos_key";

    @Override
    public Observable<List<Photo>> getList() {
        return Observable.create(e->{
            List<Photo> photos = Paper.book(NAME).read(KEY);
            if (photos == null) {
                photos = new ArrayList<>();
            }
            Log.i("Thread cachePaper ", Thread.currentThread().getName());
            e.onNext(photos);
            e.onComplete();
        });

    }

    @Override
    public Observable insert(Photo photo) {
        return Observable.just(photo)
                .doOnNext(p->{
                    List<Photo> photos = Paper.book(NAME).read(KEY);
                    if (photos == null) {
                        photos = new ArrayList<>();
                    }
                    photos.add(p);
                    Paper.book(NAME).write(KEY, photos);
                })
                .subscribeOn(Schedulers.io());
        /*List<Photo> photos = Paper.book(NAME).read(KEY);
        if (photos == null) {
            photos = new ArrayList<>();
        }
        photos.add(photo);
        Paper.book(NAME).write(KEY, photos);*/
    }

    @Override
    public Observable delete(Photo photo) {
        return Observable.just(photo)
                .doOnNext(p->{
                    List<Photo> photos = Paper.book(NAME).read(KEY);
                    if (photos == null) {
                        return;
                    } else {
                        photos.remove(photo);
                        Paper.book(NAME).write(KEY, photos);
                    }
                }).subscribeOn(Schedulers.io());

    }

    @Override
    public Observable update(Photo photo) {
        return Observable.just(photo)
                .doOnNext(p->{
                    List<Photo> photos = Paper.book(NAME).read(KEY);
                    if (photos != null) {
                        photos.set(photos.indexOf(photo), photo);
                        Paper.book(NAME).write(KEY, photos);
                    }
                }).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<Photo>> getListFavorite() {
        return Observable.create(e->{
            List<Photo> photos = Paper.book(NAME).read(KEY);
            List<Photo> photosFavorite=new ArrayList<>();
            if (photos == null) {
                photos = new ArrayList<>();
                //e.onError(new RuntimeException("no cache paper"));
            } else {
                photosFavorite = new ArrayList<>();
                for (Photo photo : photos) {
                    if (photo.isFavorite()) {
                        photosFavorite.add(photo);
                    }
                }
            }
           e.onNext(photosFavorite);
            e.onComplete();
        });


    }
}
