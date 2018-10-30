package silantyevmn.ru.materialdesign.model.photo;

import android.content.Context;
import android.net.Uri;


public interface IPhotoDataFile extends IPhotoModelBase {
    Uri getUriToCamera(Context context);

    Uri getUriToGalery(Context context, Uri uri);
}
