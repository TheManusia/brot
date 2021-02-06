package xyz.themanusia.brot.network.jikan.callback;

import xyz.themanusia.brot.network.jikan.response.AnimeSearch;

public interface JikanAnimeSearchCallback extends JikanCallback {
    void onGetAnimesSuccess(AnimeSearch results);
}
