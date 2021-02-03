package xyz.themanusia.brot.network.jikan;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import xyz.themanusia.brot.network.jikan.response.Anime;
import xyz.themanusia.brot.network.jikan.response.AnimeSearch;

public interface JikanService {
    @GET("anime/{id}")
    Call<Anime> getAnimeById(@Path("id") int id);

    @GET("search/anime")
    Call<AnimeSearch> getAnimes(@Query("q") String keyword);
}
