package xyz.themanusia.brot.network.tracemoe;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xyz.themanusia.brot.network.tracemoe.response.Response;

public interface TraceMoeService {

    @GET("search")
    Call<Response> getSauce(@Query("url") String image);
}
