package xyz.themanusia.brot.network.jikan;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import xyz.themanusia.brot.network.jikan.response.Anime;

public interface JikanService {
    @GET("anime/{id}")
    Call<Anime> getAnimeById(@Path("id") int id);
}
