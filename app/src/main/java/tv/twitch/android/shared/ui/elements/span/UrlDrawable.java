package tv.twitch.android.shared.ui.elements.span;


import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.load.resource.gif.GifDrawable;

import tv.twitch.android.mod.bridges.interfaces.IUrlDrawable;
import tv.twitch.android.mod.hooks.Jump;


public class UrlDrawable extends BitmapDrawable implements IUrlDrawable { // TODO: __IMPLEMENT
    private Drawable drawable;
    private MediaSpan$Type type;
    private String url;

    private boolean isTwitchEmote = true; // TODO: __INJECT_FIELD
    private boolean shouldWide = false; // TODO: __INJECT_FIELD


    public UrlDrawable(String str, MediaSpan$Type mediaSpan$Type) {
        /* ... */
    }

    public final void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void draw(Canvas canvas) { // TODO: __REPLACE_METHOD
        Drawable drawable = this.drawable;
        if (drawable != null) {
            drawable.draw(canvas);
            if (drawable instanceof GifDrawable) {
                if (Jump.shouldAnimate()) {
                    ((GifDrawable) drawable).start();
                }
            }
        }
    }

    @Override
    public Drawable getDrawable() { // TODO: __INJECT_METHOD
        return drawable;
    }

    public boolean isBadge() { // TODO: __INJECT_METHOD
        return type == MediaSpan$Type.Badge;
    }

    public void setTwitchEmote(boolean z) { // TODO: __INJECT_METHOD
        isTwitchEmote = z;
    }

    public void setShouldWide(boolean z) { // TODO: __INJECT_METHOD
        shouldWide = z;
    }

    public boolean shouldWide() { // TODO: __INJECT_METHOD
        return shouldWide;
    }

    public boolean isTwitchEmote() { // TODO: __INJECT_METHOD
        return isTwitchEmote;
    }

    public final String getUrl() {
        return this.url;
    }
}
