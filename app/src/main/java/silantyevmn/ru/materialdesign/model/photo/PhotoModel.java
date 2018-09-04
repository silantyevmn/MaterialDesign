package silantyevmn.ru.materialdesign.model.photo;

import java.util.List;

/**
 * Created by silan on 18.08.2018.
 */

public class PhotoModel implements IModelPhoto {
    private IPhotoEmmiter photoEmmiter;
    private IPhotoEmmiter photoData;
    private static PhotoModel modelPhoto;

    private PhotoModel() {
        photoEmmiter = new PhotoEmmiter();
        photoData = PhotoDataFile.getInstance();
    }

    @Override
    public List<Photo> getList() {
        return photoEmmiter.getList();
    }

    @Override
    public void insert(Photo photo) {
        photoEmmiter.insert(photo);
        photoData.insert(photo);
    }

    @Override
    public void delete(Photo photo) {
        photoEmmiter.delete(photo);
        photoData.delete(photo);
    }

    @Override
    public void favorites(Photo photo) {
        photoEmmiter.favorites(photo);
        photoData.favorites(photo);
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
