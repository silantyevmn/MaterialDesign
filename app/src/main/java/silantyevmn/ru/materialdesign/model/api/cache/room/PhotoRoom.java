package silantyevmn.ru.materialdesign.model.api.cache.room;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import silantyevmn.ru.materialdesign.model.api.cache.ICache;
import silantyevmn.ru.materialdesign.model.photo.Photo;


public class PhotoRoom implements ICache {
    private static PhotoRoom instance;
    private final String DATABASE_NAME = "photos.db";
    private PhotoRoomAbs database;

    private PhotoRoom(Context context) {
        this.database = Room.databaseBuilder(context, PhotoRoomAbs.class, DATABASE_NAME).allowMainThreadQueries().build();
    }

    @Override
    public Observable<List<Photo>> getList() {
        return Observable.just(database.photoDao().getList())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable insert(Photo photo) {
        return Observable.just(photo)
                .doOnNext(p -> {
                    database.photoDao().insert(p);
                }).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable delete(Photo photo) {
        return Observable.just(photo)
                .doOnNext(p -> {
                    database.photoDao().delete(p);
                }).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable update(Photo photo) {
        return Observable.just(photo)
                .doOnNext(p -> {
                    database.photoDao().update(p);
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<Photo>> getListFavorite() {
        return Observable.just(database.photoDao().getListFavorite())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void insertAll(List<Photo> photos) {
        for (Photo photo : photos) {
            database.photoDao().insert(photo);
        }
    }

    public static PhotoRoom getInstance() {
        return instance;
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new PhotoRoom(context);
        }
    }


}
