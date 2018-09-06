package silantyevmn.ru.materialdesign.model.photo;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import silantyevmn.ru.materialdesign.R;

/**
 * Created by silan on 29.08.2018.
 */

public class PhotoDataFile implements IPhotoEmmiter {
    private static PhotoDataFile fileOperation;
    private final File storageDIR;
    //private final String path = "content://com.example.android.provider/my_images/";
    private File photoFile;

    private PhotoDataFile(Context context) {
        this.storageDIR = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    public Uri getUriToFileProvider(Context context,File file){
        return FileProvider.getUriForFile(context, context.getString(R.string.file_provider),photoFile);
    }

    public File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        photoFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDIR      /* directory */
        );
        return photoFile;
    }

    public Uri getUri(File photoFile) {
        //return Uri.parse(path + photoFile.getName());
        return Uri.fromFile(photoFile);
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
        for (File file : storageDIR.listFiles()) {
            if (file.getName().equals(photo.getName())) {
                file.delete();
                return;
            }
        }
    }

    @Override
    public void favorites(Photo photo) {

    }

    @Override
    public List<Photo> getListFavorite() {
        return null;
    }

    public static PhotoDataFile getInstance() {
        return fileOperation;
    }

    public static void init(Context context) {
        if (fileOperation == null) {
            fileOperation = new PhotoDataFile(context);
        }
    }

    public File getStorageDir() {
        return storageDIR;
    }
}
