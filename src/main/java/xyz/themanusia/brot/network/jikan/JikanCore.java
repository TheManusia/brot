package xyz.themanusia.brot.network.jikan;

import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.themanusia.brot.network.RetrofitService;
import xyz.themanusia.brot.network.jikan.callback.JikanAnimeCallback;
import xyz.themanusia.brot.network.jikan.response.Anime;

public class JikanCore {
    private final JikanService service;
    private Anime anime;

    public JikanCore() {
        RetrofitService retrofitService = new RetrofitService();
        service = retrofitService.getService();
    }

    public void getAnimeById(int id, JikanAnimeCallback callback) {
        Call<Anime> client = service.getAnimeById(id);
        client.enqueue(new Callback<Anime>() {
            @Override
            public void onResponse(@NotNull Call<Anime> call, @NotNull Response<Anime> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        anime = response.body();
                        callback.onGetAnimeSuccess(anime);
                    } else {
                        callback.onFailure("Anime not found");
                    }
                } else {
                    callback.onFailure("An Error Occurred: 400");
                }
            }

            @Override
            public void onFailure(@NotNull Call<Anime> call, @NotNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}