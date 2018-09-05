package silantyevmn.ru.materialdesign.model.photo;

/**
 * Created by silan on 18.08.2018.
 */

public class Photo {
    private String name;
    private boolean isFavorite;
    private String uri = null;

    public Photo(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }
}
