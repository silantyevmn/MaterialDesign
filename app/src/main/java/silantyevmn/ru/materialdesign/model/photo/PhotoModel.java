package silantyevmn.ru.materialdesign.model.photo;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;

import java.util.List;

import silantyevmn.ru.materialdesign.model.DataSharedPreference;

/**
 * Created by silan on 18.08.2018.
 */

public class PhotoModel implements IModelPhoto {
    private IPhotoEmmiter photoEmmiter;
    private IPhotoDataFile photoData;
    private static PhotoModel modelPhoto;
    private DataSharedPreference dataSharedPreference;

    private PhotoModel() {
        photoEmmiter = new PhotoEmmiter();
        photoData = PhotoDataFile.getInstance();
        dataSharedPreference = DataSharedPreference.getInstance();
    }

    @Override
    public List<Photo> getList() {
        return photoEmmiter.getList();
    }

    @Override
    public void insert(Photo photo) {
        photoEmmiter.insert(photo);
    }

    @Override
    public void delete(Photo photo) {
        photoEmmiter.delete(photo);
        photoData.delete(photo);
        dataSharedPreference.deleteFavorite(photo.getName());
    }

    @Override
    public void favorite(Photo photo) {
        Photo tempPhoto = photoEmmiter.favorites(photo);
        dataSharedPreference.setFavorite(tempPhoto.getName(), tempPhoto.isFavorite());
    }

    @Override
    public List<Photo> getListFavorite() {
        return photoEmmiter.getListFavorite();
    }

    // пока реализовал таким способом, с дальнейщей возможностью вынести количество фото в настройки
    @Override
    public int getGridLayoutManagerSpan(int orientation) {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return 2;
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return 3;
        }
        return 2;
    }

    @Override
    public Uri getUriToCamera(Context context) {
        return photoData.getUriToCamera(context);
    }

    @Override
    public void setUriCamera(String photoUriCameraToString) {
        //записывает имя файла в память
        dataSharedPreference.setUriCamera(photoUriCameraToString);
    }

    @Override
    public Uri getUriToGalery(Context context, Uri uri) {
        return photoData.getUriToGalery(context, uri);
    }

    @Override
    public void setIdFragment(int idFragment) {
        dataSharedPreference.setIdFragment(idFragment);
    }

    @Override
    public int getIdFragment() {
        return dataSharedPreference.getIdFragment();
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
