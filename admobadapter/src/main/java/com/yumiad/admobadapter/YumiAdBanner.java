package com.yumiad.admobadapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.customevent.CustomEventBanner;
import com.google.android.gms.ads.mediation.customevent.CustomEventBannerListener;
import com.yumi.android.sdk.ads.publish.AdError;
import com.yumi.android.sdk.ads.publish.YumiBanner;
import com.yumi.android.sdk.ads.publish.YumiSettings;
import com.yumi.android.sdk.ads.publish.listener.IYumiBannerListener;
import com.yumi.android.sdk.ads.utils.ZplayDebug;

import static com.yumi.android.sdk.ads.publish.enumbean.AdSize.BANNER_SIZE_320X50;
import static com.yumi.android.sdk.ads.publish.enumbean.AdSize.BANNER_SIZE_728X90;
import static com.yumiad.admobadapter.YumiAdUtil.getGDPRConsent;
import static com.yumiad.admobadapter.YumiAdUtil.recodeYumiError;

/**
 * Description:
 * <p>
 * Created by lgd on 2019-07-18.
 */
public class YumiAdBanner implements CustomEventBanner {
    private static final String TAG = "YumiAdBanner";

    private YumiBanner banner;

    @Override
    public void requestBannerAd(Context context, final CustomEventBannerListener customEventBannerListener, String s, AdSize adSize, MediationAdRequest mediationAdRequest, Bundle bundle) {
        ZplayDebug.d(TAG, "requestBannerAd: ");
        if (!(context instanceof Activity)) {
            Log.e(TAG, "requestInterstitialAd: YumiAd needs Activity object to initialize sdk.");
            customEventBannerListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INVALID_REQUEST);
            return;
        }

        YumiAdUtil.YumiParams p = new YumiAdUtil.YumiParams(s);
        YumiSettings.runInCheckPermission(p.runInCheckPermissions);
        YumiSettings.setGDPRConsent(getGDPRConsent(p.GDPRConsent));

        banner = new YumiBanner((Activity) context, p.slotId, p.isAutoloadNext);
        banner.setBannerContainer(null, calculateAdSize(adSize), p.isMatchWindowWidth);
        banner.setChannelID(p.channelId);
        banner.setDefaultChannelAndVersion(context);
        banner.setVersionName(p.versionName);
        banner.setBannerEventListener(new IYumiBannerListener() {
            @Override
            public void onBannerPrepared() {
                ZplayDebug.d(TAG, "onBannerPrepared");
                customEventBannerListener.onAdLoaded(banner.getBannerView());
            }

            @Override
            public void onBannerPreparedFailed(AdError adError) {
                ZplayDebug.d(TAG, "onBannerPreparedFailed: " + adError);
                customEventBannerListener.onAdFailedToLoad(recodeYumiError(adError));
            }

            @Override
            public void onBannerExposure() {
                ZplayDebug.d(TAG, "onBannerExposure");
                customEventBannerListener.onAdOpened();
            }

            @Override
            public void onBannerClicked() {
                ZplayDebug.d(TAG, "onBannerClicked");
                customEventBannerListener.onAdClicked();
            }

            @Override
            public void onBannerClosed() {
                ZplayDebug.d(TAG, "onBannerClosed");
                customEventBannerListener.onAdClosed();
            }
        });

        banner.requestYumiBanner();
    }

    private com.yumi.android.sdk.ads.publish.enumbean.AdSize calculateAdSize(AdSize adSize) {
        // Use the smallest AdSize that will properly contain the adView
        if (adSize == AdSize.BANNER) {
            return BANNER_SIZE_320X50;
        } else if (adSize == AdSize.LARGE_BANNER) {
            return BANNER_SIZE_728X90;
        }
        return BANNER_SIZE_320X50;
    }

    @Override
    public void onDestroy() {
        banner.destroy();
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onResume() {
    }
}