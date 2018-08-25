package silantyevmn.ru.materialdesign.model.photo;

import java.util.List;

/**
 * Created by silan on 18.08.2018.
 */

public class ModelPhoto implements IModelPhoto<Photo> {
    private IPhotoEmmiter iPhotoEmmiter;
    private static ModelPhoto modelPhoto;

    private ModelPhoto(){
        iPhotoEmmiter =new PhotoEmmiter();
    }

    @Override
    public List<Photo> getList() {
        return iPhotoEmmiter.getList();
    }

    @Override
    public void insert(Photo photo) {
        iPhotoEmmiter.insert(photo);
    }

    @Override
    public void delete(Photo photo) {
        iPhotoEmmiter.delete(photo);
    }

    @Override
    public void favorites(Photo photo) {
        iPhotoEmmiter.favorites(photo);
    }

    public static ModelPhoto getInstance(){
        return modelPhoto;
    }

    public static void init() {
        if(modelPhoto==null){
            modelPhoto=new ModelPhoto();
        }
    }
}
