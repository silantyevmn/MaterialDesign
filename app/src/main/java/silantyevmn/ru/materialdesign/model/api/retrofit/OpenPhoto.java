package silantyevmn.ru.materialdesign.model.api.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OpenPhoto {
     /*"id":1,
             "name":"Ivan",
             "url":"https://www.worldpresidentsdb.com/images/presidents/vladimir-putin.jpg"*/

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("surname")
    @Expose
    private String surname;

    @SerializedName("urlPhoto")
    @Expose
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
