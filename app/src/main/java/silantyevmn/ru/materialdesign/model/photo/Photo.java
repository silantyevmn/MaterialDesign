package silantyevmn.ru.materialdesign.model.photo;

import android.arch.persistence.room.*;

import org.jetbrains.annotations.NotNull;


@Entity(indices = {@Index(value = {"name"}, unique = true)})
public class Photo {

    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "isFavorite")
    private boolean isFavorite = false;

    @ColumnInfo(name = "uri")
    private String uri = null;

    public Photo(@NotNull String name, String uri) {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Photo)) return false;

        return this.name.equals(((Photo) obj).getName());
    }
}
