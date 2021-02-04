package xyz.themanusia.brot.network.jikan.callback;

import xyz.themanusia.brot.network.jikan.response.MangaSearch;

public interface JikanMangaSearchCallback extends JikanCallback {
    void onGetMangasSuccess(MangaSearch results);
}
