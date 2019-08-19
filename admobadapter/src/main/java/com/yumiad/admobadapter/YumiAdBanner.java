package com.yumiad.admobadapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.customevent.CustomEventBanner;
import com.google.android.gms.ads.mediation.customevent.CustomEventBannerListener;
import com.yumi.android.sdk.ads.publish.AdError;
import com.yumi.android.sdk.ads.publish.YumiBanner;
import com.yumi.android.sdk.ads.publish.listener.IYumiBannerListener;

import static com.yumi.android.sdk.ads.publish.enumbean.AdSize.BANNER_SIZE_320X50;
import static com.yumi.android.sdk.ads.publish.enumbean.AdSize.BANNER_SIZE_728X90;
import static com.yumiad.admobadapter.YumiAdUtil.recodeYumiError;

/**
 * Description:
 * <p>
 * Created by lgd on 2019-07-18.
 */
public class YumiAdBanner implements CustomEventBanner {
    private String TAG = "YumiAdBanner";

    private YumiBanner banner;

    @Override
    public void requestBannerAd(Context context, final CustomEventBannerListener customEventBannerListener, String s, AdSize adSize, MediationAdRequest mediationAdRequest, Bundle bundle) {
        // create YumiBanner instance by activity and your YumiID.
        YumiAdUtil.YumiParams p = new YumiAdUtil.YumiParams(s);
        banner = new YumiBanner((Activity) context, p.slotId, p.isAutoloadNext);
        //setBannerContainer
        banner.setBannerContainer(null, calculateAdSize(adSize), p.isMatchWindowWidth);
        //setChannelID . (Recommend)
        banner.setChannelID(p.channelId);
        // set channel and version (optional)
        banner.setDefaultChannelAndVersion(context);
        //setVersionName . (Recommend)
        banner.setVersionName(p.versionName);
        //setBannerEventListener. (Recommend)
        banner.setBannerEventListener(new IYumiBannerListener() {
            @Override
            public void onBannerPrepared() {
                customEventBannerListener.onAdLoaded(banner.getBannerView());
            }

            @Override
            public void onBannerPreparedFailed(AdError adError) {
                customEventBannerListener.onAdFailedToLoad(recodeYumiError(adError));
            }

            @Override
            public void onBannerExposure() {
                customEventBannerListener.onAdOpened();
            }

            @Override
            public void onBannerClicked() {
                customEventBannerListener.onAdClicked();
            }

            @Override
            public void onBannerClosed() {
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