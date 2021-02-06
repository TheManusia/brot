package xyz.themanusia.brot.network.tracemoe.callback;

import xyz.themanusia.brot.network.tracemoe.response.Sauce;

public interface TraceMoeCallback {
    void onSuccess(Sauce sauce);

    void onFailure(String message);
}
