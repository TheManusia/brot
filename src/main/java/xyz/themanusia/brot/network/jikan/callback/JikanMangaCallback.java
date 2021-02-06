package xyz.themanusia.brot.network.jikan.callback;

import xyz.themanusia.brot.network.jikan.response.Manga;

public interface JikanMangaCallback extends JikanCallback {
    void onGetMangaSuccess(Manga manga);
}
