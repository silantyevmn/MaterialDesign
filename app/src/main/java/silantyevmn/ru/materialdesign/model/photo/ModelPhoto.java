package silantyevmn.ru.materialdesign.model;

import java.util.List;

import silantyevmn.ru.materialdesign.model.entity.IPhotoEmmiter;
import silantyevmn.ru.materialdesign.model.entity.Photo;
import silantyevmn.ru.materialdesign.model.entity.PhotoEmmiter;

/**
 * Created by silan on 18.08.2018.
 */

public class ModelPhoto implements IModelPhoto<Photo> {
    private IPhotoEmmiter iModel;
    private static ModelPhoto modelPhoto;

    private ModelPhoto(){
        iModel=new PhotoEmmiter();
    }

    @Override
    public List<Photo> getList() {
        return iModel.getList();
    }

    @Override
    public void insert(Photo photo) {
        iModel.insert(photo);
    }

    @Override
    public void delete(Photo photo) {
        iModel.delete(photo);
    }

    @Override
    public void favorites(Photo photo) {
        iModel.favorites(photo);
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
