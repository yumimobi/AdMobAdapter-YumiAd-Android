package com.yumiad.admobadapter;

import android.text.TextUtils;
import android.util.Log;

import com.yumi.android.sdk.ads.publish.AdError;
import com.yumi.android.sdk.ads.publish.enumbean.YumiGDPRStatus;

import org.json.JSONException;
import org.json.JSONObject;

import static com.google.android.gms.ads.AdRequest.ERROR_CODE_INTERNAL_ERROR;
import static com.google.android.gms.ads.AdRequest.ERROR_CODE_INVALID_REQUEST;
import static com.google.android.gms.ads.AdRequest.ERROR_CODE_NETWORK_ERROR;
import static com.google.android.gms.ads.AdRequest.ERROR_CODE_NO_FILL;

/**
 * Description:
 * <p>
 * Created by lgd on 2019-07-18.
 */
final class YumiAdUtil {
    private static final String TAG = "YumiAdUtil";

    static YumiGDPRStatus getGDPRConsent(String gdpt) {
        if (TextUtils.equals(gdpt, "0")) {
            return YumiGDPRStatus.NON_PERSONALIZED;
        } else if (TextUtils.equals(gdpt, "1")) {
            return YumiGDPRStatus.PERSONALIZED;
        } else {
            return YumiGDPRStatus.UNKNOWN;
        }
    }

    static boolean isRunInCheckPermissions(String json) {
        YumiParams p = new YumiParams(json);
        return p.runInCheckPermissions;
    }

    static int recodeYumiError(AdError yumiError) {
        switch (yumiError.getErrorCode()) {
            case ERROR_NETWORK_ERROR:
            case ERROR_INVALID_NETWORK:
                return ERROR_CODE_NETWORK_ERROR;
            case ERROR_NO_FILL:
                return ERROR_CODE_NO_FILL;
            case ERROR_INVALID:
                return ERROR_CODE_INVALID_REQUEST;
            default:
                return ERROR_CODE_INTERNAL_ERROR;
        }
    }

    static class YumiParams {
        String slotId = "";
        boolean isAutoloadNext = false;
        String channelId = "";
        String versionName = "";
        String GDPRConsent;
        boolean isMatchWindowWidth;
        boolean runInCheckPermissions = false;

        YumiParams(String json) {
            if (json == null) {
                return;
            }
            try {
                JSONObject jo = new JSONObject(json);
                slotId = jo.getString("slotId");
                channelId = jo.getString("channelId");
                versionName = jo.getString("versionName");
                GDPRConsent = jo.getString("GDPRConsent");
                isAutoloadNext = jo.getBoolean("isAutoloadNext");
                isMatchWindowWidth = jo.getBoolean("isMatchWindowWidth");
                runInCheckPermissions = jo.getBoolean("runInCheckPermissions");
            } catch (JSONException e) {
                Log.d(TAG, "YumiParams: parse error, ", e);
            }
        }
    }
}
