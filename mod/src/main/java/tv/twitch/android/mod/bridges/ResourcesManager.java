package tv.twitch.android.mod.bridges;


import android.content.Context;
import android.content.res.Resources;
import android.util.LruCache;

import java.util.HashMap;


public class ResourcesManager {
    public static final ResourcesManager INSTANCE = new ResourcesManager();

    private final HashMap<IdType, PubCache> mMap = new HashMap<>();


    private enum IdType {
        STRING("string"),
        ID("id"),
        LAYOUT("layout");

        private final String mType;

        IdType(String type) {
            mType = type;
        }

        public String getType() {
            return mType;
        }
    }

    private ResourcesManager () {
        for (IdType idType : IdType.values()) {
            mMap.put(idType, new PubCache(200, idType.getType()));
        }
    }

    private static class PubCache extends LruCache<String, Integer> {
        private final String mDefType;

        public PubCache(int maxSize, String defType) {
            super(maxSize);
            mDefType = defType;
        }

        @Override
        protected Integer create(String key) {
            Context context = LoaderLS.getInstance();
            Resources resources = context.getResources();
            return resources.getIdentifier(key, mDefType, context.getPackageName());
        }
    }

    public Integer getId(String name) {
        PubCache cache = mMap.get(IdType.ID);
        if (cache != null)
            return cache.get(name);

        return 0;
    }

    public Integer getLayoutId(String name) {
        PubCache cache = mMap.get(IdType.LAYOUT);
        if (cache != null)
            return cache.get(name);

        return 0;
    }

    public Integer getStringId(String name) {
        PubCache cache = mMap.get(IdType.STRING);
        if (cache != null)
            return cache.get(name);

        return 0;
    }

    public String getString(String name) {
        PubCache cache = mMap.get(IdType.STRING);

        int resId = cache != null ? cache.get(name) : 0;
        if (resId == 0) {
            return "RESOURCE ID NOT FOUND: '" + name + "'";
        } else {
            return LoaderLS.getInstance().getResources().getString(resId);
        }
    }
}