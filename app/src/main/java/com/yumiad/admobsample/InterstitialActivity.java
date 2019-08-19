package com.yumiad.admobsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import static com.yumiad.admobsample.MainActivity.INTERSTITIAL_ID;

/**
 * Description:
 * <p>
 * Created by lgd on 2019-07-18.
 */
public class InterstitialActivity extends AppCompatActivity {
    private static final String TAG = "InterstitialActivity";

    TextView mLogView;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstiail);
        mLogView = findViewById(R.id.ai_log_text);
        mLogView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mLogView.setText("");
                return true;
            }
        });


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(INTERSTITIAL_ID);
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdClosed() {
                addLog("onAdClosed: ");
            }

            public void onAdFailedToLoad(int var1) {
                addLog("onAdFailedToLoad: " + var1);
            }

            public void onAdLeftApplication() {
                addLog("onAdLeftApplication: ");
            }

            public void onAdOpened() {
                addLog("onAdOpened: ");
            }

            public void onAdLoaded() {
                addLog("onAdLoaded: ");
            }

            public void onAdClicked() {
                addLog("onAdClicked: ");
            }

            public void onAdImpression() {
                addLog("onAdImpression: ");
            }
        });
    }

    public void requestAd(View view) {
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        addLog("start loading advertising.");
    }

    public void presentAd(View view) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            addLog("interstitial ad not ready");
        }
    }

    void addLog(String msg) {
        Log.d(TAG, msg);
        mLogView.append(msg + "\n");
    }
}