package com.yumiad.admobsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import static com.yumiad.admobsample.MainActivity.VIDEO_ID;

/**
 * Description:
 * <p>
 * Created by lgd on 2019-07-18.
 */
public class RewardedVideoActivity extends AppCompatActivity implements RewardedVideoAdListener {
    private static final String TAG = "RewardedVideoActivity";

    TextView mLogView;
    private RewardedVideoAd mRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded_video);
        mLogView = findViewById(R.id.log_text);
        mLogView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mLogView.setText("");
                return true;
            }
        });

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRewardedVideoAd.destroy(this);
    }

    public void requestAd(View view) {
        mRewardedVideoAd.loadAd(VIDEO_ID, new AdRequest.Builder().build());
        addLog("start loading advertising.");
    }


    public void presentAd(View view) {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        } else {
            addLog("the video ad not ready yet.");
        }
    }


    void addLog(String msg) {
        Log.d(TAG, msg);
        mLogView.append(msg + "\n");
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        addLog("onRewardedVideoAdLoaded: ");
    }

    @Override
    public void onRewardedVideoAdOpened() {
        addLog("onRewardedVideoAdOpened: ");

    }

    @Override
    public void onRewardedVideoStarted() {
        addLog("onRewardedVideoStarted: ");
    }

    @Override
    public void onRewardedVideoAdClosed() {
        addLog("onRewardedVideoAdClosed: ");
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        addLog("onRewarded: " + rewardItem);
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        addLog("onRewardedVideoAdLeftApplication: ");

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        addLog("onRewardedVideoAdFailedToLoad: " + i);
    }

    @Override
    public void onRewardedVideoCompleted() {
        addLog("onRewardedVideoCompleted: ");
    }
}