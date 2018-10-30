package silantyevmn.ru.materialdesign.model.photo;

import android.content.Context;
import android.net.Uri;


public interface IPhotoModel extends IPhotoModelBase {

    int getGridLayoutManagerSpan(int orientation);

    Uri getUriToCamera(Context context);

    void setUriCamera(String photoUriCameraToString);

    Uri getUriToGalery(Context context, Uri uri);

}
