package xyz.themanusia.brot.network.saucenao;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSaucenaoService {
    private static final String URL = "https://saucenao.com/";

    public SauceNaoService getService() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(SauceNaoService.class);
    }
}