package silantyevmn.ru.materialdesign.model.photo;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;

import java.util.List;

import io.reactivex.Observable;
import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.api.ApiService;
import silantyevmn.ru.materialdesign.model.api.ApiServiceImpl;


public class PhotoModel implements IPhotoModel {
    private static PhotoModel modelPhoto;
    private IPhotoDataFile dataFile;
    private DataSharedPreference dataSharedPreference;

    private ApiServiceImpl apiService;

    private PhotoModel() {
        dataFile = PhotoDataFile.getInstance();
        dataSharedPreference = DataSharedPreference.getInstance();
        apiService = new ApiServiceImpl();
    }

    @Override
    public Observable<List<Photo>> getList() {
        return apiService.getList();
    }

    @Override
    public Observable insert(Photo photo) {
        return apiService.insert(photo);
    }

    @Override
    public Observable delete(Photo photo) {
        return apiService.delete(photo);
    }

    @Override
    public Observable update(Photo photo) {
        return apiService.update(photo);
    }

    @Override
    public Observable<List<Photo>> getListFavorite() {
        return apiService.getListFavorite();
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
