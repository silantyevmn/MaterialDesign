package silantyevmn.ru.materialdesign.model.api.cache.room;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import io.reactivex.Completable;
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
        return Observable.just(database.photoDao().getList());
    }

    @Override
    public Completable insert(Photo photo) {
        return Completable.create(e->{
            database.photoDao().insert(photo);
            e.onComplete();
        });
    }

    @Override
    public Completable delete(Photo photo) {
        return Completable.create(e->{
            database.photoDao().delete(photo);
            e.onComplete();
        });
    }

    @Override
    public Completable update(Photo photo) {
        return Completable.create(e->{
            database.photoDao().update(photo);
            e.onComplete();
        });
    }

    @Override
    public Observable<List<Photo>> getListFavorite() {
        return Observable.just(database.photoDao().getListFavorite());
    }

    @Override
    public Completable insertAll(List<Photo> photos) {
        return Completable.create(e->{
            for (Photo photo : photos) {
                database.photoDao().insert(photo);
            }
            e.onComplete();
        });

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
