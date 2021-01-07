package tv.twitch.android.mod.models.chat;


import androidx.annotation.NonNull;

import tv.twitch.android.mod.emotes.UrlProvider;


public interface Emote {
    @NonNull
    String getCode();

    boolean isGif();

    String getEmoteId();

    UrlProvider getUrlProvider();
}