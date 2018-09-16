package silantyevmn.ru.materialdesign.model.photo;

import android.content.Context;
import android.net.Uri;

/**
 * Created by silan on 23.08.2018.
 */

public interface IPhotoDataFile {
    void delete(Photo photo);

    Uri getUriToCamera(Context context);

    Uri getUriToGalery(Context context, Uri uri);

    void insert(Photo photo);
}
