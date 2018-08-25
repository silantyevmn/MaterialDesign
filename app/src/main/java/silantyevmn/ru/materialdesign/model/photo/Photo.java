package silantyevmn.ru.materialdesign.model.entity;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by silan on 18.08.2018.
 */

public class Photo implements Serializable {
    private String name;
    private boolean isFavorites;
    private Uri localUri=null;

    public Photo(String name) {
        this.name=name;
    }

    public Photo(Uri uri){
        this.localUri=uri;
    }

    public boolean isFavorites() {
        return isFavorites;
    }

    public void setFavorites(boolean favorites) {
        isFavorites = favorites;
    }

    public Uri getLocalUri() {
        return localUri;
    }

    public void setLocalUri(Uri localUri) {
        this.localUri = localUri;
    }

    public String getName() {
        return name;
    }
}
