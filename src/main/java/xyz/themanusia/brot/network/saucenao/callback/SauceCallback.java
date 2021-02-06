package xyz.themanusia.brot.network.saucenao.callback;

import xyz.themanusia.brot.network.saucenao.response.Sauce;

public interface SauceCallback {
    void onSuccess(Sauce sauce);

    void onFailure(String message);
}
