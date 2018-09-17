package silantyevmn.ru.materialdesign.model.photo;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;

import java.util.List;

import silantyevmn.ru.materialdesign.model.DataSharedPreference;

/**
 * Created by silan on 18.08.2018.
 */

public class PhotoModel implements IPhotoModel {
    private IPhotoDataFile dataFile;
    private static PhotoModel modelPhoto;
    private DataSharedPreference dataSharedPreference;
    private PhotoModelDataBase dataBase;

    private PhotoModel() {
        dataFile = PhotoDataFile.getInstance();
        dataBase = PhotoModelDataBase.getInstance();
        dataSharedPreference = DataSharedPreference.getInstance();
    }

    @Override
    public List<Photo> getList() {
        return dataBase.getList();
    }

    @Override
    public void insert(Photo photo) {
        dataFile.insert(photo);
        dataBase.insert(photo);
    }

    @Override
    public void delete(Photo photo) {
        dataFile.delete(photo);
        dataBase.delete(photo);
    }

    @Override
    public void update(Photo photo) {
        photo.setFavorite(!photo.isFavorite());
        dataBase.update(photo);
        dataSharedPreference.setFavorite(photo.getName(), photo.isFavorite());
    }

    @Override
    public List<Photo> getListFavorite() {
        return dataBase.getListFavorite();
    }

    // пока реализовал таким способом, с дальнейщей возможностью вынести количество фото в настройки
    @Override
    public int getGridLayoutManagerSpan(int orientation) {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return dataSharedPreference.getCurrentSpan();
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return dataSharedPreference.getCurrentSpan()+1;
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

/*    @Override
    public void setIdFragment(int idFragment) {
        dataSharedPreference.setIdFragment(idFragment);
    }

    @Override
    public int getIdFragment() {
        return dataSharedPreference.getIdFragment();
    }*/

    public static PhotoModel getInstance() {
        return modelPhoto;
    }

    public static void init() {
        if (modelPhoto == null) {
            modelPhoto = new PhotoModel();
        }
    }
}
