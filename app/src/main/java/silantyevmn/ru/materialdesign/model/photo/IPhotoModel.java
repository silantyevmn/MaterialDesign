package silantyevmn.ru.materialdesign.model.photo;

import android.content.Context;
import android.net.Uri;

import java.util.List;


public interface IPhotoModel extends IPhotoModelBase{

    int getGridLayoutManagerSpan(int orientation);

    Uri getUriToCamera(Context context);

    void setUriCamera(String photoUriCameraToString);

    Uri getUriToGalery(Context context, Uri uri);

   /* void setIdFragment(int idFragment);

    int getIdFragment();*/

}
