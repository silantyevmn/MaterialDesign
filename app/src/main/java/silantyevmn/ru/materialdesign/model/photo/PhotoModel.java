package silantyevmn.ru.materialdesign.model.photo;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;

import java.util.List;

import io.reactivex.Observable;
import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.cache.CachePaper;
import silantyevmn.ru.materialdesign.model.cache.ICache;


public class PhotoModel implements IPhotoModel {
    private IPhotoDataFile dataFile;
    private static PhotoModel modelPhoto;
    private DataSharedPreference dataSharedPreference;
    private PhotoModelDataBase dataBase;
    //
    private ICache cache;

    private PhotoModel() {
        dataFile = PhotoDataFile.getInstance();
        dataBase = PhotoModelDataBase.getInstance();
        dataSharedPreference = DataSharedPreference.getInstance();
        cache = new CachePaper();
    }

    @Override
    public Observable<List<Photo>> getList() {
        //if online
        //return internet api
        return cache.getList();
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
        //return dataBase.getListFavorite();
    }

    // пока реализовал таким способом, с дальнейщей возможностью вынести количество фото в настройки
    @Override
    public int getGridLayoutManagerSpan(int orientation) {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return dataSharedPreference.getCurrentSpan();
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return dataSharedPreference.getCurrentSpan() + 1;
        }
        return dataSharedPreference.getCurrentSpan();
    }

    @Override
    public Uri getUriToCamera(Context context) {
        return dataFile.getUriToCamera(context);
    }

    @Override
    public void setUriCamera(String photoUriCameraToString) {
        //записывает имя файла в память
        dataSharedPreference.setUriCamera(photoUriCameraToString);
    }

    @Override
    public Uri getUriToGalery(Context context, Uri uri) {
        return dataFile.getUriToGalery(context, uri);
    }

    public static PhotoModel getInstance() {
        return modelPhoto;
    }

    public static void init() {
        if (modelPhoto == null) {
            modelPhoto = new PhotoModel();
        }
    }
}
