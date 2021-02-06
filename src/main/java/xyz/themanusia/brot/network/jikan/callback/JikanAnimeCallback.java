package xyz.themanusia.brot.network.jikan.callback;

import xyz.themanusia.brot.network.jikan.response.Anime;

public interface JikanAnimeCallback extends JikanCallback {
    void onGetAnimeSuccess(Anime anime);
}
