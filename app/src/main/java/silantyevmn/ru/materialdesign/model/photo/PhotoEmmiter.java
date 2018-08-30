package silantyevmn.ru.materialdesign.model.photo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import silantyevmn.ru.materialdesign.model.DataSharedPreference;

/**
 * Created by silan on 19.08.2018.
 */

public class PhotoEmmiter implements IPhotoEmmiter {
    private List<Photo> arrays;
    private FileOperation fileOperation;

    public PhotoEmmiter(){
        fileOperation=FileOperation.getInstance();
        init();
    }

    @Override
    public List<Photo> getList() {
        return arrays;
    }

    public void init() {
        //content://com.example.android.provider/my_images/JPEG_20180829_143946_1411028100.jpg
        arrays = new ArrayList<>();
        for (File file : fileOperation.getStorageDir().listFiles()) {
            Photo photo=new Photo(file.getName(),fileOperation.getUri(file).toString());
            boolean favorite=DataSharedPreference.getInstance().getFavorite(photo.getName());
            if(favorite){
                photo.setFavorite(true);
            } else
                photo.setFavorite(false);
            arrays.add(photo);
        }
    }

    @Override
    public void delete(Photo photo) {
        arrays.remove(photo);
        fileOperation.deleteFile(photo);
        DataSharedPreference.getInstance().deleteFavorite(photo.getName());
    }

    @Override
    public void favorites(Photo photo) {
        int position=arrays.indexOf(photo);
        photo.setFavorite(!photo.isFavorite());
        arrays.set(position,photo);
        DataSharedPreference.getInstance().setFavorite(photo.getName(),photo.isFavorite());
    }


    @Override
    public void insert(Photo photo) {
        arrays.add(photo);
        fileOperation.insert(photo);
    }


}
