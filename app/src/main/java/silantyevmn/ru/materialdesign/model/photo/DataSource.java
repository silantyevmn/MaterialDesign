package silantyevmn.ru.materialdesign.model.photo;

import java.util.List;

/**
 * Created by silan on 31.08.2018.
 */

public class DataSource implements IPhotoEmmiter {
    private FileOperation fileOperation;

    public DataSource(FileOperation fileOperation) {
        this.fileOperation = fileOperation;
        init();
    }

    @Override
    public List<Photo> getList() {
        return null;
    }

    @Override
    public void insert(Photo photo) {

    }

    @Override
    public void delete(Photo photo) {
        fileOperation.deleteFile(photo);
    }

    @Override
    public void favorites(Photo photo) {

    }
    
    public void init() {

    }
}
