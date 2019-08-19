package com.yumiad.admobsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import static com.yumiad.admobsample.MainActivity.BANNER_ID;

/**
 * Description:
 * <p>
 * Created by lgd on 2019-07-18.
 */
public class BannerActivity extends AppCompatActivity {
    private static String TAG = "BannerActivity";
    private TextView text;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_banner);
        text = findViewById(R.id.textView2);
        text.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                text.setText("");
                return true;
            }
        });

        mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(BANNER_ID);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                addLog("onAdClosed: ");
            }

            @Override
            public void onAdFailedToLoad(int var1) {
                addLog("onAdFailedToLoad: " + var1);
            }

            @Override
            public void onAdLeftApplication() {
                addLog("onAdLeftApplication: ");
            }

            @Override
            public void onAdOpened() {
                addLog("onAdOpened: ");
            }

            @Override
            public void onAdLoaded() {
                addLog("onAdLoaded: ");
            }

            @Override
            public void onAdClicked() {
                addLog("onAdClicked: ");
            }

            @Override
            public void onAdImpression() {
                addLog("onAdImpression: ");
            }
        });

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;
        addContentView(mAdView, params);
    }

    public void loadAd(View view) {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        addLog("loadAd");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdView != null) {
            mAdView.destroy();
        }
    }

    private void addLog(final String msg) {
        Log.d(TAG, msg);
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (text != null) {
                    text.append(msg + "\n");
                }
            }
        });
    }
}
