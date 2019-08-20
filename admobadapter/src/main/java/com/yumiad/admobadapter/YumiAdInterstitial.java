package com.yumiad.admobadapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitial;
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitialListener;
import com.yumi.android.sdk.ads.publish.AdError;
import com.yumi.android.sdk.ads.publish.YumiInterstitial;
import com.yumi.android.sdk.ads.publish.YumiSettings;
import com.yumi.android.sdk.ads.publish.listener.IYumiInterstitialListener;

import static com.yumiad.admobadapter.YumiAdUtil.getGDPRConsent;
import static com.yumiad.admobadapter.YumiAdUtil.recodeYumiError;

/**
 * Description:
 * <p>
 * Created by lgd on 2019-07-18.
 */
public class YumiAdInterstitial implements CustomEventInterstitial {
    private static final String TAG = "ZPLAYAdsAdMobAdapter";
    private YumiInterstitial mYumiInterstitial;

    @Override
    public void requestInterstitialAd(Context context, final CustomEventInterstitialListener listener, String serverParameter, MediationAdRequest mediationAdRequest, Bundle customEventExtras) {
        Log.e(TAG, "requestInterstitialAd");

        if (!(context instanceof Activity)) {
            Log.e(TAG, "requestInterstitialAd: YumiAd needs Activity object to initialize sdk.");
            listener.onAdFailedToLoad(AdRequest.ERROR_CODE_INVALID_REQUEST);
            return;
        }

        YumiAdUtil.YumiParams p = new YumiAdUtil.YumiParams(serverParameter);
        YumiSettings.runInCheckPermission(p.runInCheckPermissions);
        YumiSettings.setGDPRConsent(getGDPRConsent(p.GDPRConsent));

        mYumiInterstitial = new YumiInterstitial((Activity) context, p.slotId, p.isAutoloadNext);
        mYumiInterstitial.setChannelID(p.channelId);
        mYumiInterstitial.setVersionName(p.versionName);
        mYumiInterstitial.setInterstitialEventListener(new IYumiInterstitialListener() {
            @Override
            public void onInterstitialPrepared() {
                listener.onAdLoaded();
            }

            @Override
            public void onInterstitialPreparedFailed(AdError adError) {
                listener.onAdFailedToLoad(recodeYumiError(adError));
            }

            @Override
            public void onInterstitialExposure() {
                listener.onAdOpened();
            }

            @Override
            public void onInterstitialClicked() {
                listener.onAdClicked();
            }

            @Override
            public void onInterstitialClosed() {
                listener.onAdClosed();
            }

            @Override
            public void onInterstitialExposureFailed(AdError adError) {
                // exposure failed should close the ad
                listener.onAdClosed();
            }

            @Override
            public void onInterstitialStartPlaying() {
            }
        });
        mYumiInterstitial.requestYumiInterstitial();
    }

    @Override
    public void showInterstitial() {
        if (mYumiInterstitial == null) {
            Log.e(TAG, "YumiAd not initialized.");
            return;
        }
        mYumiInterstitial.showInterstitial();
    }

    @Override
    public void onDestroy() {
        if (mYumiInterstitial != null) {
            mYumiInterstitial.destroy();
        }
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onResume() {
    }

}
