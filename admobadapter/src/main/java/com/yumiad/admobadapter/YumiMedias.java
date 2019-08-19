package com.yumiad.admobadapter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Pair;

import com.yumi.android.sdk.ads.publish.YumiMedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description:
 * <p>
 * Created by lgd on 2019-07-18.
 */
class YumiMedias {
    private static HashMap<Activity, List<Pair<String, YumiMedia>>> sYumiMedias = new HashMap<>();

    private YumiMedias() {
    }

    static YumiMedia getYumiMedia(Activity activity, String slotId) {
        List<Pair<String, YumiMedia>> medias = sYumiMedias.get(activity);

        if (medias != null) {
            for (Pair<String, YumiMedia> p : medias) {
                if (TextUtils.equals(p.first, slotId)) {
                    return p.second;
                }
            }
        }

        YumiMedia yumiMedia = new YumiMedia(activity, slotId);
        addYumiMedia(activity, slotId, yumiMedia);

        return yumiMedia;
    }

    private static void addYumiMedia(Activity activity, String slotId, YumiMedia yumiMedia) {
        List<Pair<String, YumiMedia>> yumiMedias = sYumiMedias.get(activity);
        if (yumiMedias == null) {
            yumiMedias = new ArrayList<>();
            sYumiMedias.put(activity, yumiMedias);
        }

        yumiMedias.add(new Pair<>(slotId, yumiMedia));
    }

    static void removeMediasByActivity(Activity activity) {
        List<Pair<String, YumiMedia>> medias = sYumiMedias.remove(activity);

        if (medias != null) {
            for (Pair<String, YumiMedia> p : medias) {
                p.second.destroy();
            }
        }
    }
}
