package silantyevmn.ru.materialdesign.model.photo;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


public class PhotoModelDataBase implements IPhotoModelBase {
    private static PhotoModelDataBase instance;
    private final String DATABASE_NAME = "photos.db";
    private PhotoBase photoBase;

    private PhotoModelDataBase(Context context) {
        this.photoBase = Room.databaseBuilder(context, PhotoBase.class, DATABASE_NAME).allowMainThreadQueries().build();
    }

    @Override
    public Observable<List<Photo>> getList() {
        return Observable.just(photoBase.photoDao().getList());
    }

    @Override
    public Observable insert(Photo photo) {
        return Observable.just(photo)
                .doOnNext(p -> {
                    photoBase.photoDao().insert(p);
                }).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable delete(Photo photo) {
        return Observable.just(photo)
                .doOnNext(p -> {
                    photoBase.photoDao().delete(photo);
                }).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable update(Photo photo) {
        return Observable.just(photo)
                .doOnNext(p -> {
                    photoBase.photoDao().update(photo);
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<Photo>> getListFavorite() {
        return Observable.just(photoBase.photoDao().getListFavorite());
    }

    public static PhotoModelDataBase getInstance() {
        return instance;
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new PhotoModelDataBase(context);
        }
    }


}
