package com.yumiad.admobadapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdAdapter;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdListener;
import com.yumi.android.sdk.ads.publish.AdError;
import com.yumi.android.sdk.ads.publish.YumiMedia;
import com.yumi.android.sdk.ads.publish.YumiSettings;
import com.yumi.android.sdk.ads.publish.listener.IYumiMediaListener;

import static com.yumiad.admobadapter.YumiAdUtil.getGDPRConsent;
import static com.yumiad.admobadapter.YumiAdUtil.recodeYumiError;

/**
 * Description:
 * <p>
 * Created by lgd on 2019-07-18.
 */
public class YumiAdRewardedVideo implements MediationRewardedVideoAdAdapter {
    private static final String TAG = "ZPLAYAdsAdMobAdapter";
    private YumiMedia mYumiMedia;
    private MediationRewardedVideoAdListener mListener;
    private boolean hasCalledLoad;

    @Override
    public void initialize(Context context, MediationAdRequest mediationAdRequest, String s, final MediationRewardedVideoAdListener listener, Bundle serverParameters, Bundle bundle1) {
        if (!(context instanceof Activity)) {
            Log.e(TAG, "requestInterstitialAd: YumiAd needs Activity object to initialize sdk.");
            listener.onAdFailedToLoad(this, AdRequest.ERROR_CODE_INVALID_REQUEST);
            return;
        }

        mListener = listener;

        YumiAdUtil.YumiParams p = new YumiAdUtil.YumiParams(serverParameters.getString(CUSTOM_EVENT_SERVER_PARAMETER_FIELD));
        YumiSettings.runInCheckPermission(p.runInCheckPermissions);
        YumiSettings.setGDPRConsent(getGDPRConsent(p.GDPRConsent));

        mYumiMedia = new YumiMedia((Activity) context, p.slotId);
        mYumiMedia.setMediaEventListener(new IYumiMediaListener() {
            @Override
            public void onMediaPrepared() {
                if (hasCalledLoad) {
                    listener.onAdLoaded(YumiAdRewardedVideo.this);
                    hasCalledLoad = false;
                }
            }

            @Override
            public void onMediaPreparedFailed(AdError adError) {
                if (hasCalledLoad) {
                    listener.onAdFailedToLoad(YumiAdRewardedVideo.this, recodeYumiError(adError));
                    hasCalledLoad = false;
                }

            }

            @Override
            public void onMediaExposure() {
                listener.onAdOpened(YumiAdRewardedVideo.this);
            }

            @Override
            public void onMediaExposureFailed(AdError adError) {
                // exposure failed show close the ad.
                listener.onAdClosed(YumiAdRewardedVideo.this);
            }

            @Override
            public void onMediaClicked() {
                listener.onAdClicked(YumiAdRewardedVideo.this);
            }

            @Override
            public void onMediaClosed(boolean isRewarded) {
                listener.onAdClosed(YumiAdRewardedVideo.this);
            }

            @Override
            public void onMediaRewarded() {
                RewardItem rewardItem = new RewardItem() {
                    @Override
                    public String getType() {
                        return "YumiAd";
                    }

                    @Override
                    public int getAmount() {
                        return 1;
                    }
                };
                listener.onRewarded(YumiAdRewardedVideo.this, rewardItem);
            }

            @Override
            public void onMediaStartPlaying() {
                listener.onVideoStarted(YumiAdRewardedVideo.this);
            }
        });

        mYumiMedia.setChannelID(p.channelId);
        mYumiMedia.setVersionName(p.versionName);
        mYumiMedia.requestYumiMedia();
        listener.onInitializationSucceeded(this);
    }

    @Override
    public void loadAd(MediationAdRequest mediationAdRequest, Bundle serverParameters, Bundle bundle1) {
        if (mYumiMedia == null) {
            Log.e(TAG, "YumiAd not initialized.");
            return;
        }

        hasCalledLoad = true;

        if (mYumiMedia.isReady()) {
            mListener.onAdLoaded(YumiAdRewardedVideo.this);
        } else {
            mYumiMedia.requestYumiMedia();
        }
    }

    @Override
    public void showVideo() {
        if (mYumiMedia == null) {
            Log.e(TAG, "YumiAd not initialized.");
            return;
        }
        mYumiMedia.showMedia();
    }

    @Override
    public boolean isInitialized() {
        return mYumiMedia != null;
    }

    @Override
    public void onDestroy() {
        if (mYumiMedia != null) {
            mYumiMedia.destroy();
        }
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onResume() {
    }
}
