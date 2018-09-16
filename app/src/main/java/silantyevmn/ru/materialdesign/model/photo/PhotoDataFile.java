package silantyevmn.ru.materialdesign.model.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import silantyevmn.ru.materialdesign.R;

/**
 * Created by silan on 29.08.2018.
 */

public class PhotoDataFile implements IPhotoDataFile {
    private static PhotoDataFile fileOperation;
    private final File storageDIR;
    private File photoFile;

    private PhotoDataFile(Context context) {
        this.storageDIR = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    public Uri getUriToFileProvider(Context context, File file) {
        return FileProvider.getUriForFile(context, context.getString(R.string.file_provider), file);
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
        return Uri.fromFile(photoFile);
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
    public void update(Photo photo) {

    }

    @Override
    public List<Photo> getListFavorite() {
        return null;
    }

    @Override
    public Uri getUriToCamera(Context context) {
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (photoFile != null) {
            return getUriToFileProvider(context, photoFile);
        }
        return null;
    }

    @Override
    public Uri getUriToGalery(Context context, Uri uri) {
        Bitmap bitmap = null;
        File file = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            file = createImageFile();
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes.toByteArray());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bitmap == null || file == null) {
            return null;
        } else {
            return Uri.fromFile(file);
        }
    }

    @Override
    public List<Photo> getList() {
        return null;
    }

    @Override
    public void insert(Photo photo) {
        //
    }

    public static PhotoDataFile getInstance() {
        return fileOperation;
    }

    public static void init(Context context) {
        if (fileOperation == null) {
            fileOperation = new PhotoDataFile(context);
        }
    }


}
