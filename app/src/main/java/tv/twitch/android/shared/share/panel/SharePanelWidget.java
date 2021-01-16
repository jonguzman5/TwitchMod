package tv.twitch.android.shared.share.panel;


import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import tv.twitch.android.app.core.ViewExtensionsKt;
import tv.twitch.android.mod.bridge.interfaces.ISharedPanelWidget;
import tv.twitch.android.mod.hooks.Controller;
import tv.twitch.android.models.clips.ClipModel;
import tv.twitch.android.models.videos.VodModel;
import tv.twitch.android.shared.player.presenters.PlayerPresenter;
import tv.twitch.android.shared.ui.elements.bottomsheet.InteractiveRowView;


public class SharePanelWidget extends FrameLayout implements ISharedPanelWidget { // TODO: __IMPLEMENT // FIXME: REWRITE
    private ClipModel mClipModel;
    private VodModel mVodModel;
    private PlayerPresenter mPlayerPresenter;

    private SharePanelWidgetListener mSharePanelWidgetListener;

    /* ... */

    private InteractiveRowView mDownloadButton; // TODO: __INJECT_FIELD

    /* ... */

    public interface SharePanelWidgetListener {
        /* ... */

        void onShareClicked();
    }


    public SharePanelWidget(@NonNull Context context) {
        super(context);
    }

    private void setupDownloadButton() { // TODO: __INJECT_METHOD
        this.mDownloadButton = (InteractiveRowView) Controller.setupDownloadButton(this,
                mClipModel, this);
    }

    private void init() {
        setupDownloadButton(); // TODO: __INJECT_CODE
    }

    private void updateModButtonsVisibility() { // TODO: __INJECT_METHOD
        if (this.mDownloadButton != null) {
            ViewExtensionsKt.visibilityForBoolean(this.mDownloadButton, mClipModel != null);
        }
    }

    private void updateViewState() {
        updateModButtonsVisibility();  // TODO: __INJECT_CODE
    }

    @Override
    public ClipModel getClipModel() { // TODO: __INJECT_METHOD
        return mClipModel;
    }

    @Override
    public void hidePanel() { // TODO: __INJECT_METHOD
        SharePanelWidgetListener sharePanelWidgetListener = this.mSharePanelWidgetListener;
        if (sharePanelWidgetListener != null) {
            sharePanelWidgetListener.onShareClicked();
        }

    }

    @Override
    public FrameLayout getLayout() { // TODO: __INJECT_METHOD
        return this;
    }
}