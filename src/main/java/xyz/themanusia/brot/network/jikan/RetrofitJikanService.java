package xyz.themanusia.brot.network.jikan;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitJikanService {
    private static final String URL = "https://api.jikan.moe/v3/";

    public JikanService getService() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        Retrofit retrofit = new Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(JikanService.class);
    }
}
