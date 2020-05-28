package tv.twitch.android.mod.bridges;


import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.view.View;

import com.google.android.exoplayer2.PlaybackParameters;

import java.util.Collection;
import java.util.Date;

import tv.twitch.a.k.g.c1.b;
import tv.twitch.a.k.g.g;
import tv.twitch.android.api.p1.i1;
import tv.twitch.android.mod.emotes.EmoteManager;
import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.models.settings.PlayerImpl;
import tv.twitch.android.mod.settings.PrefManager;
import tv.twitch.android.mod.utils.ChatUtils;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.models.Playable;
import tv.twitch.android.models.channel.ChannelInfo;
import tv.twitch.android.shared.chat.communitypoints.models.CommunityPointsModel;
import tv.twitch.chat.ChatEmoticonSet;

import static tv.twitch.android.mod.utils.ChatUtils.injectEmotesSpan;


@SuppressWarnings("FinalStaticMethod")
public class Hooks {
    private final static String VOD_PLAYER_PRESENTER_CLASS = "tv.twitch.a.k.w.j0.w";
    private final static float DEFAULT_MINIPLAYER_SIZE = 1.0f;

    /**
     * Class: tv.twitch.android.adapters.a.b.c
     * signature: public final boolean a(MotionEvent motionEvent)
     */
    public final static void tryCopyMsg(String msg) {
        PrefManager prefManager = LoaderLS.getInstance().getPrefManager();
        if (!prefManager.isCopyMsgOn())
            return;

        if (TextUtils.isEmpty(msg)) {
            Logger.debug("empty msg");
        }

        Helper.saveToClipboard(msg);
    }

    /**
     * Class: EmoteAdapterSection
     * signature: public void a(RecyclerView.b0 b0Var)
     */
    public final static String hookSetName(String org, String setId) {
        PrefManager prefManager = LoaderLS.getInstance().getPrefManager();
        if (!prefManager.isHookEmoticonSetOn())
            return org;

        ChatUtils.EmoteSet set = ChatUtils.EmoteSet.findById(setId);
        if (set != null)
            return set.getDescription();

        return org;
    }

    /**
     * Class: CommunityPointsButtonViewDelegate
     * signature: private final void e(CommunityPointsModel communityPointsModel)
     */
    public final static void setClicker(final View pointButtonView, CommunityPointsModel pointsModel) {
        PrefManager prefManager = LoaderLS.getInstance().getPrefManager();
        if (!prefManager.isClickerOn())
            return;

        Helper helper = LoaderLS.getInstance().getHelper();
        helper.setClicker(pointButtonView, pointsModel);
    }

    /**
     * Class: StandaloneMediaClock
     * signatute: private PlaybackParameters f = PlaybackParameters.e;
     */
    public final static PlaybackParameters hookVodPlayerStandaloneMediaClockInit(PlaybackParameters org) {
        PrefManager prefManager = LoaderLS.getInstance().getPrefManager();
        float speed = Float.parseFloat(prefManager.getExoplayerSpeed().getPreferenceValue());
        if (speed == org.a)
            return org;

        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element == null)
                continue;

            String clazz = element.getClassName();
            if (TextUtils.isEmpty(clazz))
                continue;

            if (!clazz.equals(VOD_PLAYER_PRESENTER_CLASS))
                continue;

