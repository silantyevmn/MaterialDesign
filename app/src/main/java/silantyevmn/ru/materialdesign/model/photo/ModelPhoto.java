package silantyevmn.ru.materialdesign.model.photo;

import java.util.List;

/**
 * Created by silan on 18.08.2018.
 */

public class ModelPhoto implements IModelPhoto {
    private IPhotoEmmiter photoEmmiter;
    private IPhotoEmmiter data;
    private static ModelPhoto modelPhoto;

    private ModelPhoto() {
        photoEmmiter = new PhotoEmmiter();
        data=new DataSource(FileOperation.getInstance());
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
        data.delete(photo);
    }

    @Override
    public void favorites(Photo photo) {
        photoEmmiter.favorites(photo);
    }

    public static ModelPhoto getInstance() {
        return modelPhoto;
    }

    public static void init() {
        if (modelPhoto == null) {
            modelPhoto = new ModelPhoto();
        }
    }
}
