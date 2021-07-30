package xyz.themanusia.brot.network.jikan;

import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.themanusia.brot.network.jikan.callback.JikanAnimeCallback;
import xyz.themanusia.brot.network.jikan.callback.JikanAnimeSearchCallback;
import xyz.themanusia.brot.network.jikan.callback.JikanMangaCallback;
import xyz.themanusia.brot.network.jikan.callback.JikanMangaSearchCallback;
import xyz.themanusia.brot.network.jikan.response.Anime;
import xyz.themanusia.brot.network.jikan.response.AnimeSearch;
import xyz.themanusia.brot.network.jikan.response.Manga;
import xyz.themanusia.brot.network.jikan.response.MangaSearch;

public class JikanCore {
    private final JikanService service;

    public JikanCore() {
        RetrofitJikanService retrofitJikanService = new RetrofitJikanService();
        service = retrofitJikanService.getService();
    }

    /**
     * Get anime informtion by id from MyAnimelist
     *
     * @param id MyAnimelist id
     * @param callback JikanAnimeCallback
     */
    public void getAnimeById(int id, JikanAnimeCallback callback) {
        Call<Anime> client = service.getAnimeById(id);
        client.enqueue(new Callback<Anime>() {
            @Override
            public void onResponse(@NotNull Call<Anime> call, @NotNull Response<Anime> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        callback.onGetAnimeSuccess(response.body());
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

    /**
     * Search anime on MyAnimelist
     *
     * @param keyword anime title keyword
     * @param callback JikanAnimeSearchCallback
     */

    public void searchAnime(String keyword, JikanAnimeSearchCallback callback) {
        service.getAnimes(keyword).enqueue(new Callback<AnimeSearch>() {
            @Override
            public void onResponse(@NotNull Call<AnimeSearch> call, @NotNull Response<AnimeSearch> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResults().isEmpty()) {
                            callback.onFailure("No result");
                        } else {
                            callback.onGetAnimesSuccess(response.body());
                        }
                    } else {
                        callback.onFailure("No Results");
                    }
                } else {
                    callback.onFailure("An Error Occurred: 400 Bad Request");
                }
            }

            @Override
            public void onFailure(@NotNull Call<AnimeSearch> call, @NotNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    /**
     * Get manga by id from MyAnimeList
     *
     * @param id Manga id
     * @param callback JikanMangaCallback
     */
    public void getMangaById(int id, JikanMangaCallback callback) {
        service.getMangaById(id).enqueue(new Callback<Manga>() {
            @Override
            public void onResponse(@NotNull Call<Manga> call, @NotNull Response<Manga> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        callback.onGetMangaSuccess(response.body());
                    } else {
                        callback.onFailure("Anime not found");
                    }
                } else {
                    callback.onFailure("An Error Occurred: 400");
                }
            }

            @Override
            public void onFailure(@NotNull Call<Manga> call, @NotNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    /**
     * Search manga on MyAnimelist
     *
     * @param keyword manga title keyword
     * @param callback JikanMangaSearchCallback
     */
    public void searchManga(String keyword, JikanMangaSearchCallback callback) {
        service.getMangas(keyword).enqueue(new Callback<MangaSearch>() {
            @Override
            public void onResponse(@NotNull Call<MangaSearch> call, @NotNull Response<MangaSearch> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        callback.onGetMangasSuccess(response.body());
                    } else {
                        callback.onFailure("No Results");
                    }
                } else {
                    callback.onFailure("An Error Occurred: 400 Bad Request");
                }
            }

            @Override
            public void onFailure(@NotNull Call<MangaSearch> call, @NotNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}