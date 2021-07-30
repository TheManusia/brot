package xyz.themanusia.brot.network.tracemoe;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Callback;
import xyz.themanusia.brot.network.tracemoe.callback.TraceMoeCallback;
import xyz.themanusia.brot.network.tracemoe.response.Response;
import xyz.themanusia.brot.network.tracemoe.response.Sauce;

import java.util.ArrayList;

public class TraceMoeCore {
    private final TraceMoeService traceMoeService;
    private final static Logger logger = LoggerFactory.getLogger(TraceMoeCore.class);

    public TraceMoeCore() {
        RetrofitTraceMoeService retrofitTraceMoeService = new RetrofitTraceMoeService();
        traceMoeService = retrofitTraceMoeService.getService();
    }

    public void getSauce(String image, TraceMoeCallback callback) {
        traceMoeService.getSauce(image).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NotNull Call<Response> call, retrofit2.@NotNull Response<Response> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            ArrayList<Sauce> sauces = response.body().getSauces();
                            if (!sauces.isEmpty()) {
                                callback.onSuccess(sauces.get(0));
                            } else {
                                callback.onFailure("Sauce not found");
                            }
                        } else {
                            callback.onFailure("Sauce not found");
                        }
                    } else {
                        callback.onFailure("An Error Occurred: 500");
                        logger.error(response.message());
                    }
                } catch (Exception e) {
                    callback.onFailure(e.getMessage());
                    logger.error(e.getMessage(), e);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Response> call, @NotNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}
