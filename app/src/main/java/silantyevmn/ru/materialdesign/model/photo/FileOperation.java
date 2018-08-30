package silantyevmn.ru.materialdesign.model.photo;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by silan on 29.08.2018.
 */

public class FileOperation {
    private static FileOperation fileOperation;
    private final File storageDIR;
    private final String path="content://com.example.android.provider/my_images/";
    private File photoFile;

    public File getStorageDir() {
        return storageDIR;
    }

    public String getPath() {
        return path;
    }

    private FileOperation(Context context) {
        this.storageDIR = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);;
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

    public static FileOperation getInstance(){
        return fileOperation;
    }

    public static void init(Context context){
        if(fileOperation==null){
            fileOperation=new FileOperation(context);
        }
    }

    public Uri getUri(File photoFile) {
        return Uri.parse(path+photoFile.getName());
    }

    public void deleteFile(Photo photo) {
        for (File file : storageDIR.listFiles()) {
            if(file.getName().equals(photo.getName())){
                file.delete();
                return;
            }
        }
    }

    public boolean deleteNewFile() {
        return photoFile.delete();
    }

    public void insert(Photo photo) {
        File newFile= new File(photo.getName());


    }
}
