package silantyevmn.ru.materialdesign.model.photo;

import android.net.Uri;

/**
 * Created by silan on 18.08.2018.
 */

public class Photo {
    private String name;
    private boolean isFavorite;
    private Uri uri=null;

    public Photo(String name) {
        this.name = name;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }
}
