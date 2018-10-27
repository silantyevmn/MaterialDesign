package silantyevmn.ru.materialdesign.model.api.retrofit;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OpenPhotoRetrofit {
    @GET("/questions")
    Call<OpenPhoto[]> getPhotos();
    //Call<WeatherRequestOneDay> loadWeather(@Query("q") String cityCountry);
}