            return new PlaybackParameters(speed);
        }

        return PlaybackParameters.e;
    }

    /**
     * Class: MessageRecyclerItem
     * signature:
     */
    public final static Spanned addTimestampToMessage(Spanned message, String messageId) {
        PrefManager manager = LoaderLS.getInstance().getPrefManager();
        if (!manager.isTimestampsOn())
            return message;

        return ChatUtils.addTimestamp(message, new Date());
    }

    /**
     * Class: ChatController
     * signature: ChatEmoticonSet[] b()
     */
    public final static ChatEmoticonSet[] hookChatEmoticonSet(ChatEmoticonSet[] orgSet) {
        PrefManager manager = LoaderLS.getInstance().getPrefManager();
        if (!manager.isHookEmoticonSetOn())
            return orgSet;

        if (orgSet == null)
            return null;

        Helper helper = LoaderLS.getInstance().getHelper();
        EmoteManager emoteManager = LoaderLS.getInstance().getEmoteManager();

        final int currentChannel = helper.getCurrentChannel();
        Collection<Emote> globalEmotes = emoteManager.getGlobalEmotes();
        Collection<Emote> bttvEmotes = emoteManager.getBttvEmotes(currentChannel);
        Collection<Emote> ffzEmotes = emoteManager.getFfzEmotes(currentChannel);

        ChatEmoticonSet[] newSet = new ChatEmoticonSet[orgSet.length+3];
        System.arraycopy(orgSet, 0, newSet, 0, orgSet.length);

        newSet[newSet.length-1] = ChatFactory.getSet(ChatUtils.EmoteSet.BTTV.getId(), bttvEmotes);
        newSet[newSet.length-2] = ChatFactory.getSet(ChatUtils.EmoteSet.FFZ.getId(), ffzEmotes);
        newSet[newSet.length-3] = ChatFactory.getSet(ChatUtils.EmoteSet.GLOBAL.getId(), globalEmotes);

        return newSet;
    }

    /**
     * Class: ChatConnectionController
     * signature: private final void a(ChannelInfo channelInfo)
     */
    public final static void requestEmotes(ChannelInfo channelInfo) {
        PrefManager manager = LoaderLS.getInstance().getPrefManager();
        if (!manager.isEmotesOn())
            return;

        EmoteManager emoteManager = LoaderLS.getInstance().getEmoteManager();
        emoteManager.requestEmotes(channelInfo.getId(), false);
    }

    /**
     * Class: ModelTheatreModeTracker
     * signature: public c(f1 f1Var, Playable playable, Object pageViewTracker)
     */
    public final static void requestEmotes(final i1 playableModelParser, final Playable playable) {
        PrefManager manager = LoaderLS.getInstance().getPrefManager();
        if (!manager.isEmotesOn())
            return;

        EmoteManager emoteManager = LoaderLS.getInstance().getEmoteManager();
        emoteManager.requestEmotes(Helper.getChannelId(playableModelParser, playable), true);
    }

    /**
     * Class: FollowedGamesFetcher
     * signature: public final boolean j()
     */
    public final static boolean hookFollowerFetcher(boolean org) {
        PrefManager manager = LoaderLS.getInstance().getPrefManager();
        if (!manager.isDisableFollowedGames())
            return org;

        return false;
    }

    /**
     * Class: RecommendedStreamsFetcher
     * signature: public final boolean j()
     */
    public final static boolean hookRecommendedFetcher(boolean org) {
        PrefManager manager = LoaderLS.getInstance().getPrefManager();
        if (!manager.isDisableRecommendations())
            return org;

        return false;
    }

    /**
     * Class: ResumeWatchingVideosFetcher
     * signature: public final boolean j()
     */
    public final static boolean hookResumeWatchingFetcher(boolean org) {
        PrefManager manager = LoaderLS.getInstance().getPrefManager();
        if (!manager.isDisableRecentWatching())
            return org;

        return false;
    }

    /**
     * Class: VideoDebugConfig
     * signature: public final boolean a()
     */
    public final static boolean hookVideoDebugPanel(boolean org) {
        PrefManager manager = LoaderLS.getInstance().getPrefManager();
        if (!manager.isShowVideoDebugPanel())
            return org;

        return true;
    }

    /**
     * Class: MiniPlayerSize
     * signature: public final int b()
     */
    public final static int hookMiniplayerSize(int size) {
        PrefManager manager = LoaderLS.getInstance().getPrefManager();
        float k = Float.parseFloat(manager.getMiniPlayerSize().getPreferenceValue());
        if (k == DEFAULT_MINIPLAYER_SIZE)
            return size;

        return (int) (k * size);
    }

    /**
     * Class: PlayerImplementation
     * signature: public final PlayerImplementation getProviderForName(String str)
     */
    public final static String hookPlayerProvider(String name) {
        if (TextUtils.isEmpty(name)) {
            Logger.warning("empty name");
            return name;
        }

        PrefManager manager = LoaderLS.getInstance().getPrefManager();
        PlayerImpl playerImpl = manager.getPlayer();
        switch (playerImpl) {
            default:
            case AUTO:
                return name;
            case CORE:
            case EXO:
                return playerImpl.getPreferenceValue();
        }
    }

    /**
     * Class: SearchSuggestionAdapterBinder
     * signature: public final void a(Object obj)
     */
    public final static boolean isJumpDisRecentSearch() {
        PrefManager manager = LoaderLS.getInstance().getPrefManager();
        if (!manager.isDisableRecentSearch())
            return false;

        return true;
    }

    /**
     * Class: *.*
     */
    public final static boolean isDevModeOn() {
        PrefManager manager = LoaderLS.getInstance().getPrefManager();
        if (manager.isDevModeOn())
            return true;

        return false;
    }

    /**
     * Class: RecommendationAutoPlayPresenter
     * signature: public final void prepareRecommendationForCurrentModel(T t)
     */
    public final static boolean isJumpDisableAutoplay() {
        PrefManager manager = LoaderLS.getInstance().getPrefManager();
        if (!manager.isDisableAutoplay())
            return false;

        return true;
    }

    /**
     * Class: ChatMessageInputViewPresenter
     * signature: public final void a(b bVar)
     */
    public final static void setCurrentChannel(b event) {
        if (event == null) {
            Logger.error("event is null");
            return;
        }

        Helper helper = LoaderLS.getInstance().getHelper();
        helper.setCurrentChannel(event.a().getId());
    }

    /**
     * Class: *.*
     */
    public final static boolean isHideDiscoverTab() {
        PrefManager manager = LoaderLS.getInstance().getPrefManager();
        if (manager.isHideDiscoverTab())
            return true;

        return false;
    }

    /**
     * Class: *.*
     */
    public final static boolean isHideEsportsTab() {
        PrefManager manager = LoaderLS.getInstance().getPrefManager();
        if (manager.isHideEsportsTab())
            return true;

        return false;
    }

    /**
     * Class: *.*
     */
    public final static boolean isFloatingChatEnabled() {
        PrefManager manager = LoaderLS.getInstance().getPrefManager();
        if (manager.isFloatingChatEnabled())
            return true;

        return false;
    }

    /**
     * Some hooks
     */
    public final static void helper() {
        Object o = hookVodPlayerStandaloneMediaClockInit(new PlaybackParameters(0.0f)); // TODO: __HOOK
    }

    /**
     * Class: ChatMessageFactory
     */
    public static SpannedString hookChatMessage(IChatMessageFactory factory, g chatMessageInterface, SpannedString orgMessage, int channelId) { // TODO: __INJECT_METHOD
        PrefManager manager = LoaderLS.getInstance().getPrefManager();
        if (!manager.isEmotesOn())
            return orgMessage;

        try {
            if (TextUtils.isEmpty(orgMessage))
                return orgMessage;

            if (chatMessageInterface.a())
                return orgMessage;

            EmoteManager emoteManager = LoaderLS.getInstance().getEmoteManager();
            emoteManager.requestEmotes(channelId, false);

            return injectEmotesSpan(factory, emoteManager, orgMessage, channelId, manager);
        } catch (Throwable th) {
            th.printStackTrace();
            if (chatMessageInterface.e() == null) {
                Helper.showToast("message is null");
                return orgMessage;
            }

            String message = ChatUtils.getRawMessage(chatMessageInterface.e());
            if (!TextUtils.isEmpty(message)) {
                Helper.showToast("Bad message: '" + message + "'");
            } else {
                Helper.showToast("Empty message");
            }
        }

        return orgMessage;
    }


}