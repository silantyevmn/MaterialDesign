package silantyevmn.ru.materialdesign.model.api.retrofit;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import silantyevmn.ru.materialdesign.model.api.ApiService;
import silantyevmn.ru.materialdesign.model.photo.Photo;


public class OpenPhotoRetrofitImpl implements ApiService {
    private OpenPhotoRetrofit openRetrofit;
    private final String OPEN_MAP_URL = "http://private-c89fa-photo7.apiary-mock.com/";
    //private final int OPEN_COD_GOOD = 200;

    public OpenPhotoRetrofitImpl() {
        initRetorfit();
    }

    private void initRetorfit() {
        Retrofit retrofit = new Retrofit.Builder()
                // Базовая часть адреса
                .baseUrl(OPEN_MAP_URL)
                // Конвертер, необходимый для преобразования JsonData в объекты
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient())
                .build();
        // Создаем объект, при помощи которого будем выполнять запросы
        openRetrofit = retrofit.create(OpenPhotoRetrofit.class);
    }

    private OkHttpClient createOkHttpClient() {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        return httpClient.build();
    }

    public Observable getList() {
        return Observable.create(e -> {
            List<Photo> list = new ArrayList<>();
            openRetrofit.getPhotos()
                    .enqueue(new Callback<OpenPhoto[]>() {
                        @Override
                        public void onResponse(Call<OpenPhoto[]> call, Response<OpenPhoto[]> response) {
                            if (response.body() != null) {
                                OpenPhoto[] openPhoto = response.body();
                                for (OpenPhoto photo : openPhoto) {
                                    list.add(new Photo(photo.getName().toUpperCase().charAt(0) + ". " + photo.getSurname(), photo.getUrl()));
                                }
                                e.onNext(list);
                                e.onComplete();
                            } else
                                e.onError(new RuntimeException("response.body not null"));
                        }

                        @Override
                        public void onFailure(Call<OpenPhoto[]> call, Throwable t) {
                            e.onError(new RuntimeException("load photo error: " + t.getMessage()));
                        }
                    });

        });
    }
}
