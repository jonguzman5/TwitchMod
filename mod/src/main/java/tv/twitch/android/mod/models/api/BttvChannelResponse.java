package tv.twitch.android.mod.models.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BttvChannelResponse {
    @SerializedName("id")
    private String channelId;
    @SerializedName("bots")
    private String[] bots;
    @SerializedName("channelEmotes")
    private List<BttvEmoteResponse> channelEmotes;
    @SerializedName("sharedEmotes")
    private List<BttvEmoteResponse> sharedEmotes;

    public String getChannelId() {
        return channelId;
    }

    public String[] getBots() {
        return bots;
    }

    public List<BttvEmoteResponse> getChannelEmotes() {
        return channelEmotes;
    }

    public List<BttvEmoteResponse> getSharedEmotes() {
        return sharedEmotes;
    }

    public void setBots(String[] bots) {
        this.bots = bots;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void setChannelEmotes(List<BttvEmoteResponse> channelEmotes) {
        this.channelEmotes = channelEmotes;
    }

    public void setSharedEmotes(List<BttvEmoteResponse> sharedEmotes) {
        this.sharedEmotes = sharedEmotes;
    }
}
